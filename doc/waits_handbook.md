# Selenium Waits Tutorial Handbook

This handbook is a student-friendly guide for learning waits in Selenium WebDriver with Java. It explains why waits are important, how to use implicit wait, explicit wait, fluent wait, and how to fix common timing problems in automation scripts.

---

## Table of contents

- Why waits matter
- What problem do waits solve?
- Types of waits in Selenium
- Static wait using Thread.sleep
- Implicit wait
- Explicit wait
- Common ExpectedConditions
- Fluent wait
- Page load timeout
- Script timeout
- Waits for dynamic elements
- Waits for alerts
- Waits for frames
- Waits for multiple windows
- Waits with page objects
- Best practices
- Common mistakes and troubleshooting
- Practice exercises
- Quick reference

---

## Why waits matter

Web applications do not always load everything at the same time. A page may appear quickly, but buttons, tables, messages, popups, or AJAX data may load a few seconds later.

Without proper waits, Selenium scripts may fail even when the application is working correctly.

Common wait-related errors:

- `NoSuchElementException`
- `ElementNotInteractableException`
- `ElementClickInterceptedException`
- `TimeoutException`
- `StaleElementReferenceException`

Students must learn waits because stable automation depends on timing control.

---

## What problem do waits solve?

Selenium is faster than a real user. It may try to click or type before the page is ready.

Example problem:

```java
driver.get("https://example.com/login");
driver.findElement(By.id("loginButton")).click();
```

If the button is not ready yet, the script fails.

Better approach:

```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("loginButton")));
loginButton.click();
```

The wait gives the application time to reach the expected state.

---

## Types of waits in Selenium

This handbook explains four common wait styles:

| Wait type | Purpose |
|---|---|
| Static wait | Pause for a fixed time |
| Implicit wait | Wait globally while finding elements |
| Explicit wait | Wait for a specific condition |
| Fluent wait | Explicit wait with custom polling and ignored exceptions |

In real automation, explicit wait is usually the most useful and safest option.

---

## Static wait using Thread.sleep

`Thread.sleep()` pauses the test for a fixed time.

```java
Thread.sleep(3000);
```

This waits for exactly 3 seconds.

Problems:

- It waits even if the element is ready early
- It may still fail if the element takes longer
- It makes tests slow
- It does not check any condition

Use `Thread.sleep()` only for temporary debugging or classroom demonstration. Do not depend on it in stable automation suites.

---

## Implicit wait

Implicit wait tells WebDriver to wait for a period of time while trying to find an element.

Syntax:

```java
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
```

Example:

```java
WebDriver driver = new ChromeDriver();
driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

driver.get("https://example.com/login");
driver.findElement(By.id("username")).sendKeys("student1");
```

Important points:

- It applies globally to `findElement()` and `findElements()`
- It does not wait for clickability
- It does not wait for visibility unless finding the element requires it
- It does not solve all timing problems

Use implicit wait carefully. Many teams prefer setting it to zero and using explicit waits for important actions.

---

## Explicit wait

Explicit wait waits for a specific condition before continuing.

Required imports:

```java
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
```

Basic syntax:

```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
```

Example: wait for button to be clickable

```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

WebElement loginButton = wait.until(
    ExpectedConditions.elementToBeClickable(By.id("loginButton"))
);

loginButton.click();
```

Explicit wait is preferred because it waits only as long as needed, up to the maximum timeout.

---

## Common ExpectedConditions

### Element is visible

```java
WebElement message = wait.until(
    ExpectedConditions.visibilityOfElementLocated(By.id("successMessage"))
);
```

Use when the element must be displayed on the page.

### Element is clickable

```java
WebElement button = wait.until(
    ExpectedConditions.elementToBeClickable(By.id("submitButton"))
);
```

Use before clicking buttons, links, checkboxes, or radio buttons.

### Element is present in DOM

```java
WebElement element = wait.until(
    ExpectedConditions.presenceOfElementLocated(By.id("result"))
);
```

Presence means the element exists in HTML. It may still be hidden.

### Text is present

```java
boolean isTextPresent = wait.until(
    ExpectedConditions.textToBePresentInElementLocated(By.id("status"), "Completed")
);
```

Use for status messages, loading states, and validation messages.

### Element is invisible

```java
boolean loaderGone = wait.until(
    ExpectedConditions.invisibilityOfElementLocated(By.id("loader"))
);
```

