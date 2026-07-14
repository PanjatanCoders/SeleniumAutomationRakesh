# Page Object Model (POM) Handbook

This handbook is a complete, student-friendly guide to the Page Object Model — the most widely used design pattern in Selenium automation. It explains what POM is, how to build it correctly in a real project, what to avoid, how to make pages robust and reliable, and how to run tests safely in parallel using a thread-safe driver.

You have already learned Java, Selenium, TestNG, and how to choose a framework approach. This handbook goes deep on the *one pattern* that professional Selenium teams actually build on: POM.

---

## Table of contents

- Why POM matters
- What is the Page Object Model?
- The core rules of POM
- Anatomy of a page class
- A first complete example
- The BasePage class
- The BaseTest class
- Returning page objects (page chaining)
- Keeping waits inside page objects
- Component objects (for reusable page parts)
- Locators: By vs PageFactory
- Configuration and the ConfigReader
- A recommended real-project structure
- Making page objects robust
- Thread-safe driver for parallel execution
- Parallel-safe POM checklist
- Data-driven tests with POM
- What to avoid in POM
- Best practices
- Common mistakes and troubleshooting
- Practice exercises
- Quick reference
- Student learning outcome

---

## Why POM matters

Without a pattern, Selenium tests repeat the same locators and steps everywhere. When one button's `id` changes, you edit dozens of test methods.

The Page Object Model solves this by giving **each page its own class**. Locators and actions live in that one class. Tests call the page's methods and never touch locators directly.

The payoff:

- A UI change is a **one-line fix** in one class.
- Tests read like **business steps**, not Selenium code.
- Code is **reused** across hundreds of tests.
- New team members find things fast because every page follows the same shape.

> Key idea: A test should say *what* the user does ("log in as student1"). A page object knows *how* to do it ("type into `#username`, click `#loginButton`").

---

## What is the Page Object Model?

The Page Object Model is a design pattern where:

- Each web page (or major UI area) is represented by a **Java class**.
- That class holds the page's **locators** and **action methods**.
- **Test classes** use those methods and contain the **assertions**.

A clean separation of responsibilities:

| Layer | Responsibility | Contains |
|---|---|---|
| Page object | How to interact with a page | Locators + action methods |
| Test class | What to verify | Test flow + assertions |

Page objects **do not** contain assertions. Tests **do not** contain locators. This single discipline is what makes POM powerful.

---

## The core rules of POM

Memorize these. Everything else is detail.

1. **One class per page** (or per meaningful component).
2. **Locators live only in page classes** — never in tests.
3. **Page methods represent user actions** (`login`, `search`, `addToCart`).
4. **Page methods return page objects** — the next page, or the same page.
5. **Assertions live in tests, not in page objects.**
6. **No `WebDriver` details leak into tests** — tests should not call `findElement`.

> A quick test of good POM: open a test class. If you see a single `By.id(...)` or `driver.findElement(...)`, a rule was broken.

---

## Anatomy of a page class

A page class has four parts:

```java
public class LoginPage {

    // 1. Driver reference (passed in, never created here)
    private WebDriver driver;

    // 2. Locators (private, one place)
    private By username    = By.id("username");
    private By password    = By.id("password");
    private By loginButton = By.id("loginButton");

    // 3. Constructor (receives the driver)
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // 4. Action methods (public, user-focused)
    public DashboardPage login(String user, String pass) {
        driver.findElement(username).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(loginButton).click();
        return new DashboardPage(driver);
    }
}
```

Notes:

- The page **receives** the driver; it never calls `new ChromeDriver()`.
- Locators are `private` — tests cannot reach them.
- Methods are named after **what the user does**, and return the **next page**.

---

## A first complete example

Two page objects and a test that uses them.

### LoginPage

