package com.ibm.automation.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class DemoDropDownHandling {
    static void main() throws InterruptedException {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://panjatan.netlify.app/");
        driver.manage().window().maximize();

        String countryXpath = "//select[@id='country']";
        WebElement countryDd = driver.findElement(By.xpath(countryXpath));
//        Handling DropDown
        Select select =  new Select(countryDd);
//        select.selectByIndex(2);
//        select.selectByVisibleText("India");
        select.selectByValue("ca");
//        select.selectByContainsVisibleText("ted Ki");


        // Fetch all the data from the dropdown
        // selected data/default selected data
        WebElement fistElement = select.getFirstSelectedOption();
        System.out.println(fistElement.getText());

//        fetch all the values from the dropdown
        List<WebElement> ddValues = select.getOptions();

        for (WebElement ddValue : ddValues) {
            System.out.println(ddValue.getText());
        }

        Thread.sleep(2000);
        driver.quit();
    }
}
