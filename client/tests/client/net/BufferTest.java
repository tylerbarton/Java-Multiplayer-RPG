package tests.client.net;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import src.client.net.PacketBuffer;

/**
 * Tests the ability to create and read from a buffer
 * @author Tyler Barton
 */
public class BufferTest {

    @Test
    public void readAndWrite(){
        PacketBuffer buffer = new PacketBuffer(1024);
        buffer.writeInt(66);
        Assertions.assertEquals(66, buffer.readInt());
    }
}
