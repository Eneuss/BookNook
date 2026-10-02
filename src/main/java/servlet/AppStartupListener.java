package servlets;

import dao.DatabaseConnection;
import dao.DatabaseInitializer;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.sql.SQLException;
import java.util.logging.Logger;

//creates and seeds the database on first start
@WebListener
public class AppStartupListener implements ServletContextListener {
    private static final Logger LOG = Logger.getLogger(AppStartupListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try {
            DatabaseInitializer.initialize();
            LOG.info("Using database " + DatabaseConnection.getDatabasePath());
        } catch (SQLException e) {
            throw new IllegalStateException("Database initialization failed", e);
        }
    }
}
