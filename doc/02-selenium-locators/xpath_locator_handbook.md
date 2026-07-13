# XPath Locator Tutorial Handbook

This handbook is a student-friendly guide for learning XPath locators in Selenium WebDriver with Java. It explains XPath from the basics to practical automation use cases, including attributes, text, contains, starts-with, indexing, parent-child relationships, siblings, ancestors, descendants, and dynamic elements.

---

## Table of contents

- Why XPath matters
- What is XPath?
- Absolute XPath and relative XPath
- Basic XPath syntax
- XPath using attributes
- XPath using text
- XPath using contains
- XPath using starts-with
- XPath using multiple conditions
- XPath using indexes
- XPath using parent and child
- XPath using siblings
- XPath using ancestor and descendant
- XPath using following and preceding
- XPath for forms
- XPath for tables
- XPath for dynamic elements
- Useful XPath functions
- XPath in Selenium Java
- Best practices
- Common mistakes and troubleshooting
- Practice exercises
- Quick reference

---

## Why XPath matters

XPath is one of the most useful locator strategies in Selenium automation. CSS selectors are often faster and cleaner, but XPath can solve many cases that CSS cannot handle easily.

XPath is important because it can:

- Locate elements by text
- Move from child to parent
- Move between siblings
- Locate elements using complex relationships
- Handle dynamic attributes
- Work with tables and nested structures
- Locate elements when `id` and `class` are missing or unstable

Students should learn XPath because many real-world applications do not provide clean IDs or test-friendly attributes.

---

## What is XPath?

XPath means XML Path Language. It is used to navigate through elements and attributes in an HTML or XML document.

In Selenium Java, XPath is used with:

```java
driver.findElement(By.xpath("xpath_expression"));
```

Example:

```java
driver.findElement(By.xpath("//input[@id='username']"));
```

---

## Absolute XPath and relative XPath

### Absolute XPath

Absolute XPath starts from the root of the HTML document.

Example:

```xpath
/html/body/div/form/input
```

Problem:

- Very long
- Breaks easily when page structure changes
- Not recommended for automation

### Relative XPath

Relative XPath starts from anywhere in the document using `//`.

Example:

```xpath
//input[@id='username']
```

Relative XPath is preferred in Selenium automation because it is shorter, more readable, and more stable.

---

## Basic XPath syntax

Select any element by tag name:

```xpath
//input
```

Select any element:

```xpath
//*
```

Select element by attribute:

```xpath
//input[@id='username']
```

Basic format:

```xpath
//tagName[@attributeName='attributeValue']
```

Examples:

```xpath
//button[@type='submit']
//input[@name='email']
//a[@href='/login']
//div[@class='alert success']
```

---

## XPath using attributes

XPath can locate elements using any HTML attribute.

### By id

```xpath
//input[@id='username']
```

### By name

```xpath
//input[@name='password']
```

### By placeholder

```xpath
//input[@placeholder='Enter username']
```

### By type

```xpath
//input[@type='checkbox']
```

### By data attribute

```xpath
//button[@data-testid='login-button']
```

Data attributes are usually excellent for automation when developers provide them.

---

## XPath using text

Use `text()` when the exact visible text is known.

```xpath
//button[text()='Login']
```

Use `.` when text may be inside nested elements.

```xpath
//button[.='Login']
```

Example:

```html
<button><span>Login</span></button>
```

For this HTML, `//button[text()='Login']` may fail because the text is inside `span`, but `//button[.='Login']` can work.

Useful examples:

```xpath
//a[text()='Forgot Password?']
//label[text()='Username:']
//h1[.='Dashboard']
```

---

## XPath using contains

Use `contains()` when only part of an attribute or text is stable.

### Contains attribute

```xpath
//input[contains(@placeholder, 'username')]
```

### Contains text

```xpath
//p[contains(text(), 'invalid username')]
```

### Contains visible text using dot

```xpath
//div[contains(., 'Login failed')]
```

Common dynamic id example:

```xpath
//div[contains(@id, 'loginAlert')]
```

Use `contains()` carefully. If the matching text is too general, Selenium may find the wrong element.

---

## XPath using starts-with

