package com.ibm.automation.demo;

import com.ibm.automation.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.function.Function;

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


    static void fluentWaitDemo() {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30L))
                .pollingEvery(Duration.ofSeconds(5L))
                .ignoring(NoSuchElementException.class);

        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.id("id"));
            }
        });
    }
}