```java
public class LoginPage {
    private WebDriver driver;

    private By username    = By.id("username");
    private By password    = By.id("password");
    private By loginButton = By.id("loginButton");
    private By errorMessage = By.cssSelector(".error-message");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public DashboardPage loginExpectingSuccess(String user, String pass) {
        driver.findElement(username).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(loginButton).click();
        return new DashboardPage(driver);
    }

    public LoginPage loginExpectingFailure(String user, String pass) {
        driver.findElement(username).sendKeys(user);
        driver.findElement(password).sendKeys(pass);
        driver.findElement(loginButton).click();
        return this;   // stay on the same page
    }

    public String getErrorMessage() {
        return driver.findElement(errorMessage).getText();
    }
}
```

### DashboardPage

```java
public class DashboardPage {
    private WebDriver driver;

    private By heading = By.tagName("h1");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getHeading() {
        return driver.findElement(heading).getText();
    }
}
```

### The test (assertions live here)

```java
public class LoginTests {
    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://example.com/login");
    }

    @Test
    public void validLoginShowsDashboard() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboard = loginPage.loginExpectingSuccess("student1", "password123");
        Assert.assertEquals(dashboard.getHeading(), "Dashboard");
    }

    @Test
    public void invalidLoginShowsError() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginExpectingFailure("student1", "wrongpass");
        Assert.assertEquals(loginPage.getErrorMessage(), "Invalid credentials");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
```

Notice how readable the test is. It expresses intent, not mechanics.

---

## The BasePage class

Every page shares common needs: a driver, a wait, small helpers. Put them in a **BasePage** that all pages extend. This removes duplication.

```java
public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Reusable, robust helpers used by every page
    protected void type(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected String getText(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
```

Now `LoginPage` becomes cleaner and automatically robust (every action waits):

```java
public class LoginPage extends BasePage {

    private By username    = By.id("username");
    private By password    = By.id("password");
    private By loginButton = By.id("loginButton");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public DashboardPage login(String user, String pass) {
        type(username, user);
        type(password, pass);
        click(loginButton);
        return new DashboardPage(driver);
    }
}
```

> This is the single biggest upgrade to a real POM: waits are built into `type`, `click`, and `getText`, so no page ever interacts with an element that is not ready.

---

## The BaseTest class

Just like pages share a BasePage, tests share a **BaseTest** for browser setup and teardown. This keeps every test class small.

```java
public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://example.com/login");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
```

Test classes extend it:

```java
public class LoginTests extends BaseTest {

    @Test
    public void validLogin() {
        DashboardPage dashboard = new LoginPage(driver).login("student1", "password123");
        Assert.assertEquals(dashboard.getHeading(), "Dashboard");
    }
}
```

> Warning: This simple BaseTest uses a plain `driver` field. That is fine for **sequential** runs. For **parallel** runs you must make the driver thread-safe — covered later in this handbook.

---

## Returning page objects (page chaining)

A well-designed page method returns the page the user lands on. This lets tests read as a fluent journey.

```java
DashboardPage dashboard = new LoginPage(driver)
        .login("student1", "password123");

OrdersPage orders = dashboard
        .openMenu()
        .goToOrders();

Assert.assertTrue(orders.hasOrders());
```

Rules for return values:

- If the action **navigates** to a new page → return the **new** page object.
- If the action **stays** on the same page → return `this`.
- If the action can succeed **or** fail (like login) → provide **two methods** (`loginExpectingSuccess` / `loginExpectingFailure`) so the return type is honest.

Do not force chaining where it does not fit. Clarity beats cleverness.

---

## Keeping waits inside page objects

Timing is the number-one cause of flaky Selenium tests. In POM, **waits belong inside page methods**, not in tests.

Bad — the test knows about timing:

```java
Thread.sleep(3000);                       // never in real tests
loginPage.login("student1", "password123");
```

Good — the page handles timing through BasePage helpers:

```java
public DashboardPage login(String user, String pass) {
    type(username, user);      // waits for visibility internally
    type(password, pass);
    click(loginButton);        // waits for clickability internally
    return new DashboardPage(driver);
}
```

Because `type` and `click` already wait, the test never worries about timing. This is what makes POM tests stable.

> Rule: A test should never contain `Thread.sleep`, `WebDriverWait`, or `ExpectedConditions`. Those belong in the page layer.

---

## Component objects (for reusable page parts)