Use `starts-with()` when an attribute begins with a stable value but ends with a dynamic value.

Example:

```xpath
//input[starts-with(@id, 'user_')]
```

Dynamic id example:

```html
<input id="user_98375">
```

XPath:

```xpath
//input[starts-with(@id, 'user_')]
```

Text example:

```xpath
//p[starts-with(text(), 'Please enter')]
```

---

## XPath using multiple conditions

### AND condition

Use `and` when both conditions must match.

```xpath
//input[@id='username' and @name='userName']
```

More examples:

```xpath
//button[@type='submit' and text()='Login']
//input[@placeholder='Enter username' and @type='text']
```

### OR condition

Use `or` when either condition can match.

```xpath
//input[@id='username' or @name='username']
```

Example for changing IDs:

```xpath
//button[@id='loginButton' or @data-testid='login-button']
```

---

## XPath using indexes

Indexes help when multiple elements match the same XPath.

### Index inside XPath

```xpath
//input[@type='text'][1]
```

This selects the first matching `input` within each matching group, depending on document structure.

### Index around XPath

```xpath
(//input[@type='text'])[1]
```

This selects the first matching element from the complete result list.

Recommended format:

```xpath
(//input[@type='text'])[1]
```

Examples:

```xpath
(//button[text()='Edit'])[1]
(//input[@class='form-control'])[2]
(//table//tr)[3]
```

Important note:

Indexes can break if the page order changes. Prefer unique attributes or relationships when possible.

---

## XPath using parent and child

XPath can move through element relationships.

### Direct child

```xpath
//form[@id='loginForm']/input
```

This selects `input` elements that are direct children of the form.

### Any descendant child

```xpath
//form[@id='loginForm']//input
```

This selects `input` elements at any depth inside the form.

### Parent axis

```xpath
//input[@id='username']/parent::div
```

### Child axis

```xpath
//form[@id='loginForm']/child::input
```

Use parent and child relationships when the target element has poor attributes but its nearby container is stable.

---

## XPath using siblings

Sibling relationships are useful for forms, labels, rows, and related fields.

### Following sibling

```xpath
//label[text()='Username']/following-sibling::input
```

Example:

```html
<label>Username</label>
<input type="text">
```

XPath:

```xpath
//label[text()='Username']/following-sibling::input
```

### Preceding sibling

```xpath
//input[@id='username']/preceding-sibling::label
```

### Specific sibling position

```xpath
//td[text()='John']/following-sibling::td[1]
```

This selects the first `td` after the cell containing `John`.

---

## XPath using ancestor and descendant

### Ancestor

Use `ancestor` to move upward to a parent, grandparent, or higher-level container.

```xpath
//input[@id='email']/ancestor::form
```

Example use case:

- Locate an input
- Move to its form
- Find another element inside that form

```xpath
//input[@id='email']/ancestor::form//button[@type='submit']
```

### Descendant

Use `descendant` to find elements inside another element at any depth.

```xpath
//section[@id='products']/descendant::button
```

Short version:

```xpath
//section[@id='products']//button
```

---

## XPath using following and preceding

### Following

`following` selects elements after the current element in the document.

```xpath
//h2[text()='Billing Address']/following::input
```

Select the first input after a heading:

```xpath
(//h2[text()='Billing Address']/following::input)[1]
```

### Preceding

`preceding` selects elements before the current element in the document.

```xpath
//button[text()='Submit']/preceding::input
```

Select the last input before a button:

```xpath
(//button[text()='Submit']/preceding::input)[last()]
```

Use `following` and `preceding` carefully because they can search across large parts of the page.

---

## XPath for forms

### Locate input by label

```xpath
//label[text()='Email']/following-sibling::input
```

If the input is inside the same parent:

```xpath
//label[text()='Email']/parent::*//input
```

### Locate required field

```xpath
//input[@required]
```

### Locate enabled button by text

```xpath
//button[text()='Submit' and not(@disabled)]
```

### Locate checkbox by label

```xpath
//label[text()='Remember me']/preceding-sibling::input[@type='checkbox']
```

Alternative when label wraps the input:

```xpath
//label[contains(., 'Remember me')]//input[@type='checkbox']
```

