package com.ibm.automation.demo;

import com.ibm.automation.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AutoSuggestInput extends BaseTest {

    static void main() {
        setUp();

        driver.findElement(By.id("autocompleteInput")).sendKeys("tes");
        List<WebElement> listOfData = driver.findElements(By.xpath("//div[@id='autocompleteList']/div"));
        for (WebElement element : listOfData) {
            if (element.getText().equals("Kubernetes")) {
                element.click();
                break;
            }
        }
        String text = driver.findElement(By.id("selectedValue")).getText();
        System.out.println("Selected value: " + text);

        tearDown();
    }
}
