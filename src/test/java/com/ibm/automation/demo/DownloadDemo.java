package com.ibm.automation.demo;

import org.openqa.selenium.By;

import com.ibm.automation.base.BaseTest;

public class DownloadDemo extends BaseTest {
    static void main() {
        setUp();

        driver.findElement(By.xpath("//a[@data-testid='nav-table']")).click();

        driver.findElement(By.id("download-csv")).click();

        waitTime(7);

        tearDown();
    }
}


//Download file from application
//Download file from application and save in local system
//Download file from application and save in local system and verify the file is downloaded or not
//Download file from application and save in local system and verify the file is downloaded or not and delete the file after verification
// Handling WebTables
// Next: Keyboard/Mouse action
// JavaScript Executor
// TestNG Framework
// Cucumber Framework