Use when waiting for spinners, overlays, or loading masks to disappear.

### Title contains

```java
wait.until(ExpectedConditions.titleContains("Dashboard"));
```

Use after navigation.

### URL contains

```java
wait.until(ExpectedConditions.urlContains("/dashboard"));
```

Use when page URL should change.

---

## Fluent wait

Fluent wait gives more control over:

- Maximum timeout
- Polling interval
- Exceptions to ignore

Required imports:

```java
import java.time.Duration;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
```

Example:

```java
Wait<WebDriver> wait = new FluentWait<>(driver)
    .withTimeout(Duration.ofSeconds(20))
    .pollingEvery(Duration.ofSeconds(2))
    .ignoring(NoSuchElementException.class);

WebElement element = wait.until(webDriver ->
    webDriver.findElement(By.id("dynamicElement"))
);
```

Use fluent wait when you need custom polling behavior or need to ignore specific exceptions while waiting.

---

## Page load timeout

Page load timeout controls how long WebDriver waits for a page load to complete.

```java
driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
```

Example:

```java
WebDriver driver = new ChromeDriver();
driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
driver.get("https://example.com");
```

If the page does not load within 30 seconds, Selenium throws a timeout error.

---

## Script timeout

Script timeout controls how long Selenium waits for asynchronous JavaScript execution.

```java
driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
```

This is useful when using:

```java
executeAsyncScript()
```

Example:

```java
driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10));
```

Most beginner Selenium tests do not need script timeout unless they use asynchronous JavaScript.

---

## Waits for dynamic elements

Dynamic elements are created, updated, or removed after the page loads.

### Wait for search result

```java
driver.findElement(By.id("search")).sendKeys("Selenium");

WebElement result = wait.until(
    ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".search-result"))
);
```

### Wait for loader to disappear

```java
wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loadingSpinner")));
```

### Wait for table rows

```java
List<WebElement> rows = wait.until(
    ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("table tbody tr"), 0)
);
```

Required import:

```java
import java.util.List;
```

---

## Waits for alerts

Use alert wait when a JavaScript alert appears after an action.

```java
driver.findElement(By.id("deleteButton")).click();

Alert alert = wait.until(ExpectedConditions.alertIsPresent());
System.out.println(alert.getText());
alert.accept();
```

Required import:

```java
import org.openqa.selenium.Alert;
```

---

## Waits for frames

Use frame wait when an iframe loads dynamically.

```java
wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("paymentFrame")));
```

After this line, WebDriver is inside the frame.

Return to main page:

```java
driver.switchTo().defaultContent();
```

---

## Waits for multiple windows

When clicking a link opens a new tab or window, wait for the window count to increase.

```java
String originalWindow = driver.getWindowHandle();

driver.findElement(By.id("openReport")).click();

wait.until(ExpectedConditions.numberOfWindowsToBe(2));

for (String windowHandle : driver.getWindowHandles()) {
    if (!windowHandle.equals(originalWindow)) {
        driver.switchTo().window(windowHandle);
        break;
    }
}
```

Use this when testing links that open in a new tab.

---

## Waits with page objects

In Page Object Model, waits are often placed inside page methods.

Example:

```java
public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By username = By.id("username");
    private By password = By.id("password");
    private By loginButton = By.id("loginButton");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void login(String user, String pass) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(username)).sendKeys(user);
        wait.until(ExpectedConditions.visibilityOfElementLocated(password)).sendKeys(pass);
        wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();
    }
}
```

This keeps test cases cleaner and makes wait logic reusable.

---

## Best practices

- Prefer explicit waits for important actions.
- Wait for a meaningful condition, not just time.
- Use `elementToBeClickable()` before clicking.
- Use `visibilityOfElementLocated()` before reading visible text.
- Use `invisibilityOfElementLocated()` for loaders and overlays.
- Avoid depending on `Thread.sleep()` in real tests.
- Avoid mixing large implicit waits with explicit waits.
- Keep wait timeout values reasonable, such as 5 to 15 seconds.
- Add clear assertions after wait-based actions.
- Put repeated wait logic in helper methods or page objects.

Good approach:

```java
WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id("saveButton")));
button.click();
```

Risky approach:

```java
Thread.sleep(10000);
driver.findElement(By.id("saveButton")).click();
```

---

## Common mistakes and troubleshooting

### Using Thread.sleep everywhere

Problem:

```java
Thread.sleep(5000);
```

