package com.ibm.automation.testngdemo;

import com.ibm.automation.base.BaseTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class AlertHandlingWithTestNG extends BaseTest {

    @BeforeMethod
    public void beforeMethod() {
        setUp();
    }

    @AfterMethod
    public void afterMethod() {
        tearDown();
    }

    @Test
    static void verifyPromptAlert() {
        driver.findElement(By.id("promptBtn")).click();

        Alert alert = driver.switchTo().alert();
        alert.sendKeys("Selenium Alert");
        alert.accept();

        String text = driver.findElement(By.xpath("//div[@id='alertResult']/p")).getText();
        System.out.printf("Text: %s\n", text);
    }


    @Test
    static void verifyInfoAlert() {
        driver.findElement(By.id("alertBtn")).click();
//        Switch to alert
        Alert alert = driver.switchTo().alert();

        waitTime(2);

        String alertText = alert.getText();
        System.out.printf("Alert text: %s\n", alertText);
        alert.accept();
    }
}
