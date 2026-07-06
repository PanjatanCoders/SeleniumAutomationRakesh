package com.ibm.automation.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {
    protected static WebDriver driver;
    private static String BROWSER;
    private static String URL;

    @BeforeSuite
    @Parameters({"browser", "url"})
    public void beforeSuite(String myBrowser, String url) {
        System.out.println("Before Suite");
        System.out.println("Browser: " + myBrowser);
        System.out.println("URL: " + url);
        BROWSER = myBrowser;
        URL = url;
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite");
    }

    @BeforeTest
    public void beforeTest() {
        System.out.println("Before Test");
    }

    @AfterTest
    public void afterTest() {
        System.out.println("After Test");
    }

    @BeforeMethod
    public void beforeMethod() {
        System.out.println("Before Method from BaseTest");
    }


    static public void setUp() {
        // ChromeOptions myChromeOptions = getChromeoptions();

        // driver = new ChromeDriver(myChromeOptions);
        if (BROWSER.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else {
            driver = new ChromeDriver(getChromeoptions());
        }

        driver.manage().window().maximize();
//        global wait -applicable to all the find elements
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get(URL);
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