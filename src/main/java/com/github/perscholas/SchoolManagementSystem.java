package com.github.perscholas;

import com.github.perscholas.dao.StudentDao;
import com.github.perscholas.model.CourseInterface;
import com.github.perscholas.service.CourseService;
import com.github.perscholas.service.StudentService;
import com.github.perscholas.utils.IOConsole;

import java.util.List;
import java.util.stream.Collectors;

public class SchoolManagementSystem implements Runnable {
    private static final IOConsole console = new IOConsole();

    @Override
    public void run() {
        String smsDashboardInput;
        do {
            smsDashboardInput = getSchoolManagementSystemDashboardInput();
            if ("login".equals(smsDashboardInput)) {
                StudentDao studentService = new StudentService();
                String studentEmail = console.getStringInput("Enter your email:");
                String studentPassword = console.getStringInput("Enter your password:");
                Boolean isValidLogin = studentService.validateStudent(studentEmail, studentPassword);
                //Handled the invalid case
                if (isValidLogin) {
                    String studentDashboardInput = getStudentDashboardInput(studentEmail);
                    if ("register".equals(studentDashboardInput)) {
                        Integer courseId = getCourseRegistryInput();
                        studentService.registerStudentToCourse(studentEmail, courseId);
                        String studentCourseViewInput = getCourseViewInput();
                        //Formatted the output based on the example workflow
                        if ("view".equals(studentCourseViewInput)) {
                            List<String> courses =  new StudentService().getStudentCourses(studentEmail)
                                    .stream()
                                    .map(course -> String.format("%-5s %-15s %-10s", course.getId().toString() , course.getName()  , course.getInstructor().toString()))
                                    .collect(Collectors.toList());
                            console.println(new StringBuilder()
                                    .append( studentEmail +" is registered to the following courses:")
                                    .append("\n\t" + String.format("%-5s %-15s %-10s", "ID", "Course Name", "Instructor Name"))
                                    .append("\n\t" + courses
                                            .toString()
                                            .replaceAll("\\[", "")
                                            .replaceAll("\\]", "")
                                            .replaceAll(", ", "\n\t"))
                                    .toString(), studentEmail);
                        }
                    }
                } else {
                    console.println("Invalid Login Details, please try again: \n");
                }
            }
        } while (!"logout".equals(smsDashboardInput));
    }

    private String getCourseViewInput() {
        return console.getStringInput(new StringBuilder()
                .append("Welcome to the Course View Dashboard!")
                .append("\nFrom here, you can select any of the following options:")
                .append("\n\t[ view ], [ logout ]")
                .toString());
    }

    private String getSchoolManagementSystemDashboardInput() {
        return console.getStringInput(new StringBuilder()
                .append("Welcome to the School Management System Dashboard!")
                .append("\nFrom here, you can select any of the following options:")
                .append("\n\t[ login ], [ logout ]")
                .toString());
    }

    //Formatted the output based on the example workflow
    private String getStudentDashboardInput(String studentEmail) {
        List<String> listOfStudentClass = new StudentService().getStudentCourses(studentEmail)
                .stream()
                .map(classes -> String.format("%-5s %-15s %-10s", classes.getId().toString() , classes.getName()  , classes.getInstructor().toString()))
                .collect(Collectors.toList());
        return console.getStringInput(new StringBuilder()
                .append("My Classes: ")
                .append("\n\t" + String.format("%-5s %-15s %-10s", "ID", "Course Name", "Instructor Name"))
                .append("\n\t" + listOfStudentClass
                        .toString()
                        .replaceAll("\\[", "")
                        .replaceAll("\\]", "")
                        .replaceAll(", ", "\n\t"))
                .append("\n\nWelcome to the Course Registration Dashboard!")
                .append("\nFrom here, you can select any of the following options: ")
                .append("\n\t[ register ], [ logout]")
                .toString());
    }

    //Formatted the output based on the example workflow
    private Integer getCourseRegistryInput() {
        List<String> listOfCoursesIds = new CourseService().getAllCourses()
                .stream()
                .map(course -> String.format("%-10s %-30s %-20s",course.getId().toString() , course.getName().toString()  , course.getInstructor().toString()))
                .collect(Collectors.toList());
        return console.getIntegerInput(new StringBuilder()
                .append("Welcome to the Course Registration Dashboard!")
                .append("\nFrom here, you can select any of the following options:")
                .append("\nAll Courses: \n")
                .append(String.format("%-5s %-15s %-10s", "ID", "Course Name", "Instructor Name"))
                .append("\n\t" + listOfCoursesIds
                        .toString()
                        .replaceAll("\\[", "")
                        .replaceAll("\\]", "")
                        .replaceAll(", ", "\n\t"))
                .toString());
    }
}
