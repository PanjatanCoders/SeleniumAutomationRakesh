package com.ibm.automation.demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Demo1Intoduction {
    static void main() {
        WebDriver driver = new ChromeDriver(); //Launch a browser

//        ChromeDriver driver = new ChromeDriver(); //Launch a browser
//        driver = new FirefoxDriver();

//        maximize the browser window
        driver.manage().window().maximize();

//        Navigate to an url
        driver.get("https://www.google.com");
        System.out.println(driver.getTitle());
        driver.close();
    }
}


/*
    1. Launch a browser
    2. Navigate to an application url
    3. Perform some action
    4. Close the browser
 */