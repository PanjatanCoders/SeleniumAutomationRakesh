package com.ibm.automation.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class BaseTest {
    protected static WebDriver driver;

    static public void setUp() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
//        global wait -applicable to all the find elements
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
        driver.get("https://panjatan.netlify.app/");
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

    static public void clickOperation(By locator){
        driver.findElement(locator).click();
    }

    public static String getText(By locator){
        return driver.findElement(locator).getText();
    }
}

// Exception, Error
// Exception: Checked(Compile time), UnChecked Exception