package com.ibm.automation.demo;

import com.ibm.automation.base.BaseTest;
import org.openqa.selenium.By;

import java.util.Set;

public class WindowSwitchDemo extends BaseTest {
    static void main() {
        setUp();

        String mainWindow = driver.getWindowHandle(); // return current window id
        System.out.printf("Main Window: %s\n", mainWindow);

        driver.findElement(By.id("openWindowBtn")).click();
//        Switch to new Window
//        driver.getWindowHandle();

        Set<String> windowSet = driver.getWindowHandles(); // returns the set of ids of windows opened

        System.out.printf("All Windows: %s\n", windowSet);
        windowSet.remove(mainWindow);
        System.out.printf("Current Window: %s\n", windowSet);

        String newWindow = windowSet.iterator().next();

        driver.switchTo().window(newWindow);
        System.out.println(driver.findElement(By.xpath("//p[.='This is a new window for testing.']")).getText());
//        driver.findElement(By.xpath("//button[.='Close Window']")).click();

//        perform some action in main window
//        driver.switchTo().window(mainWindow);
        System.out.println(driver.findElement(By.xpath("//h3[.='Window Handling']")).getText());


        tearDown();
    }
}
