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
        /**
         * desc: Sent to login to the server with a username
         * args: string username
         */
        LOGIN(0),

        /**
         * desc: Pings the server to confirm connection
         */
        PING(1),

        /**
         * desc: When connection is lost, tries to rejoin the server with the saved username
         */
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
        /**
         * desc: Received from the server to confirm login
         */
        OPEN_CONNECTION_NOTIFY(3),
        /**
         * desc: Received from the server to confirm logout
         */
        CLOSE_CONNECTION_NOTIFY(4),
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
