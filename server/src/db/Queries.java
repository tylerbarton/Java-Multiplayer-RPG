package src.db;

import src.Config;

/**
 * Predefined queries to be called
 */
public class Queries {
    public static final String PREFIX = Config.DATABASE_NAME + '.';
    public static final String save_PlayerData = "INSERT INTO `" + "players` VALUES (?, ?, ?, ?)";
    public static final String load_PlayerDataByUser = "SELECT `uuid`, `username`, `xLocation`, `yLocation`"
            + "FROM `players` WHERE `username`=?";
    public static final String load_PlayerDataById = "SELECT `uuid`, `username`, `xLocation`, `yLocation`"
            + "FROM `players` WHERE `uuid`=?";
}
