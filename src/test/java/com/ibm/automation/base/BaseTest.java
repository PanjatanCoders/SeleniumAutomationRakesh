package com.ibm.automation.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseTest {
    protected static WebDriver driver;

    static public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://panjatan.netlify.app/");
        driver.manage().window().maximize();
    }

    static public void tearDown(){

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("There is an exception");
        }
        driver.quit();
    }
}

// Exception, Error
// Exception: Checked(Compile time), UnChecked Exception