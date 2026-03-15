package com.ibm.automation.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Demo3FindElements {
    static void main() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://panjatan.netlify.app/");
        driver.manage().window().maximize();

//        findElements
        List<WebElement> links = driver.findElements(By.tagName("a"));
        // iterate the list and check if the links are valid
        //
         //
        for (WebElement link : links) {
            System.out.println(link.getText());
        }

    }
}
