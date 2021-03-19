package com.github.perscholas;

import com.github.perscholas.utils.ConnectionBuilder;
import com.github.perscholas.utils.IOConsole;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by leon on 2/18/2020.
 */
public enum DatabaseConnection implements DatabaseConnectionInterface {
    MANAGEMENT_SYSTEM,
    UAT;

    private static final IOConsole console = new IOConsole(IOConsole.AnsiColor.CYAN);
    private final ConnectionBuilder connectionBuilder;

    DatabaseConnection(ConnectionBuilder connectionBuilder) {
        this.connectionBuilder = connectionBuilder;
    }

    DatabaseConnection() {
        this(new ConnectionBuilder()
                .setUser("root")
                .setPassword("root")
                .setPort(3306)
                .setDatabaseVendor("mysql")
                .setHost("127.0.0.1"));
    }

    @Override
    public String getDatabaseName() {
        return name().toLowerCase();
    }

    @Override
    public Connection getDatabaseConnection() {
        return connectionBuilder
                .setDatabaseName(getDatabaseName())
                .build();
    }

    @Override
    public Connection getDatabaseEngineConnection() {
        return connectionBuilder.build();
    }

    @Override
    public void create() {
        String sqlStatement = "CREATE DATABASE IF NOT EXISTS " + name().toLowerCase(); // TODO - define statement
        String info;
        try {
            getDatabaseConnection()
                    .prepareStatement(sqlStatement)
                    .execute();
            // TODO - execute statement
            info = "Successfully executed statement `%s`.";
            console.println(info);
        } catch (Exception sqlException) {
            info = "Failed to executed statement `%s`.";
            console.println(info);
        }
        console.println(info, sqlStatement);
    }

    @Override
    public void drop() {
        try {
            String sqlStatement = "DROP DATABASE IF EXISTS " + name().toLowerCase() + ";";
            getDatabaseEngineConnection()
                    .prepareStatement(sqlStatement)
                    .execute();
            String successMessage = String.format("Successfully executed statement \n\t`%s`", sqlStatement);
            console.println(successMessage);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void use() {
        String msg;
        try {
            String sqlStatement = "USE " + name().toLowerCase() + ";";
            getDatabaseConnection()
                    .prepareStatement(sqlStatement)
                    .execute();
            msg = "Successfully executed statement \n\t " + sqlStatement;
            console.println(msg);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void executeStatement(String sqlStatement) {
        try {
            sqlStatement = sqlStatement.trim();
            getScrollableStatement().execute(sqlStatement);
            String successMessage = String.format("Successfully executed statement \n\t`%s`", sqlStatement);
            console.println(successMessage);
        } catch (SQLException e) {
            String errorMessage = String.format("Failed to execute statement \n\t`%s`", sqlStatement);
            throw new Error(errorMessage, e);
        }
    }

    @Override
    public ResultSet executeQuery(String sqlQuery) {
        try {
            sqlQuery = sqlQuery.trim();
            ResultSet result = getScrollableStatement().executeQuery(sqlQuery);
            String successMessage = String.format("Successfully executed query \n\t`%s`", sqlQuery);
            console.println(successMessage);
            return result;
        } catch (SQLException e) {
            String errorMessage = String.format("Failed to execute query \n\t`%s`", sqlQuery);
        }
        return null;
    }


    public Statement getScrollableStatement() {
        int resultSetType = ResultSet.TYPE_SCROLL_INSENSITIVE;
        int resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;
        try {
            return getDatabaseConnection().createStatement(resultSetType, resultSetConcurrency);
        } catch (SQLException e) {
            throw new Error(e);
        }

    }
}