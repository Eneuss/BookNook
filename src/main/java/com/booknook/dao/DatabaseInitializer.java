package com.booknook.dao;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Creates the database schema and loads demo data when the database is empty.
 */
public final class DatabaseInitializer {

    private DatabaseInitializer() {}

    // create and seed the database if it has no tables yet
    public static void initialize() throws SQLException {
        createParentDirectory();
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (isInitialized(conn)) {
                return;
            }
            conn.setAutoCommit(false);
            try {
                runScript(conn, "/db/schema.sql");
                runScript(conn, "/db/seed.sql");
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    private static void createParentDirectory() throws SQLException {
        Path parent =
                Paths.get(DatabaseConnection.getDatabasePath()).toAbsolutePath().getParent();
        try {
            if (parent != null) {
                Files.createDirectories(parent);
            }
        } catch (IOException e) {
            throw new SQLException("Cannot create database directory " + parent, e);
        }
    }

    private static boolean isInitialized(Connection conn) throws SQLException {
        try (Statement stmt = conn.createStatement();
                ResultSet rs =
                        stmt.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name = 'Users'")) {
            return rs.next();
        }
    }

    private static void runScript(Connection conn, String resource) throws SQLException {
        String script;
        try (InputStream in = DatabaseInitializer.class.getResourceAsStream(resource)) {
            if (in == null) {
                throw new SQLException("Missing resource " + resource);
            }
            script = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new SQLException("Cannot read " + resource, e);
        }

        // drop comment lines, then run each statement
        StringBuilder sql = new StringBuilder();
        for (String line : script.split("\n")) {
            if (!line.trim().startsWith("--")) {
                sql.append(line).append('\n');
            }
        }
        try (Statement stmt = conn.createStatement()) {
            for (String statement : sql.toString().split(";")) {
                if (!statement.isBlank()) {
                    stmt.executeUpdate(statement);
                }
            }
        }
    }
}
