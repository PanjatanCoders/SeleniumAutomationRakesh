# Alerts, Frames, and Windows Tutorial Handbook

This handbook is a student-friendly guide for handling alerts, frames, and browser windows in Selenium WebDriver with Java. These topics are important because many real web applications use popups, embedded frames, payment widgets, login windows, and links that open in new tabs.

---

## Table of contents

- Why alerts, frames, and windows matter
- Prerequisites
- Alerts overview
- Simple alert
- Confirmation alert
- Prompt alert
- Wait for alert
- Frames overview
- Switch to frame by index
- Switch to frame by name or id
- Switch to frame by WebElement
- Switch back from frame
- Nested frames
- Windows and tabs overview
- Get current window handle
- Switch to a new window or tab
- Switch between multiple windows
- Close child window and return to parent
- Best practices
- Common mistakes and troubleshooting
- Practice exercises
- Quick reference

---

## Why alerts, frames, and windows matter

Basic Selenium scripts usually work inside one normal page. Real applications are more complex.

Common examples:

- A delete button shows a confirmation alert
- A form displays a JavaScript prompt
- A payment page loads inside an iframe
- A map, chat, captcha, or video widget appears inside a frame
- A report opens in a new browser tab
- A social login opens in a new popup window

Students must learn these topics because Selenium cannot interact with alerts, frames, or new windows until WebDriver switches to the correct browser context.

---

## Prerequisites

Before practicing this topic, students should understand:

- Java basics
- Selenium WebDriver setup
- Locators such as `id`, `cssSelector`, and `xpath`
- `WebElement`
- Explicit waits
- Basic click and text input actions

Recommended imports:

```java
import java.time.Duration;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
```

---

## Alerts overview

JavaScript alerts are browser popups created by JavaScript. They are not normal HTML elements.

Because alerts are controlled by the browser, Selenium handles them using:

```java
driver.switchTo().alert();
```

Main alert actions:

| Action | Method |
|---|---|
| Read alert text | `alert.getText()` |
| Accept alert | `alert.accept()` |
| Cancel alert | `alert.dismiss()` |
| Type into prompt | `alert.sendKeys("text")` |

---

## Simple alert

A simple alert usually has only an OK button.

Example alert:

```javascript
alert("Saved successfully");
```

Selenium handling:

```java
driver.findElement(By.id("showAlert")).click();

Alert alert = driver.switchTo().alert();
String message = alert.getText();
System.out.println(message);
alert.accept();
```

Use `accept()` to click OK.

---

## Confirmation alert

A confirmation alert usually has OK and Cancel buttons.

Example alert:

```javascript
confirm("Are you sure?");
```

Accept confirmation:

```java
driver.findElement(By.id("deleteButton")).click();

Alert alert = driver.switchTo().alert();
System.out.println(alert.getText());
alert.accept();
```

Cancel confirmation:

```java
driver.findElement(By.id("deleteButton")).click();

Alert alert = driver.switchTo().alert();
System.out.println(alert.getText());
alert.dismiss();
```

Use `accept()` for OK and `dismiss()` for Cancel.

---

## Prompt alert

A prompt alert allows the user to enter text.

Example alert:

```javascript
prompt("Enter your name");
```

Selenium handling:

```java
driver.findElement(By.id("promptButton")).click();

Alert alert = driver.switchTo().alert();
alert.sendKeys("Student One");
alert.accept();
```

After accepting the prompt, verify that the page displays the expected result.

```java
String result = driver.findElement(By.id("result")).getText();
System.out.println(result);
```

---

## Wait for alert

Alerts may take time to appear. Use explicit wait before switching.

```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

driver.findElement(By.id("showAlert")).click();

Alert alert = wait.until(ExpectedConditions.alertIsPresent());
System.out.println(alert.getText());
alert.accept();
```

This avoids `NoAlertPresentException`.

---

## Frames overview

A frame or iframe is an embedded page inside another page.

Common examples:

- Payment forms
- Embedded maps
- Chat widgets
- Video players
- Captcha widgets
- Rich text editors

Selenium can interact only with the current page context. If an element is inside an iframe, WebDriver must switch into that frame first.

Basic syntax:

```java
driver.switchTo().frame(frameReference);
```

---

## Switch to frame by index

Frame index starts from zero.

```java
driver.switchTo().frame(0);
```

Then interact with elements inside the frame:

```java
driver.findElement(By.id("cardNumber")).sendKeys("4111111111111111");
```

Index is quick for demos, but it is not the best choice for stable automation because frame order can change.

---

## Switch to frame by name or id

