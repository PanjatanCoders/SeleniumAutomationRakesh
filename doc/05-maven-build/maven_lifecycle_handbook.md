# Maven and Its Lifecycles Handbook

This handbook is a student-friendly guide to understanding Apache Maven and its build lifecycles. It explains what Maven is, the problem it solves, how `pom.xml` works, what dependencies and scopes mean, and — the main goal — the Maven **lifecycles and phases** that every command like `mvn clean test` actually runs.

You have already used Maven in this project to add TestNG and Selenium. This handbook explains *what is really happening* when you run those commands.

---

## Table of contents

- Why Maven matters
- What is Maven?
- The problem Maven solves
- What is a build tool?
- The pom.xml file explained
- Dependencies and how Maven finds them
- Dependency scopes
- Maven repositories (local, central, remote)
- The three Maven lifecycles
- The default lifecycle phases (in order)
- The clean lifecycle
- The site lifecycle
- How phases run in sequence
- Decoding `mvn clean test`
- Surefire and Failsafe plugins
- Running TestNG through Maven
- Maven and this project
- Useful Maven commands
- Best practices
- Common mistakes and troubleshooting
- Practice exercises
- Quick reference
- Student learning outcome

---

## Why Maven matters

When you write a Selenium + TestNG project, you need many things to work together:

- The correct version of Selenium
- The correct version of TestNG
- Any helper libraries (like commons-io)
- A way to compile your Java code
- A way to run your tests
- A way to package everything

Doing this by hand — downloading each `.jar` file, keeping versions in sync, and building command lines — is slow and error-prone. Maven automates all of it.

Students must learn Maven because almost every real Java automation project uses it, and understanding its lifecycle removes the "magic" from commands like `mvn test`.

---

## What is Maven?

Apache Maven is a **build automation and dependency management tool** for Java projects.

In simple terms, Maven does three big jobs:

1. **Manages dependencies** — downloads the libraries your project needs (Selenium, TestNG) automatically.
2. **Builds your project** — compiles code, runs tests, and packages the result in a standard way.
3. **Standardizes structure** — every Maven project looks the same, so any Java developer can navigate it.

Maven is driven by a single configuration file: **`pom.xml`** (Project Object Model).

---

## The problem Maven solves

Imagine building this project *without* Maven:

- You manually download `selenium-java-4.45.0.jar`.
- Selenium itself needs dozens of other jars. You download each one.
- Each of those needs more jars (transitive dependencies). You download those too.
- You add all of them to your classpath by hand.
- A teammate has a slightly different set of jars. Tests behave differently.

With Maven, you write **one small block** in `pom.xml`:

```xml
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.45.0</version>
</dependency>
```

Maven then downloads Selenium **and everything Selenium depends on**, automatically, for every teammate identically.

> Key idea: Maven turns "download and wire up 50 jars by hand" into "declare what you want, once."

---

## What is a build tool?

A build tool takes your **source code** and turns it into a **finished, runnable product** through repeatable steps: compile, test, package.

Without a build tool, you run these steps manually and differently each time. With a build tool, the steps are defined once and run identically on your machine, your teammate's machine, and the CI server (like Jenkins).

Maven is Java's most widely used build tool. Others exist (Gradle, Ant), but Maven's lifecycle model is the one to learn first.

---

## The pom.xml file explained

`pom.xml` is the heart of a Maven project. Here is this project's `pom.xml`, explained piece by piece.

```xml
<project ...>
  <modelVersion>4.0.0</modelVersion>

  <!-- The unique "address" of this project -->
  <groupId>com.ibm</groupId>
  <artifactId>SeleniumAutomationRakesh</artifactId>
  <version>1.0-SNAPSHOT</version>
  <packaging>jar</packaging>
  ...
</project>
```

### The coordinates (how every project is identified)

| Tag | Meaning | Example here |
|---|---|---|
| `groupId` | The organization / group | `com.ibm` |
| `artifactId` | The project name | `SeleniumAutomationRakesh` |
| `version` | The project version | `1.0-SNAPSHOT` |
| `packaging` | The output type | `jar` |

Together, `groupId : artifactId : version` uniquely identify any project or library in the world. That is exactly how you refer to Selenium and TestNG too.

> `SNAPSHOT` means "still in development, not a final release." Maven treats snapshot versions as changeable.

### Properties (reusable values)

```xml
<properties>
    <maven.compiler.release>25</maven.compiler.release>
    <selenium-version>4.45.0</selenium-version>
</properties>
```

Properties are variables. Here the project targets **Java 25** and defines a Selenium version once, reused below as `${selenium-version}`.

### Build section (plugins)

```xml
<build>
    <plugins>
        <plugin>...compiler plugin...</plugin>
        <plugin>...surefire plugin...</plugin>
    </plugins>
</build>
```

