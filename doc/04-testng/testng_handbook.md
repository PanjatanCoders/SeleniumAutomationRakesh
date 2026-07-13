# TestNG Tutorial Handbook

This handbook is a student-friendly guide for learning TestNG with Selenium WebDriver and Java. It explains how to structure tests, use annotations, write assertions, run test groups, pass test data, and organize automation scripts professionally.

---

## Table of contents

- Why TestNG matters
- What is TestNG?
- Maven setup
- Basic TestNG test
- Common TestNG annotations
- Test execution order
- Assertions
- Soft assertions
- Before and after methods
- Before and after class
- Before and after suite
- Test priority
- Enable and disable tests
- Test groups
- Depends on methods
- DataProvider
- Parameters from testng.xml
- Expected exceptions
- Timeout in tests
- Parallel execution overview
- testng.xml suite file
- TestNG with Selenium WebDriver
- Best practices
- Common mistakes and troubleshooting
- Practice exercises
- Quick reference

---

## Why TestNG matters

Selenium WebDriver controls the browser, but it does not provide a complete test framework by itself. TestNG helps students organize Selenium tests into a proper automation framework.

TestNG is important because it provides:

- Test annotations such as `@Test`, `@BeforeMethod`, and `@AfterMethod`
- Assertions for validation
- Test grouping
- Test priority
- Data-driven testing using `@DataProvider`
- Suite execution using `testng.xml`
- Parallel execution support
- Better reporting than plain Java main methods

Students should learn TestNG because real automation projects need clear test structure, setup, teardown, validation, and controlled execution.

---

## What is TestNG?

TestNG is a Java testing framework. It is commonly used with Selenium WebDriver for writing and running automated test cases.

Simple TestNG test:

```java
import org.testng.annotations.Test;

public class LoginTest {

    @Test
    public void loginWithValidUser() {
        System.out.println("Login test executed");
    }
}
```

The `@Test` annotation tells TestNG that the method is a test case.

---

## Maven setup

If TestNG is not already configured in `pom.xml`, add a TestNG dependency.

Example:

```xml
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.10.2</version>
    <scope>test</scope>
</dependency>
```

Recommended Maven Surefire plugin setup:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.5.2</version>
    <configuration>
        <suiteXmlFiles>
            <suiteXmlFile>testng.xml</suiteXmlFile>
        </suiteXmlFiles>
    </configuration>
</plugin>
```

After setup, tests can be run from the IDE or with Maven:

```bash
mvn test
```

---

## Basic TestNG test

Example:

```java
import org.testng.Assert;
import org.testng.annotations.Test;

public class BasicTest {

    @Test
    public void verifyTitle() {
        String actualTitle = "Dashboard";
        String expectedTitle = "Dashboard";

        Assert.assertEquals(actualTitle, expectedTitle);
    }
}
```

This test passes because both values are equal.

---

## Common TestNG annotations

| Annotation | Purpose |
|---|---|
| `@Test` | Marks a method as a test case |
| `@BeforeMethod` | Runs before each test method |
| `@AfterMethod` | Runs after each test method |
| `@BeforeClass` | Runs once before the first test method in a class |
| `@AfterClass` | Runs once after all test methods in a class |
| `@BeforeTest` | Runs before a `<test>` section in `testng.xml` |
| `@AfterTest` | Runs after a `<test>` section in `testng.xml` |
| `@BeforeSuite` | Runs once before the whole suite |
| `@AfterSuite` | Runs once after the whole suite |
| `@DataProvider` | Supplies test data to a test method |
| `@Parameters` | Reads values from `testng.xml` |

---

## Test execution order

Basic execution flow:

```text
@BeforeSuite
@BeforeTest
@BeforeClass
@BeforeMethod
@Test
@AfterMethod
@BeforeMethod
@Test
@AfterMethod
@AfterClass
@AfterTest
@AfterSuite
```

Example:

```java
public class ExecutionOrderTest {

    @BeforeMethod
    public void setUp() {
        System.out.println("Before method");
    }

