package tests.client.net;

import org.junit.jupiter.api.Test;
import src.client.net.Opcode;
import src.client.net.impl.LoginPacket;
import src.client.net.impl.MovementPacket;
import src.client.net.impl.PacketType;

public class PacketTypeTest {


    @Test
    public void testPremade(){
        PacketType p = new LoginPacket(null, "test");
        assert(p.opcode == Opcode.Out.LOGIN.getOpcode());

        PacketType p1 = new MovementPacket(null, 0, 0);
        assert(p1.opcode == Opcode.Out.MOVEMENT.getOpcode());
    }
}
