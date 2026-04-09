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
    static public void waitTime(int sec) {
        try {
            Thread.sleep(sec  * 1000);
        } catch (InterruptedException e) {
            System.out.println("There is an exception");
        }

    }
    static public void tearDown(){
        waitTime(3);
        driver.quit();
    }
}

// Exception, Error
// Exception: Checked(Compile time), UnChecked Exception