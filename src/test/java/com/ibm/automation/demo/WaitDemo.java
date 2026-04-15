package com.ibm.automation.demo;

import com.ibm.automation.base.BaseTest;
import org.openqa.selenium.By;

public class WaitDemo extends BaseTest {
    static void main() {
        setUp();

        clickOperation(By.id("loadContentBtn"));
        String text = getText(By.xpath("//p[starts-with(., 'Loaded at')]"));
        System.out.println("Text fetched: " + text);

        tearDown();
    }
}
