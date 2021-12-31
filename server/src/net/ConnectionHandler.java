package src.net;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.AttributeMap;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.Config;
import src.Server;
import src.client.net.PacketBuffer;
import src.model.entity.players.Player;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Opens the socket on the port as a gateway to the server
 */
public class ConnectionHandler extends ChannelInboundHandlerAdapter implements Runnable, AttributeMap {
    public static final AttributeKey<ConnectionAttachment> attachment = AttributeKey.valueOf("conn-attachment");
    private static final Logger LOGGER = LogManager.getLogger();
    final ChannelGroup channels;
    public static Server server;
    private final HashMap<String, ArrayList<Channel>> connections;
    private final LoginHandler loginHandler;
    public ImmutableList<String> NETWORK_CONNECTION_RESET_EXCEPTIONS =
            ImmutableList.of("Connection reset by peer", "Connection reset");

    /**
     * Creates a new connection handler which manages packets and connection attempts
     * @param server Server running this connection handle
     */
    public ConnectionHandler(Server server){
        super();
        ConnectionHandler.server = server;
        this.loginHandler = new LoginHandler(server);
        this.connections = new HashMap<>();
        this.channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    /**
     * Starts the connection handler in a new thread
     */
    @Override
    public void run() {
        try {
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Starts accepting connections on the configured port using netty
     * @throws IOException
     */
    private void start() throws IOException {
        EventLoopGroup bossGroup = new NioEventLoopGroup(0);
        EventLoopGroup workerGroup = new NioEventLoopGroup(0);
        final ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(
                new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(final SocketChannel channel) {
                        final ChannelPipeline pipeline = channel.pipeline();
                        // Add handlers here
                        // Impl note: Could add checksum, decoder, encoders, etc.
                        pipeline.addLast("decoder", new ConnectionDecoder());
                        pipeline.addLast("handler", new ConnectionHandler(ConnectionHandler.server));
                    }
                }
        );
        // Set channel options
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childOption(ChannelOption.SO_SNDBUF, 1024);
        bootstrap.childOption(ChannelOption.SO_RCVBUF, 1024);

        try {
            ChannelFuture serverChannel = bootstrap.bind(new InetSocketAddress(Config.PORT)).sync();
            LOGGER.info("Game world is now online on port " + Config.PORT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a host ip address from a ChannelHandlerContext
     */
    private String getHostFromCtx(final ChannelHandlerContext ctx){
        return ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
    }

    /**
     * Called when a connection is attempted to the server
     * @param ctx
     */
    @Override
    public void channelRegistered(final ChannelHandlerContext ctx) {
        String hostAddress = getHostFromCtx(ctx);
        ctx.channel().attr(attachment).set(new ConnectionAttachment());
        ctx.fireChannelRegistered();
        //addConnection(hostAddress, ctx.channel());
    }

    /**
     * Called when a connection is dropped, removing itself from the servicing thread
     * @param ctx
     */
    @Override
    public void channelUnregistered(final ChannelHandlerContext ctx) {
        final String hostAddress = getHostFromCtx(ctx);
        final Channel channel = ctx.channel();
        //removeConnection(hostAddress, channel);
    }

    /**
     * When an incoming packet is received
     * @param ctx Channel Context
     * @param message information received
     */
    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object message) throws IOException, ClassNotFoundException {
        final Channel channel = ctx.channel();

        // Get the player associated with the session
        channel.attr(attachment).get().canSendSessionId.set(false);
        Player player = null;
        ConnectionAttachment att = channel.attr(attachment).get();
        if (att != null) {
            player = att.player.get();
        }

        // Parse the packet
        if(message instanceof Packet){
            Packet packet = (Packet)message;
            if(player != null){
                // Assign a player's packets to their model
                System.out.println("Adding packet to player: " + player.username + ", " + packet.getOpcode());
                player.addPacket(packet);
            } else {
                // if player is null, it MUST be a login packet
                PacketBuffer buffer = new PacketBuffer(packet.getPayload().array());
                String username = buffer.readString();
                LoginRequest request = new LoginRequest(channel, username);
                Player p = loginHandler.processRequest(request);

                // Start managing the player
                ConnectionHandler.server.getWorld().addPlayer(p);
                att.player.set(p);
            }
        } else {
            // Invalid packet - for security we'll send nothing back
            System.out.println("Server: Non-packet read: " + message.toString());
        }
    }

    /**
     * Channel connected
     * @param ctx context
     */
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        String hostAddress = getHostFromCtx(ctx);
        addConnection(hostAddress, ctx.channel());

        // Add a timeout to the client channel
        ctx.channel().pipeline().addLast(new ReadTimeoutHandler(Config.PACKET_TIMEOUT_SEC));

        // Send session to the client
        ctx.channel().attr(attachment).get().canSendSessionId.set(true);
        Thread t = new Thread(new SessionIdHandler(ctx));
        t.start();

        ctx.fireChannelActive();
    }

    /**
     * Channel disconnected
     * @param ctx context
     */
    @Override
    public void channelInactive(final ChannelHandlerContext ctx) {
        String hostAddress = getHostFromCtx(ctx);
        ctx.channel().close();
        //removeConnection(hostAddress, ctx.channel());
        //ctx.fireChannelUnregistered();
    }

    /**
     * Log any errors that occur in networking
     * @param ctx Context
     * @param e Thrown exception
     */
    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable e) {
        final Channel channel = ctx.channel();
        final ConnectionAttachment att = channel.attr(attachment).get();

        // Log the error
        if(NETWORK_CONNECTION_RESET_EXCEPTIONS.stream().noneMatch($it -> Objects.equal($it, e.getMessage()))) {
            LOGGER.error("Exception caught in Network I/O : Remote address " + channel.remoteAddress() + " : isOpen " + channel.isOpen() + " : isActive " + channel.isActive() + " : isWritable " + channel.isWritable() + (att == null ? "" : " : Attached Player " + att.player.get()));
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } else {
            LOGGER.info(e.getMessage() + " : Remote address " + channel.remoteAddress() + (att == null ? "" : " : Attached Player " + att.player.get()));
        }

        // Close the channel
        if (ctx.channel().isActive()){
            ctx.channel().close();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    /**
     * Adds a connection to the pool to be managed
     * @param hostAddress IP Address
     * @param channel
     */
    public void addConnection(final String hostAddress, final Channel channel){
        ArrayList<Channel> hostConnections = connections.get(hostAddress);
        channels.add(channel);
        if (hostConnections == null) {
            hostConnections = new ArrayList<>();
        }
        hostConnections.add(channel);
        connections.put(hostAddress, hostConnections);
    }

    /**
     * Drops a managed connection
     * @param hostAddress
     * @param channel
     */
    public void removeConnection(final String hostAddress, final Channel channel){
        LOGGER.info("Dropping connection from " + hostAddress);
        channels.remove(channel);
        ArrayList<Channel> hostConnections = connections.get(hostAddress);
        if (hostConnections != null) {
            hostConnections.remove(channel);
            connections.put(hostAddress, hostConnections);
        }
    }

    /**
     * @return the amount of connections to the server
     * ref: https://stackoverflow.com/questions/10372801/netty-how-to-get-all-client-channel/28071730
     */
    public int getConnectionCount(){
        if(this.channels == null){
            return 0;
        }
        return this.channels.size();
    }

    @Override
    public <T> Attribute<T> attr(AttributeKey<T> attributeKey) {
        return null;
    }

    @Override
    public <T> boolean hasAttr(AttributeKey<T> attributeKey) {
        return false;
    }
}