Plugins are what actually *do* the work during the lifecycle (compiling, testing). More on these later.

---

## Dependencies and how Maven finds them

A **dependency** is an external library your project needs. You list them in `<dependencies>`.

This project's dependencies:

```xml
<dependencies>
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.12.0</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.seleniumhq.selenium</groupId>
        <artifactId>selenium-java</artifactId>
        <version>${selenium-version}</version>
        <scope>compile</scope>
    </dependency>
    <dependency>
        <groupId>commons-io</groupId>
        <artifactId>commons-io</artifactId>
        <version>2.22.0</version>
        <scope>compile</scope>
    </dependency>
</dependencies>
```

Each dependency uses the same **coordinates** (groupId, artifactId, version) you saw earlier. Maven looks up these coordinates and downloads the matching jar.

### Transitive dependencies

Selenium itself depends on other libraries. Maven downloads those **automatically** — you do not list them. This is called *transitive dependency resolution*, and it is one of Maven's most valuable features.

---

## Dependency scopes

**Scope** tells Maven *when* a dependency is needed. This is why TestNG uses `test` while Selenium uses `compile`.

| Scope | Available during | Included in final jar? | Example |
|---|---|---|---|
| `compile` (default) | Everywhere | Yes | selenium-java |
| `test` | Only compiling/running tests | No | testng |
| `provided` | Compile + test, but supplied at runtime by the container | No | servlet-api |
| `runtime` | Running, not compiling | Yes | JDBC drivers |
| `system` | Like provided, but you give the path | No | rarely used |

### Why TestNG uses `test` scope

TestNG is only needed to compile and run your tests. It should not be bundled into a shipped application jar. So it uses `test`. Selenium is needed by the automation code itself, so it uses `compile`.

---

## Maven repositories (local, central, remote)

A **repository** is a storage location for jars. Maven uses three levels.

```text
1. Local repository   -> a folder on YOUR machine (~/.m2/repository)
2. Central repository -> Maven's public online repository (the internet)
3. Remote repository  -> a private company repository (e.g. Nexus, Artifactory)
```

### How Maven finds a dependency

```text
Need selenium-java 4.45.0?
   ↓
Is it in the LOCAL repo (~/.m2)?  ── Yes ──> use it (fast, offline)
   ↓ No
Download from CENTRAL (internet) ──> save a copy into LOCAL ──> use it
```

The first build downloads everything (slow). Later builds reuse the local `~/.m2` copy (fast). This is also why the Dockerfile in this project runs `mvn dependency:go-offline` first — to cache dependencies in a layer so they are not re-downloaded every build.

---

## The three Maven lifecycles

This is the core concept of the handbook.

Maven has **three built-in lifecycles**. A lifecycle is an ordered list of **phases**.

| Lifecycle | Purpose |
|---|---|
| `clean` | Remove previous build output |
| `default` (or `build`) | Compile, test, package, install, deploy — the real work |
| `site` | Generate project documentation and reports |

When you run a Maven command, you name a **phase**, and Maven runs that phase **and every phase before it** in that lifecycle.

> This is the single most important Maven idea: **running a phase runs all earlier phases in the same lifecycle automatically.**

---

## The default lifecycle phases (in order)

The `default` lifecycle is where building and testing happen. It has many phases; these are the ones students must know, in execution order:

```text
validate  →  compile  →  test  →  package  →  verify  →  install  →  deploy
```

| Phase | What it does |
|---|---|
| `validate` | Check the project is correct and all needed info is available |
| `compile` | Compile the main source code (`src/main/java`) |
| `test` | Run unit/automation tests using a test framework (TestNG here) |
| `package` | Bundle compiled code into a jar or war |
| `verify` | Run checks on the packaged result (integration test results, quality gates) |
| `install` | Copy the packaged jar into your **local** repo (`~/.m2`) so other local projects can use it |
| `deploy` | Upload the packaged jar to a **remote** repo for the whole team |

### The golden rule, shown

If you run:

```bash
mvn package
```

Maven does **not** just package. It runs, in order:

```text
validate → compile → test → package
```

So `mvn package` also compiles your code and runs your tests first. If tests fail, packaging stops.

---

## The clean lifecycle

The `clean` lifecycle deletes the `target/` folder — the directory where Maven puts compiled classes and build output.

Phases:

```text
pre-clean  →  clean  →  post-clean
```

Command:

```bash
mvn clean
```

This removes old build artifacts so your next build starts fresh. It is separate from the `default` lifecycle, which is why you often combine them (next section).

---

## The site lifecycle

The `site` lifecycle generates project documentation and reports (dependency reports, test reports, project info) as a static website.

Phases:

```text
pre-site  →  site  →  post-site  →  site-deploy
```

Command:

```bash
mvn site
```

Beginners rarely need this, but it is one of the three lifecycles, so it is worth knowing it exists.

