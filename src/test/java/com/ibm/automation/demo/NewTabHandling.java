package com.ibm.automation.demo;

import com.ibm.automation.base.BaseTest;
import org.openqa.selenium.By;

import java.util.Set;

public class NewTabHandling extends BaseTest {
    static void main() {
        setUp();
        String mainWinId = driver.getWindowHandle();

        clickOperation(By.id("openTabBtn"));

//        handle new window
        Set<String> windows = driver.getWindowHandles();
//        remove the main window from the list of windows
        windows.remove(mainWinId);

        String targetWindow = windows.iterator().next();

        driver.switchTo().window(targetWindow);
        System.out.println(driver.getTitle());
        driver.navigate().to("https://www.google.com");
        System.out.println(driver.getTitle());

        driver.close();

        driver.switchTo().window(mainWinId);
        System.out.println(driver.findElement(By.id("openTabBtn")).isDisplayed());

        tearDown();
    }
}