Some UI parts appear on many pages: a header, a navigation menu, a search bar, a cookie banner. Do not copy their locators into every page. Give them their **own component class** and reuse it.

```java
public class HeaderComponent extends BasePage {

    private By searchBox   = By.id("globalSearch");
    private By cartIcon    = By.id("cart");
    private By logoutLink  = By.id("logout");

    public HeaderComponent(WebDriver driver) {
        super(driver);
    }

    public SearchResultsPage search(String term) {
        type(searchBox, term);
        driver.findElement(searchBox).sendKeys(Keys.ENTER);
        return new SearchResultsPage(driver);
    }

    public LoginPage logout() {
        click(logoutLink);
        return new LoginPage(driver);
    }
}
```

Any page that shows the header can expose it:

```java
public class DashboardPage extends BasePage {
    public DashboardPage(WebDriver driver) { super(driver); }

    public HeaderComponent header() {
        return new HeaderComponent(driver);
    }
}
```

Usage in a test:

```java
SearchResultsPage results = new LoginPage(driver)
        .login("student1", "password123")
        .header()
        .search("laptop");
```

> Component objects prevent the most common POM smell: the same header/menu locators copy-pasted into ten page classes.

---

## Locators: By vs PageFactory

There are two ways to define locators. Know both; prefer one.

### Plain `By` (recommended)

```java
private By loginButton = By.id("loginButton");
```

- Explicit and easy to read
- Combines naturally with explicit waits
- No hidden proxy behavior

### PageFactory `@FindBy`

```java
@FindBy(id = "loginButton")
private WebElement loginButton;

public LoginPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
}
```

- Slightly shorter declarations
- `WebElement` fields are lazy proxies that can throw `StaleElementReferenceException` after the DOM changes
- Harder to combine cleanly with explicit waits

### Recommendation

Use **plain `By` locators with a BasePage that waits**. It is explicit, robust, and avoids stale-element surprises. Learn PageFactory for interviews and legacy code, but do not reach for it by default.

---

## Configuration and the ConfigReader

Never hard-code the browser, URL, or timeouts inside pages or tests. Read them from a properties file. This project already uses a `config/config.properties` file for exactly this.

`config/config.properties`:

```properties
browser=chrome
baseUrl=https://example.com/login
explicitWait=10
headless=true
```

A small reader:

```java
public class ConfigReader {
    private static final Properties props = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream("config/config.properties")) {
            props.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Could not load config.properties", e);
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static int getInt(String key) {
        return Integer.parseInt(props.getProperty(key));
    }
}
```

Now BaseTest and BasePage read values instead of hard-coding them:

```java
driver.get(ConfigReader.get("baseUrl"));
this.wait = new WebDriverWait(driver, Duration.ofSeconds(ConfigReader.getInt("explicitWait")));
```

> Benefit: the same suite runs against dev, staging, or production by changing one file — no code edits.

---

## A recommended real-project structure

A proven layout for a POM-based framework. It maps directly onto everything above.

```text
src
 └── main/java
      ├── base
      │    ├── BasePage.java        (waits + reusable element helpers)
      │    └── DriverManager.java   (thread-safe driver — see below)
      ├── pages
      │    ├── LoginPage.java
      │    ├── DashboardPage.java
      │    └── SearchResultsPage.java
      ├── components
      │    └── HeaderComponent.java
      ├── config
      │    └── ConfigReader.java
      └── utils
           ├── WaitUtils.java
           └── ScreenshotUtils.java
 └── test/java
      ├── base
      │    └── BaseTest.java        (@BeforeMethod / @AfterMethod)
      └── tests
           ├── LoginTests.java
           └── SearchTests.java
config/config.properties
testng_regression.xml               (referenced by Surefire in pom.xml)
pom.xml
```

- `pages` and `components` are the POM heart.
- `base` holds shared page and driver logic.
- Tests are thin: they wire pages together and assert.

---

## Making page objects robust

"Robust" means the page keeps working when the application is a little slow, a little dynamic, or a little unpredictable. Techniques:

