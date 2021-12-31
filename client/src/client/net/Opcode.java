package src.client.net;

/**
 * Contains a list of opcodes that the client will use
 * @author Tyler Barton
 */
public class Opcode {

    /**
     * Outgoing opcodes
     */
    public enum Out {
        LOGIN(0),
        PING(1),
        RECONNECT(2),
        ;

        private int opcode;

        /**
         * @param opcode Integer value associated
         */
        Out(int opcode) {
            this.setOpcode(opcode);
        }

        /**
         * @return Integer value
         */
        public int getOpcode() {
            return opcode;
        }

        /**
         * @param opcode Integer representation
         */
        public void setOpcode(int opcode) {
            this.opcode = opcode;
        }
    }
    /**
     * Incoming opcodes
     */
    public enum In {
        OPEN_CONNECTION_NOTIFY(3),  // Received on Login
        CLOSE_CONNECTION_NOTIFY(4), // Logout
        ;

        private int opcode;

        /**
         * @param opcode Integer value associated
         */
        In(int opcode) {
            this.setOpcode(opcode);
        }

        /**
         * @return Integer value
         */
        public int getOpcode() {
            return opcode;
        }

        /**
         * @param opcode Integer representation
         */
        public void setOpcode(int opcode) {
            this.opcode = opcode;
        }

    }

}
