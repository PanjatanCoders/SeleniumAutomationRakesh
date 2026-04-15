package com.ibm.automation.demo;

import com.ibm.automation.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ExplicitWaitDemo extends BaseTest {
    static void main() {
        setUp();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

        wait.until(ExpectedConditions.alertIsPresent()); // applicable for alert wait
        wait.until(ExpectedConditions.elementToBeClickable(By.id(""))); //click operation
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("")));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("")));
//        wait.until(ExpectedConditions.);


        clickOperation(By.id("loadContentBtn"));


        tearDown();
    }
}
