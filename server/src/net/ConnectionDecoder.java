package src.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.AttributeMap;

import java.util.List;

/**
 * Decodes transmitted data to translate byte data to a packet object
 */
public class ConnectionDecoder extends ByteToMessageDecoder implements AttributeMap {
    /**
     * Decodes the bytes transmitted over the channel
     * @param ctx Channel context
     * @param byteBuf Bytes buffer to be decoded to a packet
     * @param out Objects sent out in the pipeline to the next item (ConnectionHandler)
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
        //System.out.println("Starting decoder.");
        final Channel channel = ctx.channel();
        if(byteBuf.readableBytes() > 2) {
            byteBuf.markReaderIndex();
            int length = byteBuf.readUnsignedShort();   // Length does not include itself
            int opcode = byteBuf.readByte() & 0xFF;

            try{
                if(length > 0){
                    ByteBuf data = Unpooled.buffer(length-1);   // -1 for opcode
                    byteBuf.readBytes(data, length-1);
                    out.add(new Packet(opcode, data, length-1));
                }
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        } else {
           byteBuf.resetReaderIndex();
        }

        // Add packet to the out list
//        Packet packet = new Packet();
//        out.add(packet);
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
