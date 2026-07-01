package com.ibm.automation.testngdemo;

import com.ibm.automation.base.BaseTest;
import org.testng.annotations.*;

public class TestNGAttributeDemo extends BaseTest {
    @BeforeClass
    static void beforeClass() {
        System.out.println("Before Class");
    }

    @AfterClass
    static void afterClass() {
        System.out.println("After Class");
    }

    @BeforeMethod
    void beforeMethod1() {
        System.out.println("Before Method");
    }

    @AfterMethod(alwaysRun = true)
    void afterMethod() {
        System.out.println("After Method");
    }

    @Test(priority = 1, description = "attributeDemoTC001: High level Summary of the Test")
    public void attributeDemoTC001() {
        // test steps
        System.out.println("Login TC001 from TestNG");
    }


    @Test(priority = 3, enabled = true, groups = {"sanity", "auth"})
    public void attributeDemoTC002() {
        // test steps
        System.out.println("Login Positive TC002");
    }

    @Test(
            priority = 2,
            invocationCount = 3,
            timeOut = 100000
    )
    public void attributeDemoTC003() {
        System.out.println("Inside negative TC003");
        String randId = String.valueOf(Math.random());
    }

    @Test(
            priority = 4,
            dependsOnMethods = "attributeDemoTC003",
            alwaysRun = true

    )
    public void attributeDemoTC004() {
        System.out.println("Inside negative TC004");
    }

    public void bookingEndToEnd() {
        // create booking createMethod();
        // confirm booking method();
    }

    public int createMethod() {
        return 0;
    }
}
