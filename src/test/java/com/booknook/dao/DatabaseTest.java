package com.booknook.dao;

import java.nio.file.Path;
import java.sql.SQLException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.io.TempDir;

// gives every test a fresh database created from schema.sql and seed.sql
public abstract class DatabaseTest {

    @TempDir
    Path tempDir;

    @BeforeEach
    void createDatabase() throws SQLException {
        System.setProperty(
                DatabaseConnection.DB_PATH_PROPERTY, tempDir.resolve("test.db").toString());
        DatabaseInitializer.initialize();
    }

    @AfterEach
    void clearDatabaseProperty() {
        System.clearProperty(DatabaseConnection.DB_PATH_PROPERTY);
    }
}
