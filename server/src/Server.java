package src;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.db.Database;
import src.model.world.World;
import src.net.ConnectionHandler;
import src.util.logging.LogUtil;

import java.net.InetSocketAddress;

import static org.apache.logging.log4j.util.Unbox.box;

/**
 * Main server controller
 * @Author Tyler Barton
 * @ImplNote Server implements Runnable and has exposed methods (init, start) for testing purposes
 */
public class Server implements Runnable {
    private boolean running = false;
    private long startTime;
    private World world;
    private Database database;
    public final static Logger LOGGER;

    public Database getDatabase(){return this.database;}
    public void openDatabase(){database = new Database(this);
        database.open();}

    // Logger configuration
    static {
        LogUtil.configure();
        LOGGER = LogManager.getLogger();
    }

    // If we wanted multiple worlds, we could implement runnable in this class
    // Then use a ConcurrentHashMap
    public static void main(String[] args){
        Server s = new Server();
        s.init();
        s.start();

        // Keep the application running while the server is alive
        while (s.running) {
            try {
                Thread.sleep(1000);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return The run status of the server.
     */
    public boolean isRunning(){ return running; }

    /**
     * Initialize the server instance
     */
    public void init(){
        // TODO: Initialize entity information
        // TODO: deserialize SQL state information
        startTime = System.currentTimeMillis();
        LOGGER.info("Started server.");
        openDatabase();
        this.initConnection();
    }

    /**
     * Starts the server
     */
    public void start(){
        this.run();
    }

    /**
     * Clean up server objects
     */
    public void stop(){
        database.close();
    }

    /**
     * Initialize the connection
     */
    private void initConnection(){
        EventLoopGroup bossGroup = new NioEventLoopGroup(0);
        EventLoopGroup workerGroup = new NioEventLoopGroup(0);
        final ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(
                new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(final SocketChannel channel) {
                        final ChannelPipeline pipeline = channel.pipeline();
                        channel.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(30));
                        pipeline.addLast("handler", new ConnectionHandler(null));
                    }
                }
        );

        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, false);
        bootstrap.childOption(ChannelOption.SO_RCVBUF, 10000);
        bootstrap.childOption(ChannelOption.SO_SNDBUF, 10000);

        try {
            ChannelFuture serverChannel = bootstrap.bind(new InetSocketAddress(Config.PORT)).sync();
            LOGGER.info("Game world is now online on port {}!", box(Config.PORT));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs the server's main loop
     * @implNote Naming convention is intention as multi-threading support can be added
     */
    @Override
    public void run(){
        this.running = true;
        while(running){
            // TODO: Process incoming packets
            // TODO: Game loop + Game Events
            // TODO: World updating + Movement updating
            // TODO: Process outgoing packets
            // TODO: Book keeping
            try{

            }catch(final Throwable t){
                LOGGER.catching(t);
            }
        }
        stop();
    }
}
