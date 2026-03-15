package com.ibm.automation.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Demo2FindElement {
    static void main() {
        WebDriver driver = new ChromeDriver(); //Launch a browser
        driver.manage().window().maximize();

//        Navigate to an url
        driver.get("https://panjatan.netlify.app/");
        driver.manage().window().maximize();

        By locator = By.id("username");
        driver.findElement(locator).sendKeys("admin");

        By pwdLocator = By.name("password");
        driver.findElement(pwdLocator).sendKeys("password");

        By loginLocator = By.xpath("//button[text()='Login']");
        driver.findElement(loginLocator).click();

        String text = driver.findElement(By.id("loginAlert")).getText();
        System.out.println(text);

        System.out.println(driver.findElement(By.linkText("Forms")).getText());
    }
}

/*
    Actions in browser:
     - click
     - enter text
     - select from dropdown
     - fetch text
     - drag and drop
     - mouse hover
     - switch windows
     - popup handle
 */
/*
    Locators:
        - id
        - name
        - className
        - tagName
        - linkText
        - partialLinkText
        - cssSelector
        - xpath (Ultimate solution)
 */
// firebug, chromepath, SelectorsHub(Sanjeev), LocatorsHub(Naveen), Pramana Studio
// IE :