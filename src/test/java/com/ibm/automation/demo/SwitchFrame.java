package com.ibm.automation.demo;

import com.ibm.automation.base.BaseTest;
import org.openqa.selenium.By;


public class SwitchFrame extends BaseTest {
    static void main() {
        setUp();

        //finding an iframe and switching to it
        driver.switchTo().frame("testIframe") ;

        String iframetext = driver.findElement(By.xpath("//h2[.='Iframe Content']")).getText() ;

        System.out.println(iframetext);

        driver.findElement(By.id("iframeBtn")).click();

        driver.switchTo().alert().getText();
        driver.switchTo().alert().accept();

        driver.findElement(By.id("iframeInput")).sendKeys("helloI");

        System.out.println(driver.findElement(By.id("iframeText")).getText()) ;
        tearDown();
    }

}