### 1. Always wait before interacting

Handled by BasePage `type` / `click` / `getText`. No raw `driver.findElement(...).click()` in page methods.

### 2. Wait for the page to be "loaded" before using it

Give critical pages a way to confirm they are ready:

```java
public class DashboardPage extends BasePage {
    private By heading = By.tagName("h1");

    public DashboardPage(WebDriver driver) {
        super(driver);
        wait.until(ExpectedConditions.visibilityOfElementLocated(heading)); // confirm loaded
    }
}
```

### 3. Handle "maybe present" elements safely

Cookie banners and popups may or may not appear:

```java
public void dismissCookieBannerIfPresent() {
    try {
        WebElement banner = new WebDriverWait(driver, Duration.ofSeconds(2))
                .until(ExpectedConditions.elementToBeClickable(By.id("acceptCookies")));
        banner.click();
    } catch (TimeoutException ignored) {
        // banner not shown — that's fine
    }
}
```

### 4. Re-find elements to avoid stale references

Because BasePage helpers take a `By` and re-locate each time, they naturally avoid `StaleElementReferenceException` that plagues cached `WebElement` fields.

### 5. Keep locators resilient

Prefer stable locators (`id`, `data-*` attributes) over brittle ones (long absolute XPath, text that changes with language). A robust locator survives small UI changes.

### 6. Capture a screenshot on failure

```java
public class ScreenshotUtils {
    public static void capture(WebDriver driver, String name) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File("screenshots/" + name + ".png")); // commons-io
        } catch (IOException e) {
            System.out.println("Screenshot failed: " + e.getMessage());
        }
    }
}
```

> This project already includes `commons-io`, which provides `FileUtils.copyFile` for saving screenshots.

---

## Thread-safe driver for parallel execution

This is the part that separates a hobby framework from a professional one.

### The problem

When TestNG runs tests in parallel (`parallel="methods"` or `"classes"`), several tests run **at the same time on different threads**. If they all share **one** `WebDriver` field, they fight over the same browser: tests click each other's buttons, results are random, everything breaks.

### The solution: `ThreadLocal<WebDriver>`

`ThreadLocal` gives **each thread its own separate copy** of the driver. Thread A gets its own browser; thread B gets a different one. They never collide.

```java
public class DriverManager {

    // One driver PER THREAD, not one shared driver
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public static void createDriver() {
        ChromeOptions options = new ChromeOptions();
        if (Boolean.parseBoolean(ConfigReader.get("headless"))) {
            options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage");
        }
        options.addArguments("--window-size=1920,1080");
        DRIVER.set(new ChromeDriver(options));
    }

    public static WebDriver getDriver() {
        return DRIVER.get();
    }

    public static void quitDriver() {
        WebDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();   // critical: clear the thread's slot to avoid leaks
        }
    }
}
```

### A parallel-safe BaseTest

```java
public class BaseTest {

    @BeforeMethod
    public void setUp() {
        DriverManager.createDriver();
        getDriver().manage().window().maximize();
        getDriver().get(ConfigReader.get("baseUrl"));
    }

    protected WebDriver getDriver() {
        return DriverManager.getDriver();   // always the current thread's driver
    }

    @AfterMethod
    public void tearDown() {
        DriverManager.quitDriver();
    }
}
```

Tests now get the driver through `getDriver()`:

```java
public class LoginTests extends BaseTest {

    @Test
    public void validLogin() {
        DashboardPage dashboard = new LoginPage(getDriver()).login("student1", "password123");
        Assert.assertEquals(dashboard.getHeading(), "Dashboard");
    }
}
```

### Turning on parallel execution in testng.xml

```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Regression Suite" parallel="methods" thread-count="3">
    <test name="POM Tests">
        <classes>
            <class name="tests.LoginTests"/>
            <class name="tests.SearchTests"/>
        </classes>
    </test>
</suite>
```

`thread-count="3"` runs up to three browsers at once, each isolated by `ThreadLocal`.

---

## Parallel-safe POM checklist

Before you enable parallel runs, verify every item:

