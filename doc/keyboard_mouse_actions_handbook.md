# Keyboard and Mouse Actions Tutorial Handbook

This handbook is a student-friendly guide for learning keyboard and mouse actions in Selenium WebDriver with Java. It explains why these actions are important, when to use them, and how to practice them through simple automation examples.

---

## Table of contents

- Why keyboard and mouse actions matter
- What is the Actions class?
- Prerequisites
- Basic mouse actions
- Basic keyboard actions
- Combined keyboard and mouse actions
- Drag and drop
- Sliders and range controls
- Right click and double click
- Best practices
- Common mistakes and troubleshooting
- Practice exercises
- Quick reference

---

## Why keyboard and mouse actions matter

Most beginner Selenium scripts use simple commands like `click()`, `sendKeys()`, and `submit()`. These are enough for many basic forms, but real websites often require richer user interactions.

Keyboard and mouse actions help students learn how real users interact with web applications.

Important use cases:

- Opening menus that appear only on mouse hover
- Double-clicking items
- Right-clicking context menus
- Dragging and dropping cards, files, or list items
- Moving sliders
- Selecting text with keyboard shortcuts
- Pressing special keys like `ENTER`, `TAB`, `SHIFT`, `CTRL`, and arrow keys
- Testing accessibility and keyboard navigation
- Automating complex UI components that do not work with simple `click()`

Learning these actions is important because automation testing is not only about filling forms. A tester must validate the full user experience, including interactive components.

---

## What is the Actions class?

Selenium provides the `Actions` class to perform advanced keyboard and mouse operations.

Basic syntax:

```java
import org.openqa.selenium.interactions.Actions;

Actions actions = new Actions(driver);
```

Most Actions class methods are completed using `perform()`.

Example:

```java
actions.moveToElement(menuElement).perform();
```

For a sequence of actions, use `build().perform()`.

Example:

```java
actions
    .moveToElement(sourceElement)
    .clickAndHold()
    .moveToElement(targetElement)
    .release()
    .build()
    .perform();
```

---

## Prerequisites

Before practicing this topic, students should understand:

- Java basics
- Selenium WebDriver setup
- Locators such as `id`, `name`, `cssSelector`, and `xpath`
- `WebElement`
- Basic waits
- Browser setup using `ChromeDriver` or another WebDriver

Recommended imports:

```java
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
```

---

## Basic mouse actions

### Move to an element

Use `moveToElement()` when an element appears only after hovering over another element.

```java
WebElement menu = driver.findElement(By.id("productsMenu"));

Actions actions = new Actions(driver);
actions.moveToElement(menu).perform();
```

Common example:

- Hover over "Products"
- Submenu appears
- Click a submenu item

```java
WebElement menu = driver.findElement(By.id("productsMenu"));
WebElement laptops = driver.findElement(By.id("laptopsLink"));

Actions actions = new Actions(driver);
actions.moveToElement(menu).perform();
laptops.click();
```

### Click using Actions

Simple click:

```java
WebElement button = driver.findElement(By.id("saveButton"));

Actions actions = new Actions(driver);
actions.click(button).perform();
```

This can help when normal `element.click()` does not behave like a real mouse interaction.

---

## Basic keyboard actions

### Press Enter

```java
WebElement searchBox = driver.findElement(By.id("search"));

searchBox.sendKeys("Selenium");
searchBox.sendKeys(Keys.ENTER);
```

Using Actions:

```java
Actions actions = new Actions(driver);
actions.sendKeys(Keys.ENTER).perform();
```

### Press Tab

`TAB` is useful for testing keyboard navigation and focus movement.

```java
Actions actions = new Actions(driver);
actions.sendKeys(Keys.TAB).perform();
```

### Select all text and replace it

For Windows/Linux:

```java
WebElement input = driver.findElement(By.id("username"));

Actions actions = new Actions(driver);
actions
    .click(input)
    .keyDown(Keys.CONTROL)
    .sendKeys("a")
    .keyUp(Keys.CONTROL)
    .sendKeys("newUser")
    .build()
    .perform();
```

For macOS, use `Keys.COMMAND` instead of `Keys.CONTROL`.

---

## Combined keyboard and mouse actions

### Shift + click

Some applications use modifier keys to select multiple items.

```java
WebElement firstItem = driver.findElement(By.id("item1"));
WebElement lastItem = driver.findElement(By.id("item5"));

Actions actions = new Actions(driver);
actions
    .click(firstItem)
    .keyDown(Keys.SHIFT)
    .click(lastItem)
    .keyUp(Keys.SHIFT)
    .build()
    .perform();
```

### Ctrl + click

Useful for multi-select lists.

```java
WebElement itemOne = driver.findElement(By.id("item1"));
WebElement itemThree = driver.findElement(By.id("item3"));

Actions actions = new Actions(driver);
actions
    .keyDown(Keys.CONTROL)
    .click(itemOne)
    .click(itemThree)
    .keyUp(Keys.CONTROL)
    .build()
    .perform();
```

---

## Drag and drop

Drag and drop is common in dashboards, task boards, file areas, and visual builders.

Simple example:

```java
WebElement source = driver.findElement(By.id("dragItem"));
WebElement target = driver.findElement(By.id("dropArea"));

Actions actions = new Actions(driver);
actions.dragAndDrop(source, target).perform();
```