Fix:

```java
wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("message")));
```

### Waiting for presence when visibility is needed

Problem:

```java
wait.until(ExpectedConditions.presenceOfElementLocated(By.id("submitButton"))).click();
```

Fix:

```java
wait.until(ExpectedConditions.elementToBeClickable(By.id("submitButton"))).click();
```

### Loader blocks the click

Problem:

- Element is found
- Selenium tries to click
- Loader or overlay blocks the element

Fix:

```java
wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loader")));
wait.until(ExpectedConditions.elementToBeClickable(By.id("submitButton"))).click();
```

### Stale element after refresh or AJAX update

Problem:

An element was found before the page updated. After the update, the old reference is no longer valid.

Fix:

```java
WebElement freshElement = wait.until(
    ExpectedConditions.elementToBeClickable(By.id("saveButton"))
);
freshElement.click();
```

Find the element again after the page changes.

### TimeoutException

Possible reasons:

- Locator is wrong
- Element never appears
- Timeout is too short
- Application has a real bug
- Test is waiting for the wrong condition

Fix checklist:

- Verify locator in browser developer tools
- Increase timeout only if the application genuinely needs more time
- Check whether the element is inside an iframe
- Check whether a loader or overlay is blocking it
- Use the correct expected condition

---

## Practice exercises

### Exercise 1: Wait for login button

Goal:

- Open a login page
- Wait for the login button to be clickable
- Click the login button

Skills practiced:

- `WebDriverWait`
- `elementToBeClickable()`

### Exercise 2: Wait for success message

Goal:

- Submit a form
- Wait for success message to appear
- Print and verify the message text

Skills practiced:

- `visibilityOfElementLocated()`
- `getText()`
- Assertions

### Exercise 3: Wait for loader to disappear

Goal:

- Perform an action that shows a spinner
- Wait until the spinner disappears
- Continue to the next action

Skills practiced:

- `invisibilityOfElementLocated()`
- Dynamic page handling

### Exercise 4: Wait for table data

Goal:

- Open a page with dynamic table data
- Wait until at least one row appears
- Print the row count

Skills practiced:

- `numberOfElementsToBeMoreThan()`
- `findElements()`
- Lists of WebElements

### Exercise 5: Wait for alert

Goal:

- Click a delete button
- Wait for alert
- Read alert text
- Accept the alert

Skills practiced:

- `alertIsPresent()`
- Alert handling

---

## Quick reference

| Use case | Wait example |
|---|---|
| Create explicit wait | `new WebDriverWait(driver, Duration.ofSeconds(10))` |
| Visible element | `visibilityOfElementLocated(By.id("message"))` |
| Clickable element | `elementToBeClickable(By.id("submit"))` |
| Present in DOM | `presenceOfElementLocated(By.id("result"))` |
| Text appears | `textToBePresentInElementLocated(By.id("status"), "Done")` |
| Element disappears | `invisibilityOfElementLocated(By.id("loader"))` |
| Alert appears | `alertIsPresent()` |
| Frame available | `frameToBeAvailableAndSwitchToIt(By.id("frame"))` |
| Window count | `numberOfWindowsToBe(2)` |
| URL changes | `urlContains("/dashboard")` |
| Title changes | `titleContains("Dashboard")` |
| More than zero elements | `numberOfElementsToBeMoreThan(locator, 0)` |
| Implicit wait | `driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10))` |
| Page load timeout | `driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30))` |
| Script timeout | `driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(10))` |

---

## Sample complete test flow

```java
WebDriver driver = new ChromeDriver();
driver.manage().window().maximize();
driver.get("https://example.com/login");

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("username"))).sendKeys("student1");
wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys("password123");
wait.until(ExpectedConditions.elementToBeClickable(By.id("loginButton"))).click();

wait.until(ExpectedConditions.urlContains("/dashboard"));

String heading = wait.until(
    ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
).getText();

System.out.println(heading);

driver.quit();
```

---

## Student learning outcome

After completing this handbook, students should be able to:

- Explain why waits are required in Selenium automation
- Understand the difference between static, implicit, explicit, and fluent waits
- Use `WebDriverWait` and `ExpectedConditions`
- Wait for elements, alerts, frames, windows, URLs, titles, and dynamic data
- Debug common timing failures
- Write more stable and reliable Selenium test scripts

Waits are one of the most important Selenium skills because they convert fragile scripts into dependable automation tests.