---

## How phases run in sequence

Think of a lifecycle as a train line. Each phase is a station. When you buy a ticket to a station, the train stops at **every station before it too**.

```text
default lifecycle:

validate → compile → test → package → verify → install → deploy
   |         |        |        |          |         |         |
   └─────────┴────────┴────────┴──── mvn install stops here ──┘
                                          (runs all stations up to install)
```

You cannot run `test` without first running `validate` and `compile` — Maven does them for you, always in order.

---

## Decoding `mvn clean test`

This is the command you use most in automation. Now you can read it precisely.

```bash
mvn clean test
```

It names **two phases from two different lifecycles**:

1. `clean` (from the clean lifecycle) → delete the old `target/` folder.
2. `test` (from the default lifecycle) → run `validate → compile → test`.

So the full sequence is:

```text
clean            (delete old build output)
   ↓
validate         (check the project)
   ↓
compile          (compile src/main/java)
   ↓
test             (run TestNG tests)
```

That is why `mvn clean test` is the standard "start fresh and run all tests" command in CI pipelines.

---

## Surefire and Failsafe plugins

Maven's `test` and `verify` phases do not know how to run TestNG by themselves. **Plugins** do the actual work.

| Plugin | Runs during phase | Runs what | Naming convention |
|---|---|---|---|
| `maven-surefire-plugin` | `test` | Unit / automation tests | `*Test`, `Test*`, `*Tests` |
| `maven-failsafe-plugin` | `integration-test` / `verify` | Integration tests | `*IT`, `IT*` |

### This project uses Surefire

Look at this project's `pom.xml`:

```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>3.5.2</version>
    <configuration>
        <suiteXmlFiles>
            <suiteXmlFile>testng_regression.xml</suiteXmlFile>
        </suiteXmlFiles>
    </configuration>
</plugin>
```

This tells Surefire: "During the `test` phase, run the TestNG suite defined in `testng_regression.xml`." That is the exact link between `mvn test` and your TestNG suite.

> Surefire vs Failsafe in one line: **Surefire** runs fast unit-style tests and fails the build immediately; **Failsafe** runs slower integration tests and lets the build clean up before failing.

---

## Running TestNG through Maven

Putting it all together, here is how a Maven command becomes a running TestNG suite:

```text
mvn test
   ↓
default lifecycle reaches the "test" phase
   ↓
maven-surefire-plugin is bound to the "test" phase
   ↓
Surefire reads <suiteXmlFile>testng_regression.xml</suiteXmlFile>
   ↓
TestNG executes the classes/groups in that suite
   ↓
Results printed + reports written to target/surefire-reports/
```

### Common ways to run tests

```bash
# Run the whole suite defined in pom.xml (testng_regression.xml)
mvn test

# Clean first, then run the suite
mvn clean test

# Run only one test class
mvn -B test -Dtest=LoginTests

# Skip tests during a build (compile + package only)
mvn package -DskipTests
```

---

## Maven and this project

This project ties the whole handbook together:

- **Build tool:** Maven 3.9 (see the Docker base image `maven:3.9-eclipse-temurin-25`).
- **Java version:** `maven.compiler.release` is set to **25** in `pom.xml`.
- **Dependencies:** Selenium 4.45.0 (`compile`), TestNG 7.12.0 (`test`), commons-io (`compile`).
- **Test runner:** Surefire bound to the `test` phase, pointing to `testng_regression.xml`.
- **CI/Docker:** The Dockerfile runs `mvn dependency:go-offline` (cache deps), then `mvn -B test` (run the suite). `-B` means "batch mode" — no interactive prompts, ideal for CI.

Reports from `mvn test` land in `target/surefire-reports/`, which is exactly what a CI server like Jenkins collects and displays.

---

## Useful Maven commands

| Command | What it does |
|---|---|
| `mvn validate` | Check project correctness |
| `mvn compile` | Compile main source code |
| `mvn test` | Compile and run tests |
| `mvn package` | Build the jar (runs tests first) |
| `mvn verify` | Run checks on the packaged build |
| `mvn install` | Put the jar in local `~/.m2` |
| `mvn deploy` | Upload the jar to a remote repo |
| `mvn clean` | Delete the `target/` folder |
| `mvn clean test` | Fresh build, run all tests |
| `mvn clean install` | Fresh build, test, package, install locally |
| `mvn -B test` | Run tests in batch (non-interactive) mode for CI |
| `mvn test -DskipTests` | Compile tests but do not run them |
| `mvn dependency:tree` | Show all dependencies, including transitive ones |
| `mvn dependency:go-offline` | Pre-download all dependencies |

---

## Best practices