    @Test
    public void testOne() {
        System.out.println("Test one");
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("After method");
    }
}
```

For every `@Test`, TestNG runs `@BeforeMethod` first and `@AfterMethod` after it.

---

## Assertions

Assertions validate actual results against expected results.

Required import:

```java
import org.testng.Assert;
```

### Equals assertion

```java
Assert.assertEquals(actualTitle, expectedTitle);
```

### True assertion

```java
Assert.assertTrue(driver.getCurrentUrl().contains("dashboard"));
```

### False assertion

```java
Assert.assertFalse(errorMessage.isDisplayed());
```

### Not null assertion

```java
Assert.assertNotNull(userName);
```

### Fail a test manually

```java
Assert.fail("Login failed due to invalid dashboard title");
```

Assertions are important because a Selenium script without validation is only browser automation, not a real test.

---

## Soft assertions

Normal assertions stop the test immediately when they fail. Soft assertions collect multiple failures and report them at the end.

Required import:

```java
import org.testng.asserts.SoftAssert;
```

Example:

```java
SoftAssert softAssert = new SoftAssert();

softAssert.assertEquals(actualTitle, expectedTitle);
softAssert.assertTrue(isDashboardVisible);
softAssert.assertEquals(userName, "student1");

softAssert.assertAll();
```

Important:

Always call `assertAll()`. If it is missing, TestNG may not report the soft assertion failures.

---

## Before and after methods

`@BeforeMethod` and `@AfterMethod` are commonly used for browser setup and cleanup.

```java
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void loginTest() {
        driver.get("https://example.com/login");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
```

Use this when each test should start with a fresh browser.

---

## Before and after class

`@BeforeClass` runs once before all tests in the class. `@AfterClass` runs once after all tests in the class.

```java
@BeforeClass
public void beforeClass() {
    System.out.println("Open browser once for this class");
}

@AfterClass
public void afterClass() {
    System.out.println("Close browser after this class");
}
```

Use this when multiple tests in the same class can share setup.

---

## Before and after suite

`@BeforeSuite` and `@AfterSuite` run once for the full suite.

```java
@BeforeSuite
public void beforeSuite() {
    System.out.println("Suite setup");
}

@AfterSuite
public void afterSuite() {
    System.out.println("Suite cleanup");
}
```

Common uses:

- Start report configuration
- Load global test data
- Clean output folders
- Finalize reports

---

## Test priority

Priority controls the execution order of test methods.

```java
@Test(priority = 1)
public void openLoginPage() {
}

@Test(priority = 2)
public void enterCredentials() {
}
```

Important note:

Tests should be independent when possible. Do not overuse priority to create dependent test flows.

---

## Enable and disable tests

Disable a test temporarily:

```java
@Test(enabled = false)
public void skippedTest() {
}
```

Use this carefully. A disabled test is not executed.

---

## Test groups

Groups help run selected sets of tests.

```java
@Test(groups = {"smoke"})
public void loginSmokeTest() {
}

@Test(groups = {"regression"})
public void fullLoginValidation() {
}
```

Run groups from `testng.xml`:

```xml
<suite name="Automation Suite">
    <test name="Smoke Tests">
        <groups>
            <run>
                <include name="smoke"/>
            </run>
        </groups>
        <classes>
            <class name="tests.LoginTest"/>
        </classes>
    </test>
</suite>
```

Useful group names:

- `smoke`
- `regression`
- `sanity`
- `login`
- `checkout`
- `critical`

---

## Depends on methods

`dependsOnMethods` makes one test depend on another test.

```java
@Test
public void login() {
}

@Test(dependsOnMethods = {"login"})
public void viewDashboard() {
}
```

If `login()` fails, `viewDashboard()` is skipped.

Use dependencies carefully. In many professional test suites, tests are kept independent to reduce cascading failures.

---

## DataProvider

`@DataProvider` is used for data-driven testing.

Example:

```java
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginDataTest {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
            {"student1", "password123"},
            {"student2", "password456"},
            {"invalid", "wrongpass"}
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password) {
        System.out.println(username + " - " + password);
    }
}
```

TestNG runs the test once for each row of data.

Use DataProvider for:

- Login data
- Search keywords
- Form validation data
- Multiple role testing
- Positive and negative test scenarios

---

## Parameters from testng.xml

Use `@Parameters` to pass values from `testng.xml` into tests.

Java:

```java
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParameterTest {

    @Test
    @Parameters({"browser", "url"})
    public void launchApplication(String browser, String url) {
        System.out.println(browser);
        System.out.println(url);
    }
}
```

testng.xml:

```xml
<suite name="Automation Suite">
    <parameter name="browser" value="chrome"/>
    <parameter name="url" value="https://example.com"/>

    <test name="Parameter Test">
        <classes>
            <class name="tests.ParameterTest"/>
        </classes>
    </test>
