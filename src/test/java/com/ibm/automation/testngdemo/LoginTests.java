package com.ibm.automation.testngdemo;

import org.testng.annotations.*;

public class LoginTests {
    @BeforeClass
    static void beforeClass() {
        System.out.println("Before Class");
    }

    @AfterClass
    static void afterClass() {
        System.out.println("After Class");
    }

    @BeforeMethod
    void beforeMethod() {
        System.out.println("Before Method");
    }

    @AfterMethod
    void afterMethod() {
        System.out.println("After Method");
    }

    @Test
    public void loginPositiveTC001() {
        // test steps
        System.out.println("Login TC001");
    }


    @Test
    public void loginPositiveTC002() {
        // test steps
        System.out.println("Login Positive TC002");
    }

    @Test
    public void loginNegativeTC003() {
        System.out.println("Inside negative TC003");
    }
}