- Keep dependency versions in `<properties>` so they are easy to update in one place.
- Use the correct **scope** — `test` for TestNG, `compile` for Selenium.
- Use `mvn clean test` in CI so every run starts fresh.
- Do not commit the `target/` folder to git (it is generated output).
- Use `-B` (batch mode) for CI/Docker runs to avoid interactive prompts.
- Run `mvn dependency:go-offline` in Docker to cache dependencies in their own layer.
- Prefer released versions of libraries; understand that `SNAPSHOT` means "in development."
- Let Maven handle transitive dependencies — do not add jars by hand.

---

## Common mistakes and troubleshooting

### Dependency not downloading

Possible reasons:

- No internet on the first build
- Wrong `groupId`, `artifactId`, or `version`
- Corporate proxy blocking Maven Central

Fix:

- Verify coordinates on `mvnrepository.com`
- Check network / proxy settings in `~/.m2/settings.xml`
- Try `mvn -U clean test` to force an update

### Tests do not run with `mvn test`

Possible reasons:

- Test class name does not match Surefire patterns (`*Test`, `*Tests`)
- The `testng_regression.xml` suite does not include the class
- Tests were skipped with `-DskipTests`

Fix:

- Match Surefire naming conventions or configure the suite XML
- Add the class to `testng_regression.xml`
- Remove `-DskipTests`

### "Fatal error compiling: invalid target release: 25"

Problem: Your installed JDK is older than the `maven.compiler.release` value (25 here).

Fix:

- Install and use JDK 25, or lower the release value to your JDK version.

### Build works locally but fails in Docker/CI

Possible reasons:

- Local machine had cached jars in `~/.m2`; the clean CI machine did not
- Browser not installed in the container (for Selenium)
- Tests not running headless in the container

Fix:

- Ensure all dependencies are declared (not relying on a local-only jar)
- Install Chrome in the image (this project's Dockerfile does)
- Run Chrome with `--headless=new` and `--no-sandbox` in containers

### `mvn package` fails because a test failed

Explanation: This is correct behavior. `package` runs `test` first, and a failing test stops the build.

Fix:

- Fix the failing test, or run `mvn package -DskipTests` only when you deliberately want to skip tests.

---

## Practice exercises

### Exercise 1: Read the pom.xml

Goal:

- Open this project's `pom.xml`
- Identify the groupId, artifactId, version, and each dependency's scope

Skills practiced:

- Reading Maven coordinates and scopes

### Exercise 2: Trace a command

Goal:

- Write down every phase that runs when you type `mvn package`
- Explain why tests run even though you did not type `test`

Skills practiced:

- Understanding lifecycle phase ordering

### Exercise 3: Run the suite

Goal:

- Run `mvn clean test`
- Open `target/surefire-reports/` and read the results

Skills practiced:

- Running TestNG through Maven
- Locating reports

### Exercise 4: Change a version safely

Goal:

- Move the TestNG version into `<properties>` as `${testng-version}`
- Update the dependency to use the property

Skills practiced:

- Centralizing versions
- Editing `pom.xml`

### Exercise 5: Explore the dependency tree

Goal:

- Run `mvn dependency:tree`
- Find three libraries Selenium pulls in transitively

Skills practiced:

- Understanding transitive dependencies

---

## Quick reference

| Concept | Key point |
|---|---|
| Maven | Build + dependency management tool for Java |
| pom.xml | The project's configuration file |
| Coordinates | groupId : artifactId : version |
| Dependency | An external library you declare |
| Scope | When a dependency is needed (`compile`, `test`, ...) |
| Local repo | `~/.m2/repository` on your machine |
| Central repo | Maven's public online repository |
| Lifecycle | An ordered set of phases (clean, default, site) |
| Phase | A step in a lifecycle (compile, test, package) |
| Golden rule | Running a phase runs all earlier phases too |
| Surefire | Plugin that runs tests during the `test` phase |
| `mvn clean test` | clean + validate + compile + test |
| `mvn install` | Also copies the jar into local `~/.m2` |

### The default lifecycle at a glance

```text
validate → compile → test → package → verify → install → deploy
```

### The three lifecycles at a glance

```text
clean:    pre-clean → clean → post-clean
default:  validate → compile → test → package → verify → install → deploy
site:     pre-site → site → post-site → site-deploy
```

---

## Student learning outcome

After completing this handbook, students should be able to:

- Explain what Maven is and the problem it solves
- Read and understand a `pom.xml`, including coordinates and scopes
- Describe how Maven resolves dependencies from local and central repositories
- Name the three Maven lifecycles and the key phases of the default lifecycle
- Explain the golden rule that running a phase runs all earlier phases
- Decode exactly what `mvn clean test` does step by step
- Understand how Surefire connects `mvn test` to a TestNG suite
- Run this project's TestNG suite through Maven and find the reports

Understanding the Maven lifecycle turns commands like `mvn clean test` from magic words into a clear, predictable sequence you fully control.
