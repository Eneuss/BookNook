package dao;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Opens connections to the SQLite database.
 *
 * <p>The database file location is resolved in this order:
 * <ol>
 *   <li>the {@code booknook.db.path} system property</li>
 *   <li>the {@code BOOKNOOK_DB_PATH} environment variable</li>
 *   <li>{@code ~/.booknook/booknook.db}</li>
 * </ol>
 */
public class DatabaseConnection {
    public static final String DB_PATH_PROPERTY = "booknook.db.path";
    public static final String DB_PATH_ENV = "BOOKNOOK_DB_PATH";

    public static String getDatabasePath() {
        String path = System.getProperty(DB_PATH_PROPERTY);
        if (path == null || path.isBlank()) {
            path = System.getenv(DB_PATH_ENV);
        }
        if (path == null || path.isBlank()) {
            path = Paths.get(System.getProperty("user.home"), ".booknook", "booknook.db").toString();
        }
        return path;
    }

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection("jdbc:sqlite:" + getDatabasePath());
        } catch (ClassNotFoundException e) {
            throw new SQLException("SQLite JDBC Driver not found.", e);
        }
    }
}
