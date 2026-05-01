package com.ibm.automation.demo;

import com.ibm.automation.base.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

public class ScreenshotDemo extends BaseTest {
    static void main() throws IOException {
        setUp();
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String projectPath = System.getProperty("user.dir");
        String scenarioName = "Loginpositive";
        File destination = new File(projectPath + "\\screenshots\\" + scenarioName + ".png");
        FileUtils.copyFile(source, destination);
        tearDown();
    }
}


// Steps:
// TakesScreenshot
// store the screenshot to a file type object
//
// physical - file -> convert java object(File type object)