If the iframe has a stable `name` or `id`, use it.

HTML:

```html
<iframe id="paymentFrame" name="paymentFrame"></iframe>
```

Selenium:

```java
driver.switchTo().frame("paymentFrame");
```

This is more readable than using an index.

---

## Switch to frame by WebElement

This is often the most flexible frame switching method.

```java
WebElement frame = driver.findElement(By.cssSelector("iframe#paymentFrame"));
driver.switchTo().frame(frame);
```

With explicit wait:

```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("paymentFrame")));
```

After this line, WebDriver is already inside the frame.

---

## Switch back from frame

Switch back to the main page:

```java
driver.switchTo().defaultContent();
```

Switch back to the immediate parent frame:

```java
driver.switchTo().parentFrame();
```

Use `defaultContent()` when you want to return to the main page from any frame level.

Use `parentFrame()` when you are inside nested frames and want to move up one level.

---

## Nested frames

Nested frames are frames inside other frames.

Example flow:

```java
driver.switchTo().frame("outerFrame");
driver.switchTo().frame("innerFrame");

driver.findElement(By.id("innerButton")).click();

driver.switchTo().parentFrame();
driver.findElement(By.id("outerButton")).click();

driver.switchTo().defaultContent();
```

Important rule:

Switch step by step. Selenium cannot directly access an inner frame until it has switched into the outer frame.

---

## Windows and tabs overview

Each browser tab or popup window has a unique window handle.

Selenium uses window handles to switch between tabs and windows.

Important methods:

```java
driver.getWindowHandle();
driver.getWindowHandles();
driver.switchTo().window(windowHandle);
```

---

## Get current window handle

Store the parent window before opening a new tab or popup.

```java
String parentWindow = driver.getWindowHandle();
```

This helps return to the original page later.

---

## Switch to a new window or tab

Example:

```java
String parentWindow = driver.getWindowHandle();

driver.findElement(By.id("openReport")).click();

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.numberOfWindowsToBe(2));

for (String windowHandle : driver.getWindowHandles()) {
    if (!windowHandle.equals(parentWindow)) {
        driver.switchTo().window(windowHandle);
        break;
    }
}
```

Now WebDriver is focused on the new window or tab.

---

## Switch between multiple windows

When more than two windows are open, use title or URL to identify the correct one.

Switch by title:

```java
for (String windowHandle : driver.getWindowHandles()) {
    driver.switchTo().window(windowHandle);

    if (driver.getTitle().contains("Report")) {
        break;
    }
}
```

Switch by URL:

```java
for (String windowHandle : driver.getWindowHandles()) {
    driver.switchTo().window(windowHandle);

    if (driver.getCurrentUrl().contains("/report")) {
        break;
    }
}
```

This is more reliable than assuming the latest handle is always the target window.

---

## Close child window and return to parent

After completing work in a child window, close it and return to the parent.

```java
String parentWindow = driver.getWindowHandle();

driver.findElement(By.id("openHelp")).click();

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
wait.until(ExpectedConditions.numberOfWindowsToBe(2));

for (String windowHandle : driver.getWindowHandles()) {
    if (!windowHandle.equals(parentWindow)) {
        driver.switchTo().window(windowHandle);
        break;
    }
}

driver.close();
driver.switchTo().window(parentWindow);
```

Important note:

- `driver.close()` closes the current tab/window
- `driver.quit()` closes the entire browser session

---

## Best practices

- Always wait for alerts before switching to them.
- Store the parent window handle before opening a new window.
- Switch back to the parent window after closing a child window.
- Prefer frame switching by `id`, `name`, or WebElement instead of index.
- Use `frameToBeAvailableAndSwitchToIt()` for dynamic iframes.
- Always switch back using `defaultContent()` after frame actions.
- Verify title, URL, or page content after switching windows.
- Do not use normal locators for JavaScript alerts because alerts are not HTML elements.
- Avoid hard-coded window order when multiple windows are open.
- Keep alert, frame, and window handling in helper methods if repeated often.

---

## Common mistakes and troubleshooting

### NoAlertPresentException

Problem:

```java
Alert alert = driver.switchTo().alert();
```

The alert has not appeared yet.

Fix:

```java
Alert alert = wait.until(ExpectedConditions.alertIsPresent());
```

### NoSuchElementException inside iframe

Problem:

The element exists, but Selenium cannot find it.

Possible reason:

The element is inside an iframe and WebDriver is still on the main page.

Fix:

```java
wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("paymentFrame")));
driver.findElement(By.id("cardNumber")).sendKeys("4111111111111111");
driver.switchTo().defaultContent();
```

