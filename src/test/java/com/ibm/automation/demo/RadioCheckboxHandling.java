package com.ibm.automation.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class RadioCheckboxHandling {
    static void main() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://panjatan.netlify.app/");
        driver.manage().window().maximize();

        WebElement maleRadioBtn = driver.findElement(By.id("male"));
        System.out.println("MaleRadioBtn Selected status before select: " +  maleRadioBtn.isSelected());
        maleRadioBtn.click();

        System.out.println("MaleRadioBtn Selected status after select: " +  maleRadioBtn.isSelected());

        System.out.println("=".repeat(40));

        WebElement testingCB = driver.findElement(By.id("testing"));
        testingCB.click();
        System.out.println("Testing CB Selected status after select: " +  testingCB.isSelected());

        Thread.sleep(3000);
        driver.quit();
    }
}
// click(), sendKeys(), isSelected(), isDisplayed(), isEnabled()