Manual drag sequence:

```java
actions
    .clickAndHold(source)
    .moveToElement(target)
    .release()
    .build()
    .perform();
```

Use the manual sequence when `dragAndDrop()` does not work with the application.

---

## Sliders and range controls

Sliders often require mouse movement by offset.

```java
WebElement slider = driver.findElement(By.id("volumeSlider"));

Actions actions = new Actions(driver);
actions
    .clickAndHold(slider)
    .moveByOffset(50, 0)
    .release()
    .build()
    .perform();
```

Notes:

- Positive `x` moves right.
- Negative `x` moves left.
- Positive `y` moves down.
- Negative `y` moves up.
- Use small offsets first and verify the result.

---

## Right click and double click

### Right click

Right click is also called context click.

```java
WebElement element = driver.findElement(By.id("contextMenuArea"));

Actions actions = new Actions(driver);
actions.contextClick(element).perform();
```

### Double click

```java
WebElement element = driver.findElement(By.id("editableLabel"));

Actions actions = new Actions(driver);
actions.doubleClick(element).perform();
```

---

## Best practices

- Always locate elements clearly before performing actions.
- Use explicit waits before advanced actions.
- Prefer stable locators such as `id`, `name`, or reliable `data-*` attributes.
- Keep action chains short and readable.
- Add assertions after actions to verify the result.
- Avoid using fixed sleeps unless there is no better option.
- Test keyboard navigation for accessibility.
- Use `build().perform()` when chaining multiple steps.
- Use `perform()` directly for one simple action.

Example with assertion:

```java
WebElement menu = driver.findElement(By.id("productsMenu"));
WebElement submenu = driver.findElement(By.id("laptopsLink"));

Actions actions = new Actions(driver);
actions.moveToElement(menu).perform();

assertTrue(submenu.isDisplayed());
```

---

## Common mistakes and troubleshooting

### Element is not interactable

Possible reasons:

- Element is hidden
- Element is covered by another element
- Page is still loading
- Element appears only after hover

Fix:

```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id("saveButton")));
button.click();
```

Required imports:

```java
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
```

### Drag and drop does not work

Try the manual sequence:

```java
actions
    .clickAndHold(source)
    .pause(Duration.ofMillis(500))
    .moveToElement(target)
    .pause(Duration.ofMillis(500))
    .release()
    .build()
    .perform();
```

### Keyboard shortcut does not work

Check:

- Is the correct element focused?
- Is the shortcut different on macOS?
- Did you release the key using `keyUp()`?

Example:

```java
actions
    .click(input)
    .keyDown(Keys.CONTROL)
    .sendKeys("a")
    .keyUp(Keys.CONTROL)
    .build()
    .perform();
```

---

## Practice exercises

### Exercise 1: Hover menu

Goal:

- Open a page with a hover menu
- Move the mouse to the menu
- Click a submenu item
- Verify the new page or section is displayed

Skills practiced:

- `moveToElement()`
- Element visibility
- Click after hover

### Exercise 2: Search using Enter

Goal:

- Type text in a search box
- Press `ENTER`
- Verify search results are displayed

Skills practiced:

- `sendKeys()`
- `Keys.ENTER`
- Basic assertion

### Exercise 3: Drag and drop

Goal:

- Drag an item from one area
- Drop it into another area
- Verify the item moved successfully

Skills practiced:

- `dragAndDrop()`
- `clickAndHold()`
- `release()`

### Exercise 4: Slider movement

Goal:

- Move a slider to the right
- Verify the value changed

Skills practiced:

- `clickAndHold()`
- `moveByOffset()`
- UI validation

### Exercise 5: Keyboard navigation

Goal:

- Use `TAB` to move through form fields
- Type values into focused fields
- Submit using `ENTER`

Skills practiced:

- Accessibility testing
- Focus handling
- Keyboard-only interaction

---

## Quick reference

| Action | Selenium method |
|---|---|
| Hover | `moveToElement(element)` |
| Click | `click(element)` |
| Double click | `doubleClick(element)` |
| Right click | `contextClick(element)` |
| Drag and drop | `dragAndDrop(source, target)` |
| Click and hold | `clickAndHold(element)` |
| Release mouse | `release()` |
| Move by offset | `moveByOffset(x, y)` |
| Press key | `sendKeys(Keys.ENTER)` |
| Hold key | `keyDown(Keys.CONTROL)` |
| Release key | `keyUp(Keys.CONTROL)` |
| Run one action | `perform()` |
| Run chained actions | `build().perform()` |

---

## Sample complete test flow

```java
WebDriver driver = new ChromeDriver();
driver.manage().window().maximize();
driver.get("https://example.com");

WebElement searchBox = driver.findElement(By.id("search"));

Actions actions = new Actions(driver);
actions
    .click(searchBox)
    .sendKeys("Selenium Actions")
    .sendKeys(Keys.ENTER)
    .build()
    .perform();

driver.quit();
```

---

## Student learning outcome

After completing this handbook, students should be able to:

- Explain why keyboard and mouse actions are required in automation testing
- Use the Selenium `Actions` class confidently
- Automate hover, click, right click, double click, drag and drop, and keyboard shortcuts
- Debug common interaction failures
- Write more realistic automation scripts that behave like real user actions

This topic is an important step from basic Selenium scripting to practical automation testing.