### Still inside frame after frame action

Problem:

After finishing frame work, Selenium cannot find elements on the main page.

Fix:

```java
driver.switchTo().defaultContent();
```

### New window opens but Selenium stays on old window

Problem:

Selenium does not automatically switch to new windows.

Fix:

```java
wait.until(ExpectedConditions.numberOfWindowsToBe(2));

for (String handle : driver.getWindowHandles()) {
    if (!handle.equals(parentWindow)) {
        driver.switchTo().window(handle);
        break;
    }
}
```

### Closing the wrong window

Problem:

The script calls `driver.close()` while focused on the parent window.

Fix:

- Store the parent handle
- Switch to the child window
- Close the child window
- Switch back to the parent handle

---

## Practice exercises

### Exercise 1: Simple alert

Goal:

- Click a button that opens an alert
- Wait for alert
- Print alert text
- Accept alert

Skills practiced:

- `alertIsPresent()`
- `getText()`
- `accept()`

### Exercise 2: Confirmation alert

Goal:

- Click a delete button
- Read confirmation message
- Cancel once using `dismiss()`
- Repeat and accept using `accept()`

Skills practiced:

- Confirmation alert handling
- Validation after OK and Cancel

### Exercise 3: Prompt alert

Goal:

- Open a prompt
- Type a name into the alert
- Accept alert
- Verify result text on page

Skills practiced:

- `sendKeys()` on alert
- Prompt result validation

### Exercise 4: iframe form

Goal:

- Switch into an iframe
- Type into a field inside the frame
- Switch back to main page
- Click a main page button

Skills practiced:

- `frameToBeAvailableAndSwitchToIt()`
- `defaultContent()`

### Exercise 5: New tab handling

Goal:

- Store parent window handle
- Click a link that opens a new tab
- Switch to new tab
- Verify title or URL
- Close child tab
- Return to parent tab

Skills practiced:

- `getWindowHandle()`
- `getWindowHandles()`
- `switchTo().window()`
- `close()`

---

## Quick reference

| Use case | Selenium command |
|---|---|
| Switch to alert | `driver.switchTo().alert()` |
| Wait for alert | `wait.until(ExpectedConditions.alertIsPresent())` |
| Get alert text | `alert.getText()` |
| Accept alert | `alert.accept()` |
| Cancel alert | `alert.dismiss()` |
| Type in prompt | `alert.sendKeys("text")` |
| Switch frame by index | `driver.switchTo().frame(0)` |
| Switch frame by name/id | `driver.switchTo().frame("frameName")` |
| Switch frame by WebElement | `driver.switchTo().frame(frameElement)` |
| Wait and switch to frame | `frameToBeAvailableAndSwitchToIt(locator)` |
| Back to main page | `driver.switchTo().defaultContent()` |
| Back to parent frame | `driver.switchTo().parentFrame()` |
| Current window handle | `driver.getWindowHandle()` |
| All window handles | `driver.getWindowHandles()` |
| Switch window | `driver.switchTo().window(handle)` |
| Wait for two windows | `ExpectedConditions.numberOfWindowsToBe(2)` |
| Close current window | `driver.close()` |
| Close browser session | `driver.quit()` |

---

## Sample complete test flow

```java
WebDriver driver = new ChromeDriver();
driver.manage().window().maximize();
driver.get("https://example.com");

WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

driver.findElement(By.id("showAlert")).click();
Alert alert = wait.until(ExpectedConditions.alertIsPresent());
System.out.println(alert.getText());
alert.accept();

wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("paymentFrame")));
driver.findElement(By.id("cardNumber")).sendKeys("4111111111111111");
driver.switchTo().defaultContent();

String parentWindow = driver.getWindowHandle();
driver.findElement(By.id("openReport")).click();
wait.until(ExpectedConditions.numberOfWindowsToBe(2));

for (String handle : driver.getWindowHandles()) {
    if (!handle.equals(parentWindow)) {
        driver.switchTo().window(handle);
        break;
    }
}

System.out.println(driver.getTitle());
driver.close();
driver.switchTo().window(parentWindow);

driver.quit();
```

---

## Student learning outcome

After completing this handbook, students should be able to:

- Handle simple alerts, confirmation alerts, and prompt alerts
- Use explicit waits before working with alerts and frames
- Switch into iframes and return to the main page
- Handle nested frames correctly
- Switch between browser windows and tabs
- Close child windows safely and return to the parent window
- Debug common context-switching problems in Selenium

Alerts, frames, and windows are essential Selenium topics because they teach students how to control the browser context before interacting with page elements.