---

## XPath for tables

Tables are common in admin pages, reports, and dashboards.

### Select all rows

```xpath
//table[@id='usersTable']//tr
```

### Select table body rows

```xpath
//table[@id='usersTable']//tbody/tr
```

### Select a cell by row and column

```xpath
//table[@id='usersTable']//tbody/tr[1]/td[2]
```

### Find a row by cell text

```xpath
//table[@id='usersTable']//tr[td='John']
```

### Click Edit button in the same row

```xpath
//tr[td='John']//button[text()='Edit']
```

### Get status for a user

```xpath
//tr[td='John']/td[3]
```

### Find row using partial text

```xpath
//tr[contains(., 'john@example.com')]
```

Table XPath is powerful because it can locate one cell and then move to related cells or buttons in the same row.

---

## XPath for dynamic elements

Dynamic elements have changing IDs, classes, or positions.

### Dynamic id

HTML:

```html
<input id="username_42981">
```

XPath:

```xpath
//input[starts-with(@id, 'username_')]
```

### Dynamic class

```xpath
//button[contains(@class, 'submit')]
```

### Stable text with dynamic attributes

```xpath
//button[normalize-space()='Login']
```

### Stable nearby label

```xpath
//label[normalize-space()='Password']/following::input[1]
```

### Stable custom attribute

```xpath
//*[@data-testid='checkout-button']
```

Best locator order for dynamic pages:

- Stable `id`
- Stable `name`
- Stable `data-*` attribute
- Clear text
- Relationship with stable label/container
- Carefully chosen partial match
- Index only as a last option

---

## Useful XPath functions

### normalize-space()

Use `normalize-space()` to ignore extra spaces before, after, or inside text.

```xpath
//button[normalize-space()='Login']
```

Example:

```html
<button>  Login  </button>
```

The XPath still matches:

```xpath
//button[normalize-space()='Login']
```

### last()

Select the last matching element.

```xpath
(//button[text()='Delete'])[last()]
```

### position()

Select by position.

```xpath
//ul[@id='menu']/li[position()=2]
```

Short version:

```xpath
//ul[@id='menu']/li[2]
```

### not()

Select elements that do not match a condition.

```xpath
//button[not(@disabled)]
```

```xpath
//input[not(@type='hidden')]
```

---

## XPath in Selenium Java

Use XPath with `By.xpath()`.

### Find one element

```java
WebElement username = driver.findElement(By.xpath("//input[@id='username']"));
```

### Find multiple elements

```java
List<WebElement> rows = driver.findElements(By.xpath("//table[@id='usersTable']//tbody/tr"));
```

Required imports:

```java
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
```

### Click using XPath

```java
driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();
```

### Type using XPath

```java
driver.findElement(By.xpath("//input[@name='email']")).sendKeys("student@example.com");
```

### Get text using XPath

```java
String message = driver.findElement(By.xpath("//div[contains(@class, 'alert')]")).getText();
System.out.println(message);
```

---

## Best practices

- Prefer relative XPath over absolute XPath.
- Prefer stable attributes such as `id`, `name`, and `data-testid`.
- Use `normalize-space()` for visible text.
- Avoid long XPath expressions when a shorter stable locator is available.
- Avoid indexes unless the page structure is stable.
- Keep XPath readable for future maintenance.
- Test XPath in browser developer tools before using it in Selenium.
- Use explicit waits before interacting with dynamic elements.
- Do not depend on random IDs or generated class names.
- Prefer locating by business meaning, such as label, button text, or table row content.

Good XPath:

```xpath
//button[normalize-space()='Login']
```

Risky XPath:

```xpath
/html/body/div[2]/div[4]/form/div[3]/button
```

Better than a random generated id:

```xpath
//label[normalize-space()='Email']/following::input[1]
```

---

## Common mistakes and troubleshooting

### XPath matches too many elements

Problem:

```xpath
//button
```

Fix:

```xpath
//button[normalize-space()='Login']
```

### XPath does not match because of spaces

Problem:

```xpath
//button[text()='Login']
```

Fix:

```xpath
//button[normalize-space()='Login']
```

### XPath does not match nested text

Problem:

```xpath
//button[text()='Login']
```

Fix:

```xpath
//button[.='Login']
```

Or:

```xpath
//button[normalize-space()='Login']
```

### Single quote inside text

Problem text:

```text
Don't show again
```

XPath:

```xpath
//button[.="Don't show again"]
```

Use double quotes outside when the text contains a single quote.

### Element appears later

Use explicit wait:

```java
WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
WebElement login = wait.until(
    ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Login']"))
);
login.click();
```

Required imports:

```java
import java.time.Duration;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
```

---

## Practice exercises

### Exercise 1: Login form

Goal:

- Locate username by `id`
- Locate password by `name`
- Locate login button by text
- Submit the form

Skills practiced:

- Attribute XPath
- Text XPath
- Selenium Java usage

### Exercise 2: Error message

Goal:

- Trigger a validation message
- Locate the message using `contains()`
- Print the message text

Skills practiced:

- `contains(text(), 'value')`
- `getText()`
- Partial matching

### Exercise 3: Table row action

Goal:

- Locate a row by user name
- Click the Edit button in the same row
- Verify the edit page opens

Skills practiced:

- Table XPath
- Same-row button selection
- Relationship-based locators

### Exercise 4: Label-based form fields

Goal:

- Locate input fields using nearby labels
- Type values into the fields
- Submit the form

Skills practiced:

- `following-sibling`
- `parent::*`
- `following::input[1]`

### Exercise 5: Dynamic element

Goal:

- Locate an element with a changing id
- Use `starts-with()` or `contains()`
- Verify the element is displayed

Skills practiced:

- Dynamic attributes
- Partial matching
- Stable locator design

---

## Quick reference

| Use case | XPath example |
|---|---|
| By id | `//input[@id='username']` |
| By name | `//input[@name='email']` |
| By placeholder | `//input[@placeholder='Enter username']` |
| By exact text | `//button[text()='Login']` |
| By normalized text | `//button[normalize-space()='Login']` |
| By nested text | `//button[.='Login']` |
| Contains attribute | `//div[contains(@id, 'alert')]` |
| Contains text | `//p[contains(text(), 'invalid')]` |
| Starts with attribute | `//input[starts-with(@id, 'user_')]` |
| AND condition | `//input[@type='text' and @name='email']` |
| OR condition | `//input[@id='email' or @name='email']` |
| First match | `(//button[text()='Edit'])[1]` |
| Last match | `(//button[text()='Edit'])[last()]` |
| Parent | `//input[@id='email']/parent::div` |
| Child | `//form[@id='loginForm']/child::input` |
| Following sibling | `//label[text()='Email']/following-sibling::input` |
| Preceding sibling | `//input[@id='email']/preceding-sibling::label` |
| Ancestor | `//input[@id='email']/ancestor::form` |
| Descendant | `//section[@id='main']/descendant::button` |
| Following | `(//h2[text()='Login']/following::input)[1]` |
| Preceding | `(//button[text()='Submit']/preceding::input)[last()]` |
| Table row by text | `//tr[td='John']` |
| Button in same row | `//tr[td='John']//button[text()='Edit']` |

---

## Sample complete test flow

```java
WebDriver driver = new ChromeDriver();
driver.manage().window().maximize();
driver.get("https://example.com/login");

driver.findElement(By.xpath("//input[@id='username']")).sendKeys("student1");
driver.findElement(By.xpath("//input[@name='password']")).sendKeys("password123");
driver.findElement(By.xpath("//button[normalize-space()='Login']")).click();

String heading = driver.findElement(By.xpath("//h1[normalize-space()='Dashboard']")).getText();
System.out.println(heading);

driver.quit();
```

---

## Student learning outcome

After completing this handbook, students should be able to:

- Explain the difference between absolute and relative XPath
- Write XPath using attributes, text, contains, starts-with, and multiple conditions
- Use parent, child, sibling, ancestor, descendant, following, and preceding axes
- Build XPath for forms, tables, and dynamic elements
- Use XPath confidently in Selenium Java tests
- Choose stable, readable XPath locators for real automation projects

XPath is a core Selenium skill because it helps testers locate elements even when the page structure is complex or dynamic.
