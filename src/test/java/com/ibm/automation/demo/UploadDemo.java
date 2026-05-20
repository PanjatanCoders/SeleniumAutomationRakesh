package com.ibm.automation.demo;

import org.openqa.selenium.By;

import com.ibm.automation.base.BaseTest;

public class UploadDemo extends BaseTest {
    static void main() {
        setUp();

        driver.findElement(By.id("fileInput")).sendKeys("/home/saddam/Desktop/SeleniumAutomationRakesh/screenshots/Loginpositive.png");




        waitTime(7);

        tearDown();
    }
}
// API, UI, EDI(File based)
// Local Server ---> Shared Location --> Application