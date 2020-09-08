package com.github.perscholas;

import com.github.perscholas.utils.DirectoryReference;
import com.github.perscholas.utils.FileReader;
import com.mysql.cj.jdbc.Driver;

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

    private static final DatabaseConnection dc = DatabaseConnection.MANAGEMENT_SYSTEM;

    public static void initialize() {
        dc.drop();
        dc.create();
        dc.use();
        executeSqlFile("courses.create-table.sql");
        executeSqlFile("courses.populate-table.sql");
        executeSqlFile("students.create-table.sql");
        executeSqlFile("students.populate-table.sql");
        //created Student course table and added data into the table
        executeSqlFile("student_course.create-table.sql");
        executeSqlFile("student_course.populate-table.sql");
    }

    private static void executeSqlFile(String fileName) {
        File creationStatementFile = DirectoryReference.RESOURCE_DIRECTORY.getFileFromDirectory(fileName);
        FileReader fileReader = new FileReader(creationStatementFile.getAbsolutePath());
        String[] statements = fileReader.toString().split(";");
        for (int i = 0; i < statements.length; i++) {
            String statement = statements[i];
            dc.executeStatement(statement);
        }
    }
}
