package src.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.Server;
import src.db.model.PlayerData;
import src.model.entity.players.Player;

/**
 * Saves & Loads server data
 */
public class Database {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Server server;

    /**
     * Starts a new instance of the database
     * @param server back-link
     */
    public Database(Server server){
        this.server = server;
    }

    /**
     * Saves the player information to the database
     * @param player Player to serialize
     */
    public void querySavePlayerData(Player player) {
        final PlayerData playerData = new PlayerData();
        playerData.uuid = player.getDbUuid();
        playerData.xLocation = player.xPos;
        playerData.yLocation = player.yPos;
    }
}
