package com.ibm.automation.demo;

import com.ibm.automation.base.BaseTest;
import org.openqa.selenium.By;

public class ModalWindowHandling extends BaseTest {
    static void main() {
        setUp();

        driver.findElement(By.id("modalBtn")).click();
        String modalTitle = driver.findElement(By.xpath("//h3[.='Test Modal']")).getText();

        System.out.printf("modalTitle: %s\n", modalTitle);

        waitTime(2);
        driver.findElement(By.id("closeModal")).click();

        tearDown();
    }
}