</suite>
```

Parameters are useful for environment values such as browser name, URL, username, or execution mode.

---

## Expected exceptions

Use `expectedExceptions` when a test should pass only if a specific exception occurs.

```java
@Test(expectedExceptions = ArithmeticException.class)
public void divideByZeroTest() {
    int result = 10 / 0;
}
```

This is useful for unit-level tests. For Selenium UI tests, prefer validating user-facing error messages instead of expecting Java exceptions.

---

## Timeout in tests

Use timeout when a test must finish within a specific time.

```java
@Test(timeOut = 5000)
public void testMustFinishInFiveSeconds() {
}
```

The value is in milliseconds. If the test takes longer than 5000 milliseconds, TestNG fails it.

---

## Parallel execution overview

TestNG can run tests in parallel to reduce execution time.

Example `testng.xml`:

```xml
<suite name="Parallel Suite" parallel="methods" thread-count="2">
    <test name="Parallel Tests">
        <classes>
            <class name="tests.LoginTest"/>
        </classes>
    </test>
</suite>
```

Important notes:

- Parallel execution requires careful WebDriver management.
- Do not share the same `WebDriver` instance across parallel tests.
- Use separate browser instances for separate threads.
- Keep test data independent.

Students should first learn normal execution before using parallel execution.

---

## testng.xml suite file

`testng.xml` controls suite-level execution.

Basic example:

```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Selenium Suite">
    <test name="Login Tests">
        <classes>
            <class name="tests.LoginTest"/>
        </classes>
    </test>
</suite>
```

Multiple classes:

```xml
<suite name="Regression Suite">
    <test name="Regression Tests">
        <classes>
            <class name="tests.LoginTest"/>
            <class name="tests.SearchTest"/>
            <class name="tests.CheckoutTest"/>
        </classes>
    </test>
</suite>
```

---

## TestNG with Selenium WebDriver

Complete Selenium TestNG example:

```java
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void validLoginTest() {
        driver.get("https://example.com/login");

        driver.findElement(By.id("username")).sendKeys("student1");
        driver.findElement(By.id("password")).sendKeys("password123");
        driver.findElement(By.id("loginButton")).click();

        WebElement heading = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))
        );

        Assert.assertEquals(heading.getText(), "Dashboard");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
```

This example shows setup, test execution, wait, assertion, and cleanup.

---

## Best practices

- Keep each test independent when possible.
- Use `@BeforeMethod` for browser setup when every test needs a fresh browser.
- Use `@AfterMethod` to close the browser after each test.
- Always use assertions to validate test results.
- Use `@DataProvider` for repeated tests with different data.
- Use groups to separate smoke, sanity, and regression tests.
- Avoid depending heavily on priority for test flow.
- Keep test names clear and meaningful.
- Put reusable browser setup in a base test class.
- Keep page actions inside page object classes as the framework grows.
- Be careful with parallel execution and WebDriver sharing.

---

## Common mistakes and troubleshooting

### Test method does not run

Check:

- Method has `@Test`
- Class is included in `testng.xml`
- Test is not disabled with `enabled = false`
- Method is public
- TestNG dependency is configured

### Assertions are missing

Problem:

The browser opens and performs actions, but nothing is validated.

Fix:

```java
Assert.assertEquals(driver.getTitle(), "Dashboard");
```

### Browser stays open after test

Problem:

`driver.quit()` is missing or not reached.

Fix:

```java
@AfterMethod
public void tearDown() {
    if (driver != null) {
        driver.quit();
    }
}
```

### Soft assertions do not fail the test

Problem:

`assertAll()` is missing.

Fix:

```java
softAssert.assertAll();
```

### DataProvider parameter mismatch

Problem:

The number of values returned by `DataProvider` does not match the test method parameters.

Fix:

```java
@DataProvider(name = "loginData")
public Object[][] loginData() {
    return new Object[][] {
        {"student1", "password123"}
    };
}

