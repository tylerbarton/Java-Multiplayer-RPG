package src.net;

import src.model.entity.players.Player;
import java.util.concurrent.atomic.AtomicReference;

/**
 * An object mapped to each connection channel to manage sessions
 */
public class ConnectionAttachment {
    public AtomicReference<Player> player = new AtomicReference<>();
    public AtomicReference<Integer> sessionId = new AtomicReference<>();
    public AtomicReference<Boolean> canSendSessionId = new AtomicReference<>();
}