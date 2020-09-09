package com.github.perscholas;

import com.github.perscholas.utils.DirectoryReference;
import com.github.perscholas.utils.FileReader;
import com.mysql.cj.jdbc.Driver;

import java.io.File;
import java.sql.DriverManager;

public class JdbcConfigurator {
    static {
        try {
            DriverManager.registerDriver(Driver.class.newInstance());// TODO - Attempt to register JDBC Driver
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    private static final DatabaseConnection dbc = DatabaseConnection.school_sba;

    public static void initialize() {
        dbc.drop();
        dbc.create();
        dbc.use();
        executeSqlFile("courses.create-table.sql");
        executeSqlFile("courses.populate-table.sql");
        executeSqlFile("students.create-table.sql");
        executeSqlFile("students.populate-table.sql");
    }

    private static void executeSqlFile(String fileName) {
        File creationStatementFile = DirectoryReference.RESOURCE_DIRECTORY.getFileFromDirectory(fileName);
        FileReader fileReader = new FileReader(creationStatementFile.getAbsolutePath());
        String[] statements = fileReader.toString().split(";");
        for (int i = 0; i < statements.length; i++) {
            String statement = statements[i];
            dbc.executeStatement(statement);
        }
    }
}
