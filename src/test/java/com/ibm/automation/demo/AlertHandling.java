package com.ibm.automation.demo;

import com.ibm.automation.base.BaseTest;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;

public class AlertHandling extends BaseTest {
    static void main() {
        setUp();

//        verifyInfoAlert();

        verifyPromptAlert();

        tearDown();
    }

    static void verifyPromptAlert() {
        driver.findElement(By.id("promptBtn")).click();

        Alert alert = driver.switchTo().alert();
        alert.sendKeys("Selenium Alert");
        alert.accept();

        String text = driver.findElement(By.xpath("//div[@id='alertResult']/p")).getText();
        System.out.printf("Text: %s\n", text);
    }

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