| Requirement | Why |
|---|---|
| Driver stored in `ThreadLocal`, not a shared static field | Each thread needs its own browser |
| `DRIVER.remove()` called in teardown | Prevents thread/memory leaks |
| No `static` mutable state shared across tests | Threads would corrupt shared data |
| Each test is independent (no order dependency) | Parallel order is not guaranteed |
| Page objects hold **no** static WebElement/driver | They must use the current thread's driver |
| Test data not shared/mutated between tests | Avoids race conditions |
| Screenshots named uniquely (include test/thread name) | Files won't overwrite each other |
| `@BeforeMethod`/`@AfterMethod` create/quit per test | Fresh isolated browser per test |

> Golden rule of parallel POM: **nothing that changes may be shared between threads.** Give each thread its own driver, its own data, its own browser.

---

## Data-driven tests with POM

POM combines perfectly with the TestNG `@DataProvider` you already know. The page object stays the same; only the data varies.

```java
public class LoginTests extends BaseTest {

    @DataProvider(name = "credentials")
    public Object[][] credentials() {
        return new Object[][] {
            {"student1", "password123", true},
            {"student2", "wrongpass",  false},
            {"",         "",           false}
        };
    }

    @Test(dataProvider = "credentials")
    public void loginValidation(String user, String pass, boolean shouldSucceed) {
        LoginPage loginPage = new LoginPage(getDriver());

        if (shouldSucceed) {
            DashboardPage dashboard = loginPage.loginExpectingSuccess(user, pass);
            Assert.assertEquals(dashboard.getHeading(), "Dashboard");
        } else {
            loginPage.loginExpectingFailure(user, pass);
            Assert.assertTrue(loginPage.getErrorMessage().length() > 0);
        }
    }
}
```

One page object, one test method, many scenarios. This is the hybrid framework in action: **POM + data-driven + TestNG**.

---

## What to avoid in POM

These are the mistakes that quietly ruin a POM framework.

### Avoid: assertions inside page objects

```java
// BAD — page object should not assert
public void login(String user, String pass) {
    ...
    Assert.assertEquals(driver.getTitle(), "Dashboard"); // NO
}
```

Assertions belong in tests. A page object that asserts cannot be reused for negative tests.

### Avoid: locators in test classes

```java
// BAD — locator leaked into the test
driver.findElement(By.id("loginButton")).click();
```

Move it into the page class. This is the whole point of POM.

### Avoid: `Thread.sleep` anywhere

```java
Thread.sleep(5000); // BAD — flaky and slow
```

Use waits in BasePage instead.

### Avoid: creating the driver inside a page object

```java
// BAD — page must receive the driver, not make one
public LoginPage() {
    this.driver = new ChromeDriver();
}
```

Pages receive the driver so they work in parallel and in any environment.

### Avoid: a shared static driver for parallel runs

```java
// BAD for parallel — all threads share one browser
public static WebDriver driver;
```

Use `ThreadLocal<WebDriver>`.

### Avoid: giant "god" page objects

A single 2,000-line page class is unmaintainable. Split big pages into **component objects**.

### Avoid: returning `void` from navigation methods

If `goToOrders()` returns `void`, the test cannot chain and loses type safety. Return the page it navigates to.

### Avoid: public locators

Locators should be `private`. If they are public, tests will start using them directly and break encapsulation.

---

## Best practices

- One class per page; split large pages into component objects.
- Keep **all** locators private and inside page classes.
- Name methods after **user actions** (`login`, `search`), not mechanics (`clickButton`).
- Put waits in a **BasePage**; never in tests.
- Page methods **return page objects**; assertions stay in tests.
- Read browser, URL, and timeouts from `config.properties`.
- Use plain `By` locators with waiting helpers for robustness.
- Prefer stable locators (`id`, `data-*`) over brittle XPath.
- Use `ThreadLocal<WebDriver>` and always `remove()` in teardown for parallel safety.
- Keep tests independent so they can run in any order, in parallel.
- Capture a screenshot on failure with a unique name.
- Grow gradually: start with a few pages, add components as reuse appears.

