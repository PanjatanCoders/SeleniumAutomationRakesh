# Selenium File Upload / Download Guide

This user guide explains how to use `ChromeOptions` and `FirefoxOptions` in Selenium Java to manage file upload and file download behavior.

## 1. Overview

- `File upload` in Selenium is usually handled by sending a local file path to an `<input type="file">` element.
- `File download` is handled by browser preferences/options so the file is saved automatically to a chosen folder without browser prompts.
- This guide covers both Chrome and Firefox browser options.

## 2. Prerequisites

- Java 8+ / Java 11+ installed
- Selenium Java dependency configured in `pom.xml`
- Browser drivers available and compatible with browser versions
- A valid path for uploads and a writable download directory

## 3. File Upload

### 3.1 Best practice

Use the file input element directly when possible.

Example:

```java
WebElement fileInput = driver.findElement(By.id("fileInput"));
fileInput.sendKeys("/home/user/project/data/testfile.txt");
```

### 3.2 Notes

- The file path must be absolute and valid on the machine executing the test.
- The browser must be able to access the local file path.
- Do not use keyboard automation or native dialogs. Selenium cannot reliably interact with OS file pickers.

### 3.3 Sample upload flow

```java
setUp();
driver.findElement(By.id("fileInput")).sendKeys("/home/saddam/Desktop/SeleniumAutomationRakesh/screenshots/Loginpositive.png");
waitTime(5);
tearDown();
```

## 4. Chrome Download Options

Use `ChromeOptions` to configure automatic downloads and control the download folder.

### 4.1 Chrome download preferences

```java
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.HashMap;
import java.util.Map;

ChromeOptions options = new ChromeOptions();
Map<String, Object> prefs = new HashMap<>();
prefs.put("download.default_directory", "/home/saddam/Desktop/SeleniumAutomationRakesh/downloads");
prefs.put("download.prompt_for_download", false);
prefs.put("download.directory_upgrade", true);
prefs.put("safebrowsing.enabled", true);
options.setExperimentalOption("prefs", prefs);

WebDriver driver = new ChromeDriver(options);
```

### 4.2 Important Chrome settings

- `download.default_directory` sets the folder where files are saved.
- `download.prompt_for_download` disables the save dialog.
- `download.directory_upgrade` allows Chrome to use the configured folder.
- `safebrowsing.enabled` may help avoid block pages for some download types.

### 4.3 Example download scenario

```java
setUp();
// Example page actions to start the download
driver.findElement(By.xpath("//a[@data-testid='nav-table']")).click();
driver.findElement(By.id("download-csv")).click();
waitTime(7);
tearDown();
```

## 5. Firefox Download Options

Use `FirefoxOptions` and a Firefox profile to disable download dialogs and specify the save folder.

### 5.1 Firefox download preferences

```java
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

FirefoxProfile profile = new FirefoxProfile();
profile.setPreference("browser.download.folderList", 2); // 0=desktop, 1=Downloads, 2=custom
profile.setPreference("browser.download.dir", "/home/saddam/Desktop/SeleniumAutomationRakesh/downloads");
profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/pdf,application/octet-stream");
profile.setPreference("browser.download.manager.showWhenStarting", false);
profile.setPreference("pdfjs.disabled", true);

FirefoxOptions options = new FirefoxOptions();
options.setProfile(profile);
WebDriver driver = new FirefoxDriver(options);
```

### 5.2 Important Firefox settings

- `browser.download.folderList = 2` means use custom download folder.
- `browser.download.dir` is the path to save downloaded files.
- `browser.helperApps.neverAsk.saveToDisk` is a comma-separated mime type list for automatic downloads.
- `pdfjs.disabled = true` disables Firefox's built-in PDF viewer, allowing PDF download instead.

## 6. Selecting mime types for Firefox

Common values:

- `text/csv`
- `application/pdf`
- `application/zip`
- `application/octet-stream`
- `image/png`
- `image/jpeg`

If the download does not start automatically, verify the correct mime type for the response.

## 7. Verifying downloads

After the browser click triggers a download, verify the file is present in the target folder.

Example:

```java
Path downloadedFile = Paths.get("/home/saddam/Desktop/SeleniumAutomationRakesh/downloads", "sample.csv");
assertTrue(Files.exists(downloadedFile));
```

## 8. Tips and troubleshooting

- Ensure the download directory exists before launching the browser.
- Use `waitTime()` or explicit waits until the file is fully written.
- Clear or isolate the download folder between test runs.
- For upload tests, keep the test file inside the project repository so the path is stable.
- When using remote or CI environments, use paths available to the build agent.

## 9. Recommended structure for tests

- Keep browser option setup in `setUp()` or a shared `BaseTest` helper.
- Keep upload files in a `resources` or `testdata` folder.
- Keep download assertions separate from the browser action.

## 10. Example setup helper (Chrome)

```java
public void setUpChromeWithDownloadPath(String downloadDir) {
    ChromeOptions options = new ChromeOptions();
    Map<String, Object> prefs = new HashMap<>();
    prefs.put("download.default_directory", downloadDir);
    prefs.put("download.prompt_for_download", false);
    prefs.put("download.directory_upgrade", true);
    prefs.put("safebrowsing.enabled", true);
    options.setExperimentalOption("prefs", prefs);
    driver = new ChromeDriver(options);
}
```

## 11. Example setup helper (Firefox)

```java
public void setUpFirefoxWithDownloadPath(String downloadDir) {
    FirefoxProfile profile = new FirefoxProfile();
    profile.setPreference("browser.download.folderList", 2);
    profile.setPreference("browser.download.dir", downloadDir);
    profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv,application/pdf,application/octet-stream");
    profile.setPreference("browser.download.manager.showWhenStarting", false);
    profile.setPreference("pdfjs.disabled", true);
    FirefoxOptions options = new FirefoxOptions();
    options.setProfile(profile);
    driver = new FirefoxDriver(options);
}
```

---

This guide is intended for Selenium Java users who want reliable file upload and download automation with Chrome and Firefox.
