package tests.net;

import org.junit.jupiter.api.Test;
import src.net.Packet;
import src.net.PacketBuilder;

import java.util.Objects;

/**
 * Test the ability to make and deconstruct packets
 */
public class PacketBuilderTest {
    @Test
    void putInt(){
        PacketBuilder builder = new PacketBuilder(127);
        builder.writeInt(355);
        Packet p = builder.getPacket();
        assert(p.readInt() == 355);
    }

    @Test
    void putString(){
        String mesg = "packet builder test";
        PacketBuilder builder = new PacketBuilder(127);
        builder.writeString(mesg);
        Packet p = builder.getPacket();
        assert(Objects.equals(p.readString(), mesg));

    }
}
