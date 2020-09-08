package com.github.perscholas.service.studentservice;

import com.github.perscholas.dao.StudentDao;
import com.github.perscholas.model.StudentInterface;
import com.github.perscholas.service.StudentService;
import org.junit.Assert;
import org.junit.Test;


/**
 * @author leonhunter
 * @created 02/12/2020 - 8:24 PM
 */ // TODO - Define tests
public class ValidateStudentTest {
    @Test
    public void testValidateStudent(){
        //when
        StudentDao service = new StudentService();
        //then
        Assert.assertFalse(service.validateStudent("deepti.c@gmail.com",""));
        Assert.assertTrue(service.validateStudent("deepti.c@gmail.com","dak"));
    }
}
