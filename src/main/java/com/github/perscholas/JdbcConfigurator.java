package com.github.perscholas;

import com.mysql.jdbc.Driver;

import com.github.perscholas.utils.DirectoryReference;
import com.github.perscholas.utils.FileReader;

import java.io.File;
import java.sql.DriverManager;

public class JdbcConfigurator {
    static {
        try {
            // TODO - Attempt to register JDBC Driver
            DriverManager.registerDriver(Driver.class.newInstance());
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    private static final DatabaseConnection dbc = DatabaseConnection.MANAGEMENT_SYSTEM;

    public static void initialize() {
        dbc.drop();
        dbc.create();
        dbc.use();
        executeSqlFile("courses.create-table.sql");
        executeSqlFile("courses.populate-table.sql");
        executeSqlFile("students.create-table.sql");
        executeSqlFile("students.populate-table.sql");
        executeSqlFile("intermediate.student-course.sql");
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
