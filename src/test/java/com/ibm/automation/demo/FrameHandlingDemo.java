package com.ibm.automation.demo;

import com.ibm.automation.base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class FrameHandlingDemo extends BaseTest {
    static void main() {
        setUp();

//        switch by id/name
//        driver.switchTo().frame("testIframe");
//        Switch by xpath
        WebElement iframe = driver.findElement(By.xpath("//h3[.='Iframe Interaction']/following-sibling::div/iframe"));
        driver.switchTo().frame(iframe);

        String text = driver.findElement(By.xpath("//h2[.='Iframe Content']")).getText();
        System.out.printf("Actual Iframe Content: %s\n", text);

//        get out of the frame
        driver.switchTo().defaultContent();

//        to switch to parent frame
//        driver.switchTo().parentFrame();


        System.out.println(driver.findElement(By.xpath("//section[@id='advanced']/div/h2")).getText());
        tearDown();
    }
}
