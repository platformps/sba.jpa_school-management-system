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
                .setPassword("admin")
                .setPort(3306)
                .setDatabaseVendor("mariadb")
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
        String sqlStatement = "CREATE DATABASE IF NOT EXISTS " + getDatabaseName() + ";"; // TODO - define statement
        String info;
        try {
            executeStatement(sqlStatement);// TODO - execute statement
            info = "Successfully executed statement `%s`.";
        } catch (Exception sqlException) {
            info = "Failed to executed statement `%s`.";
        }
        console.println(info, sqlStatement);
    }

    @Override
    public void drop() {
        String sqlStatement = "DROP DATABASE IF EXISTS " + getDatabaseName() + ";"; // TODO - define statement
        String info;
        try {
            executeStatement(sqlStatement);// TODO - execute statement
            info = "Successfully executed statement `%s`.";
        } catch (Exception sqlException) {
            info = "Failed to executed statement `%s`.";
        }
        console.println(info, sqlStatement);
    }

    @Override
    public void use() {
        String sqlStatement = "USE " + getDatabaseName() + ";"; // TODO - define statement
        String info;
        try {
            executeStatement(sqlStatement);// TODO - execute statement
            info = "Successfully executed statement `%s`.";
        } catch (Exception sqlException) {
            info = "Failed to executed statement `%s`.";
        }
        console.println(info, sqlStatement);
    }

    @Override
    public void executeStatement(String sqlStatement) {
        String info;
        try {
            getDatabaseEngineConnection().createStatement().execute(sqlStatement);
            info = "Successfully executed statement `%s`.";
        } catch (SQLException e) {
            info = "Failed to executed statement `%s`.";
            console.println(info, sqlStatement);
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResultSet executeQuery(String sqlQuery) {
        String info;
        try {
            return getDatabaseConnection().createStatement().executeQuery(sqlQuery);
        } catch (SQLException e) {
            info = "Failed to execute query `%s`.";
            console.println(info, sqlQuery);
            throw new RuntimeException(e);
        }
    }
}