package com.ibm.automation.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected static WebDriver driver;

    static public void setUp() {
        // ChromeOptions myChromeOptions = getChromeoptions();

        // driver = new ChromeDriver(myChromeOptions);
        driver = new ChromeDriver(getChromeoptions());

        driver.manage().window().maximize();
//        global wait -applicable to all the find elements
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50));
        driver.get("https://panjatan.netlify.app/");
    }

    private static ChromeOptions getChromeoptions() {
        String downloadPath = "/home/saddam/Desktop/SeleniumAutomationRakesh/target/"; 

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("download.default_directory", downloadPath);
        
        // Optional: Disable the "Save As" popup for a seamless download
        prefs.put("download.prompt_for_download", false);
        
        // Optional: Disable PDF viewer so files download instead of opening
        prefs.put("plugins.always_open_pdf_externally", true);

        // 3. Set preferences into ChromeOptions
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);

        return options;
    }

    static public void waitTime(int sec) {
        try {
            Thread.sleep(sec  * 1000);
        } catch (InterruptedException e) {
            System.out.println("There is an exception");
        }

    }
    static public void tearDown(){
        waitTime(3);
        driver.quit();
    }

    static public void clickOperation(By locator){
        driver.findElement(locator).click();
    }

    public static String getText(By locator){
        return driver.findElement(locator).getText();
    }
}

// Exception, Error
// Exception: Checked(Compile time), UnChecked Exception