package src.net;

import io.netty.buffer.ByteBuf;
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
     * @param byteBuf Bytes
     * @param list Objects sent in the pipeline to the next item (ConnectionHandler)
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {

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