---

## Common mistakes and troubleshooting

### Tests are flaky (pass sometimes, fail sometimes)

Cause: interacting before elements are ready, or `Thread.sleep`.

Fix: route all interactions through BasePage `type` / `click` / `getText` with explicit waits.

### StaleElementReferenceException

Cause: a cached `WebElement` (often from PageFactory) used after the DOM changed.

Fix: use `By` locators re-located on each call (BasePage helpers do this automatically).

### Parallel tests interfere with each other

Cause: a shared driver or shared mutable state.

Fix: use `ThreadLocal<WebDriver>`, remove it in teardown, and keep all test data local.

### Browser stays open after tests

Cause: teardown not reached or driver not quit.

Fix: quit in `@AfterMethod`; in parallel use `DriverManager.quitDriver()` which also calls `remove()`.

### A UI change breaks many tests

Cause: locators were duplicated across tests (POM not truly followed).

Fix: ensure the locator exists in exactly one page class; update it there once.

### NullPointerException on the driver in a page

Cause: the page created its own driver or received `null`.

Fix: always pass the current driver (`new LoginPage(getDriver())`); never create a driver in a page.

### Page object grew huge and unreadable

Cause: one page absorbing every feature.

Fix: extract header, menu, table, and dialog into component objects.

---

## Practice exercises

### Exercise 1: Build a BasePage

Goal:

- Create a `BasePage` with `type`, `click`, and `getText` helpers using explicit waits
- Make `LoginPage` extend it

Skills practiced:

- Reusable, robust page interactions

### Exercise 2: Two-page flow with chaining

Goal:

- Create `LoginPage` and `DashboardPage`
- `login()` returns a `DashboardPage`
- Write a test that chains them and asserts the heading

Skills practiced:

- Page chaining and return types

### Exercise 3: Extract a component

Goal:

- Create a `HeaderComponent` with a search box
- Expose it from `DashboardPage.header()`
- Use it in a test to perform a search

Skills practiced:

- Component objects and reuse

### Exercise 4: Add configuration

Goal:

- Create `config.properties` with `browser`, `baseUrl`, and `explicitWait`
- Read them in `BaseTest` and `BasePage`

Skills practiced:

- Externalizing configuration

### Exercise 5: Make it parallel-safe

Goal:

- Introduce a `DriverManager` with `ThreadLocal<WebDriver>`
- Update `BaseTest` to use it
- Set `parallel="methods" thread-count="2"` in `testng.xml` and run

Skills practiced:

- Thread-safe driver management
- Parallel execution

---

## Quick reference

| Concept | Rule |
|---|---|
| Page class | One per page; holds locators + actions |
| Locators | Private, inside page classes only |
| Method names | Named after user actions |
| Return values | Return the next page (or `this`) |
| Assertions | In tests, never in pages |
| Waits | In BasePage, never in tests |
| Driver | Passed in; never created in a page |
| Components | Extract reusable parts (header, menu) |
| Config | Read browser/URL/waits from properties |
| Parallel driver | `ThreadLocal<WebDriver>` + `remove()` in teardown |
| Data-driven | POM + `@DataProvider` |
| Locator style | Prefer `By` with waiting helpers |

### The POM one-liner

```text
Tests know WHAT the user does; page objects know HOW; waits live in the page; drivers are per-thread.
```

---

## Student learning outcome

After completing this handbook, students should be able to:

- Explain the Page Object Model and its core rules
- Build page classes with private locators and action methods
- Create a BasePage with waiting helpers and a BaseTest for setup/teardown
- Return page objects to build readable, chained test flows
- Extract reusable UI parts into component objects
- Externalize configuration instead of hard-coding values
- Make page objects robust against timing and stale elements
- Run tests safely in parallel using `ThreadLocal<WebDriver>`
- Recognize and avoid the common POM anti-patterns

The Page Object Model is the foundation every serious Selenium framework is built on. Done well — with a BasePage for waits, component objects for reuse, and a thread-safe driver for parallel runs — it turns fragile scripts into a fast, maintainable, professional automation suite.
