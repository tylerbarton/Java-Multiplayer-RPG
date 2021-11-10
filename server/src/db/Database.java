package src.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import src.Config;
import src.Server;
import src.db.model.PlayerData;
import src.model.entity.players.Player;

import java.sql.*;

/**
 * Saves & Loads server data to a MySql server
 * ref: https://stackoverflow.com/questions/2256504/insert-java-variable-using-java-in-sql
 */
public class Database {
    private static final Logger LOGGER = LogManager.getLogger();
    private final Server server;

    private boolean connected;
    private Connection connection;
    private Statement statement;

    /**
     * Starts a new instance of the database
     * @param server back-link
     */
    public Database(Server server){
        this.server = server;
    }

    /**
     * Open a connection to the MySql database.
     */
    public void open(){
        try{
            String url = "jdbc:mysql://" + Config.DATABASE_HOST + '/' + Config.DATABASE_NAME + "?autoReconnect=true&useSSL=false&useTimezone=true&serverTimezone=UTC";
            System.out.println(url);
            connection = DriverManager.getConnection(url, Config.DATABASE_USER, Config.DATABASE_PASS);
            statement = connection.createStatement();
            statement.setEscapeProcessing(true);
            connected = true;
            LOGGER.info("Connection to database opened: " + Config.DATABASE_HOST + '/' + Config.DATABASE_NAME);
        } catch(SQLException e){
            LOGGER.throwing(e);
        }
    }

    /**
     * Close the connection to the MySql Database.
     */
    public void close(){
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        LOGGER.info("Connection to database closed.");
    }

    /**
     * Updates the status of connection to the database server.
     * @return true if there is an active connection to the database, else false.
     * ref: https://stackoverflow.com/questions/55627787/checking-connection-to-mysql-java
     */
    public boolean isConnected(){
        try {
            if(connection == null) return false;
            return !connection.isClosed();
            //statement.executeQuery("SELECT CURRENT_DATE");
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Executes a query on the database
     * @return Results of the query
     * @throws SQLException
     */
    public ResultSet executeQuery(final String query) throws SQLException {
        return statement.executeQuery(query);
    }

    // TODO: Move queries to another class?

    /**
     * Saves the player information to the database
     * @param player Player to serialize
     */
    public void querySavePlayerData(Player player) {
        final PlayerData playerData = new PlayerData();
        playerData.uuid = player.getDbUuid();
        playerData.username = player.username;
        playerData.xLocation = player.xPos;
        playerData.yLocation = player.yPos;

        // TODO: Separate these
        try {
            PreparedStatement pstatement = connection.prepareStatement(Queries.save_PlayerData);
            pstatement.setInt(1, playerData.uuid);
            pstatement.setString(2, playerData.username);
            pstatement.setInt(3, playerData.xLocation);
            pstatement.setInt(4, playerData.yLocation);
            pstatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads a player from the database
     * @param id the player id to get
     * @return A player object with loaded data
     * ref: https://stackoverflow.com/questions/43539483/how-to-get-values-of-script-sql-to-an-object-java
     */
    public Player queryLoadPlayerDataById(int id){
        Player player;
        PlayerData playerData;

        try {
            PreparedStatement pstate = connection.prepareStatement(Queries.load_PlayerDataById);
            pstate.setInt(1, id);
            ResultSet results = pstate.executeQuery();
            if(results.next()){
                PlayerData data = new PlayerData();
                data.uuid = results.getInt(1);
                data.username = results.getString(2);
                data.xLocation = results.getInt(3);
                data.yLocation = results.getInt(4);
                return(data.toPlayer());
            } else {
                LOGGER.warn("Unable to load player: id=" + id);
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
