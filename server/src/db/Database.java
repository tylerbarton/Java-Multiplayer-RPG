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

    /**
     * Loads a player from the database
     * @param uuid the player id to get
     * @return A player object with loaded data
     */
    public Player queryLoadPlayerData(int uuid){
        // Check if uuid exists
        // TODO: implement
        return null;
    }
}
