package src.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.client.net.Opcode;

/**
 * Gives each client (player) a unique session id
 * @author Tyler Barton
 * ref: https://stackoverflow.com/questions/18262926/howto-get-some-id-for-a-netty-channel
 */
public class SessionIdHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AttributeKey<ConnectionAttachment> attachment = AttributeKey.valueOf("conn-attachment");
    private ChannelHandlerContext ctx;

    public SessionIdHandler(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    /**
     * Session ID creator on a new thread
     */
    @Override
    public void run() {
        ConnectionAttachment att = ctx.channel().attr(SessionIdHandler.attachment).get();

        if (att.canSendSessionId.get()) {
            // Generate a random session id for each client
            int sessionId = ctx.channel().id().hashCode();
            att.sessionId.set(sessionId);

            // Send back the session id
            if (ctx.channel().isOpen() && ctx.channel().isWritable()) {
                PacketBuilder packetFactory = new PacketBuilder(Opcode.In.OPEN_CONNECTION_NOTIFY.getOpcode());
                packetFactory.writeInt(sessionId);
                //Unpooled.buffer(4).writeInt(sessionId)
                ctx.writeAndFlush(packetFactory.getPacket()); //8bits*4 for session id
                System.out.println("Sent session id");
            }

            LOGGER.info("Set " + ctx.channel().remoteAddress() + " session id: " + sessionId);
        }
    }
}
