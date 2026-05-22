package com.ibm.automation.demo;

import com.ibm.automation.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class ActionClassDemo extends BaseTest {
    static void main() {
        setUp();

        waitTime(3);
        handleDragAndDrop();
        waitTime(2);
        tearDown();
    }

    static void handleDragAndDrop() {
        String srcItem4Xpath = "//div[@data-testid='drag-item-4']";
        String destId = "dropZone";

        Actions actions = new Actions(driver);
//        actions.scrollToElement(driver.findElement(By.xpath(srcItem4Xpath))).perform();
//        driver.findElement(By.xpath(srcItem4Xpath)).getText(); --> target element

//        actions.moveToElement(driver.findElement(By.id(destId))).perform();
        actions.moveToElement(driver.findElement(By.xpath(srcItem4Xpath)))
                .clickAndHold()
                .moveToElement(driver.findElement(By.id(destId)))
                .release()
                .build()
                .perform();
    }
}
// Mosehover
// drag and drop
// scrollToElement