@Test(dataProvider = "loginData")
public void loginTest(String username, String password) {
}
```

### Parallel tests fail randomly

Possible reasons:

- Shared WebDriver instance
- Shared test data
- Tests depending on execution order
- Static variables used incorrectly

Fix:

- Use separate WebDriver per test thread
- Keep tests independent
- Avoid shared mutable state
- Start with sequential execution before parallel execution

---

## Practice exercises

### Exercise 1: First TestNG test

Goal:

- Create a class with one `@Test` method
- Print a message
- Run it from the IDE

Skills practiced:

- `@Test`
- TestNG execution

### Exercise 2: Selenium setup and teardown

Goal:

- Open browser in `@BeforeMethod`
- Navigate to a page in `@Test`
- Close browser in `@AfterMethod`

Skills practiced:

- `@BeforeMethod`
- `@AfterMethod`
- WebDriver lifecycle

### Exercise 3: Assertions

Goal:

- Open a page
- Validate title or URL
- Fail the test intentionally once
- Fix the expected value

Skills practiced:

- `Assert.assertEquals()`
- `Assert.assertTrue()`

### Exercise 4: DataProvider login

Goal:

- Create a `DataProvider` with three username/password rows
- Run one login test with all rows
- Print each username and password

Skills practiced:

- `@DataProvider`
- Data-driven testing

### Exercise 5: Groups

Goal:

- Create two smoke tests and two regression tests
- Run only smoke tests using `testng.xml`

Skills practiced:

- `groups`
- `testng.xml`

---

## Quick reference

| Use case | TestNG example |
|---|---|
| Test method | `@Test` |
| Before each test | `@BeforeMethod` |
| After each test | `@AfterMethod` |
| Before class | `@BeforeClass` |
| After class | `@AfterClass` |
| Assertion equals | `Assert.assertEquals(actual, expected)` |
| Assertion true | `Assert.assertTrue(condition)` |
| Soft assertion | `SoftAssert softAssert = new SoftAssert()` |
| Data provider | `@DataProvider(name = "loginData")` |
| Use data provider | `@Test(dataProvider = "loginData")` |
| Groups | `@Test(groups = {"smoke"})` |
| Priority | `@Test(priority = 1)` |
| Disable test | `@Test(enabled = false)` |
| Depends on method | `@Test(dependsOnMethods = {"login"})` |
| Expected exception | `@Test(expectedExceptions = Exception.class)` |
| Timeout | `@Test(timeOut = 5000)` |
| XML parameter | `@Parameters({"browser"})` |

---

## Sample complete testng.xml

```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Student Automation Suite">
    <test name="Smoke Test">
        <groups>
            <run>
                <include name="smoke"/>
            </run>
        </groups>
        <classes>
            <class name="tests.LoginTest"/>
        </classes>
    </test>
</suite>
```

---

## Student learning outcome

After completing this handbook, students should be able to:

- Explain why TestNG is used with Selenium
- Create and run TestNG test methods
- Use setup and teardown annotations
- Write hard assertions and soft assertions
- Organize tests with groups, priorities, and dependencies
- Create data-driven tests using `@DataProvider`
- Configure and run tests from `testng.xml`
- Understand the basics of parallel execution

TestNG is a key step from simple Selenium scripts to a maintainable automation framework.
