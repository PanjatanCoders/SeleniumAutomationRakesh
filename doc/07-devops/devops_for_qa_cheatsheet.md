# DevOps Cheatsheet for QA Engineers

This cheatsheet is a fast, practical reference for QA and automation engineers who need to work with DevOps tools: Git, CI/CD, Jenkins, Docker, and the Linux commands that surround them. It is focused on the real stack used in this project — **Java, Maven, TestNG, Selenium, Jenkins, and Docker** — and shows how automated tests fit into a delivery pipeline.

This is a *cheatsheet*: dense tables, copy-paste commands, and short explanations. Use it as a lookup, not a novel.

---

## Table of contents

- Why QA engineers need DevOps
- The big picture: how code becomes a tested release
- CI vs CD explained
- Git essentials cheatsheet
- Git branching for test code
- CI/CD pipeline concepts
- Jenkins for test automation
- A Jenkins pipeline for Selenium tests
- Docker essentials for QA
- This project's Docker setup explained
- Running Selenium in CI (headless and Grid)
- Test reports and artifacts
- Linux and shell cheatsheet for QA
- Environment variables and secrets
- Best practices
- Common mistakes and troubleshooting
- Practice exercises
- Quick reference
- Student learning outcome

---

## Why QA engineers need DevOps

Writing tests is only half the job. In real teams, your tests must run **automatically** — on every code change, on a server, without you clicking "Run."

A modern QA engineer is expected to:

- Push test code to Git like any developer
- Understand how a CI server (Jenkins) triggers test runs
- Run tests inside Docker containers for consistency
- Read pipeline logs and test reports
- Use basic Linux commands on build servers

You do not need to become a DevOps engineer. You need to be **fluent enough** to plug your automation into the delivery pipeline. That is what this cheatsheet gives you.

---

## The big picture: how code becomes a tested release

```text
Developer / QA writes code
        ↓ git push
     GitHub (remote repository)
        ↓ triggers
     Jenkins (CI server)
        ↓ clones repo onto a build server / VM
     Build & Test:  mvn clean test   (often inside Docker)
        ↓
   Test reports + screenshots collected
        ↓ if all green
     Deploy to a test / staging server
        ↓
        Monitor
```

This matches the notes already in this project:

```text
your project -> GitHub (Remote)
Jenkins -> Test Server (VM -> clone git repository) -> run tests
development -> build -> deploy -> test -> deploy production -> monitor
```

Your automated tests are the **quality gate** in the middle. If they fail, the pipeline stops and nothing ships.

---

## CI vs CD explained

| Term | Full form | Meaning in plain English |
|---|---|---|
| CI | Continuous Integration | Every code change is automatically built and tested |
| CD | Continuous Delivery | Every passing build is automatically prepared for release (deploy is a button press) |
| CD | Continuous Deployment | Every passing build is automatically released to production (no button) |

For QA, **CI is where you live**: your tests run on every commit. The more your tests can be trusted, the further "right" the team can automate delivery.

---

## Git essentials cheatsheet

Git is how test code is stored, shared, and versioned. These are the commands you will use daily.

| Command | What it does |
|---|---|
| `git clone <url>` | Copy a remote repo to your machine |
| `git status` | See what changed |
| `git add <file>` | Stage a file for commit |
| `git add .` | Stage everything changed |
| `git commit -m "message"` | Save staged changes with a message |
| `git push` | Send commits to the remote (GitHub) |
| `git pull` | Get latest changes from the remote |
| `git branch` | List branches |
| `git checkout -b <name>` | Create and switch to a new branch |
| `git checkout <name>` | Switch to an existing branch |
| `git merge <name>` | Merge another branch into the current one |
| `git log --oneline` | Compact commit history |
| `git diff` | Show unstaged changes |
| `git stash` | Temporarily shelve changes |
| `git reset --hard origin/main` | Discard local changes, match remote (careful!) |

### The everyday QA Git flow

```bash
git checkout -b feature/login-tests   # start a branch
# ... write your tests ...
git add .
git commit -m "Add data-driven login tests"
git push -u origin feature/login-tests
# ... open a Pull Request on GitHub ...
```

---

## Git branching for test code

Branches let you work without breaking the shared `main` branch.

```text
main            o───o───o───────o  (always stable, always passing)
                     \         /
feature/login-tests   o───o───o     (your work in progress)
```

| Branch type | Purpose |
|---|---|
| `main` / `master` | Stable, deployable code. Never push broken tests here. |
| `feature/*` | New tests or features in progress |
| `bugfix/*` | Fixing a specific problem |
| `release/*` | Preparing a release |

Rule of thumb: **branch → commit → push → pull request → review → merge.** Your CI pipeline usually runs the tests automatically on the pull request.

---

## CI/CD pipeline concepts

A **pipeline** is an automated sequence of stages. A typical test automation pipeline looks like this:

```text
Stage 1: Checkout   -> clone the git repo
Stage 2: Build      -> mvn clean compile
Stage 3: Test       -> mvn test  (runs the TestNG suite)
Stage 4: Report     -> publish test results + screenshots
Stage 5: Deploy     -> (if green) push to test/staging server
```

| Concept | Meaning |
|---|---|
| Pipeline | The whole automated build/test/deploy process |
| Stage | A named group of steps (Build, Test, Deploy) |
| Job | A unit of work Jenkins runs |
| Agent / Node | The machine (or container) that runs the job |
| Trigger | What starts the pipeline (a git push, a schedule) |
| Artifact | A file the pipeline produces and saves (jar, report) |

---

## Jenkins for test automation

Jenkins is the most common open-source **CI server**. It watches your repository and runs your tests when code changes.

### Key Jenkins ideas

| Term | Meaning |
|---|---|
| Job / Pipeline | A configured task Jenkins runs |
| Freestyle job | Simple point-and-click job configuration |
| Pipeline job | Job defined as code in a `Jenkinsfile` |
| Build trigger | What starts a build (webhook, poll SCM, schedule) |
| Workspace | The folder where Jenkins checks out your repo |
| Executor | A slot that runs one build at a time |
| Node / Agent | A machine that runs builds |

### How Jenkins runs your Selenium tests (the flow from the project notes)

```text
Jenkins job triggered (by git push or schedule)
   ↓
Jenkins clones the GitHub repo onto a Test Server (VM)
   ↓
Runs:  mvn clean test
   ↓
Surefire runs testng_regression.xml
   ↓
Publishes TestNG / Surefire reports
   ↓
Marks build GREEN (pass) or RED (fail)
```

---

## A Jenkins pipeline for Selenium tests

A `Jenkinsfile` defines the pipeline **as code**, stored in your repo next to the tests.

```groovy
pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-25'
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                    url: 'https://github.com/your-org/SeleniumAutomationRakesh.git'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn -B test'   // runs testng_regression.xml via Surefire
            }
        }
    }

    post {
        always {
            // Publish TestNG / Surefire results
            junit 'target/surefire-reports/*.xml'
            // Save screenshots taken on failure
            archiveArtifacts artifacts: 'screenshots/**', allowEmptyArchive: true
        }
    }
}
```

Key points for QA:

- `sh 'mvn -B test'` is the same command you run locally — Jenkins just runs it on a server.
- `-B` (batch mode) prevents interactive prompts.
- The `post { always { ... } }` block **always** publishes reports, even when tests fail (which is exactly when you need them).
- `junit` reads Surefire's XML so Jenkins shows pass/fail trends over time.

---

## Docker essentials for QA

Docker packages your tests **and everything they need** (Java, Maven, Chrome) into one portable image. "It works on my machine" stops being a problem, because the container is the same everywhere.

### Core Docker vocabulary

| Term | Meaning |
|---|---|
| Image | A read-only blueprint (Java + Maven + Chrome + your code) |
| Container | A running instance of an image |
| Dockerfile | The recipe that builds an image |
| Layer | Each instruction in a Dockerfile creates a cached layer |
| Registry | Where images are stored (Docker Hub) |
| Volume | Persistent storage mounted into a container |

### Everyday Docker commands

| Command | What it does |
|---|---|
| `docker build -t myimage .` | Build an image from the Dockerfile |
| `docker run --rm myimage` | Run a container, remove it after |
| `docker run --rm myimage mvn -B test -Dtest=LoginTests` | Override the default command |
| `docker ps` | List running containers |
| `docker ps -a` | List all containers |
| `docker images` | List images |
| `docker logs <id>` | View a container's output |
| `docker exec -it <id> bash` | Open a shell inside a running container |
| `docker stop <id>` | Stop a container |
| `docker rmi <image>` | Delete an image |
| `docker system prune` | Clean up unused data |

---

## This project's Docker setup explained

This project already has a working `Dockerfile`. Here is what each part does, in QA terms.

```dockerfile
FROM maven:3.9-eclipse-temurin-25
```

Start from an image that already has **Maven 3.9 and Java 25** installed. No manual JDK setup needed.

```dockerfile
RUN apt-get update && apt-get install -y ... google-chrome-stable ...
```

Install **Google Chrome** inside the image. Selenium 4.45's built-in **Selenium Manager** automatically downloads the matching chromedriver at runtime — so no driver binary is committed to the repo.

```dockerfile
COPY pom.xml ./
RUN mvn -B dependency:go-offline
```

Copy only `pom.xml` first and pre-download all dependencies. Because Docker caches layers, dependencies are only re-downloaded when `pom.xml` changes — builds stay fast.

```dockerfile
COPY . .
CMD ["mvn", "-B", "test"]
```

Copy the rest of the project and, by default, run the TestNG suite (`testng_regression.xml`) via Surefire.

### Build and run this project's tests in Docker

```bash
# Build the image
docker build -t selenium-automation-rakesh .

# Run the full suite
docker run --rm selenium-automation-rakesh

# Run a specific test class
docker run --rm selenium-automation-rakesh mvn -B test -Dtest=LoginTests
```

> Note: The `.dockerignore` in this project excludes `target/`, `.git/`, `doc/`, and IDE folders so the image stays small and clean.

---

## Running Selenium in CI (headless and Grid)

A build server has **no screen**. Chrome must run **headless** (no visible window). This is the single most common CI gotcha for QA.

### Headless Chrome options (required in Docker/Jenkins)

```java
ChromeOptions options = new ChromeOptions();
options.addArguments("--headless=new");    // run without a visible window
options.addArguments("--no-sandbox");      // required in most containers
options.addArguments("--disable-dev-shm-usage"); // avoid /dev/shm crashes
options.addArguments("--window-size=1920,1080");  // give elements room to render

WebDriver driver = new ChromeDriver(options);
```

| Argument | Why it matters in CI |
|---|---|
| `--headless=new` | No display exists on a build server |
| `--no-sandbox` | Chrome's sandbox often fails as root in a container |
| `--disable-dev-shm-usage` | Prevents crashes from small shared memory |
| `--window-size=...` | Ensures responsive elements render and are clickable |

### Selenium Grid (running tests on many browsers/machines)

Selenium Grid distributes tests across multiple machines or browsers in parallel.

```text
        ┌─────────────┐
        │  Grid Hub   │  (receives test requests)
        └──────┬──────┘
       ┌───────┼────────┐
   ┌───┴───┐ ┌─┴────┐ ┌─┴─────┐
   │ Chrome│ │Firefox│ │ Edge │   (nodes that actually run browsers)
   └───────┘ └───────┘ └───────┘
```

The easiest way to run Grid is with Docker:

```bash
docker run -d -p 4444:4444 selenium/standalone-chrome
```

Then point your test at the hub:

```java
WebDriver driver = new RemoteWebDriver(
    new URL("http://localhost:4444/wd/hub"), options);
```

Use Grid when you need to test many browsers or run large suites in parallel.

---

## Test reports and artifacts

When tests run on a server, **the reports are your eyes**. Know where they live.

| Artifact | Location | Produced by |
|---|---|---|
| Surefire results (XML/TXT) | `target/surefire-reports/` | Maven Surefire |
| TestNG report | `target/surefire-reports/` (e.g. `emailable-report.html`) | TestNG |
| Screenshots on failure | `screenshots/` (project convention) | Your test code |
| Compiled build output | `target/` | Maven |

### Taking a screenshot on failure (a QA must-have)

```java
File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
FileUtils.copyFile(src, new File("screenshots/failure.png")); // commons-io
```

> This project already includes `commons-io`, which provides `FileUtils.copyFile` for saving screenshots.

In CI, always publish reports and archive screenshots in the pipeline's `post`/`always` step so you can debug failures without re-running.

---

## Linux and shell cheatsheet for QA

Build servers and containers are almost always Linux. These commands cover 90% of what QA needs.

| Command | What it does |
|---|---|
| `pwd` | Print current directory |
| `ls -la` | List files (including hidden) |
| `cd <dir>` | Change directory |
| `cat <file>` | Print a file |
| `less <file>` | Scroll through a file |
| `tail -f app.log` | Follow a log file live |
| `grep "ERROR" app.log` | Search text in a file |
| `find . -name "*.xml"` | Find files by name |
| `chmod +x run.sh` | Make a script executable |
| `./run.sh` | Run a script |
| `export KEY=value` | Set an environment variable |
| `echo $PATH` | Print an environment variable |
| `ps -ef | grep chrome` | Find running Chrome processes |
| `kill -9 <pid>` | Force-stop a process |
| `df -h` | Check disk space |
| `curl http://localhost:4444/status` | Check a service (e.g. Grid) |

### Reading a failing test log fast

```bash
# Show only the failures in the Surefire output
grep -A 5 "FAILED" target/surefire-reports/*.txt

# Follow a live test run
tail -f target/surefire-reports/*.txt
```

---

## Environment variables and secrets

Never hard-code URLs, usernames, or passwords in test code. Read them from the environment or a config file.

### Reading environment variables in Java

```java
String browser = System.getenv("BROWSER");   // set by Jenkins/Docker
String baseUrl = System.getenv("BASE_URL");
```

### Passing them into Docker

```bash
docker run --rm -e BROWSER=chrome -e BASE_URL=https://staging.example.com \
    selenium-automation-rakesh
```

### Config file approach (this project uses `config/config.properties`)

```properties
browser=chrome
url=https://example.com
```

```java
Properties props = new Properties();
props.load(new FileInputStream("config/config.properties"));
String url = props.getProperty("url");
```

| Rule | Why |
|---|---|
| Never commit real passwords to Git | They live in history forever |
| Use Jenkins credentials / secrets | Injected at runtime, not stored in code |
| Use env vars or config files | Same test runs against many environments |

---

## Best practices

- Push test code to Git on a **branch**, not directly to `main`.
- Write commit messages that explain *why*, not just *what*.
- Make tests **headless-ready** so they run on any build server.
- Keep the pipeline command identical to local: `mvn clean test`.
- Always publish reports and archive screenshots, even on failure.
- Read config from files or environment variables, never hard-code secrets.
- Keep Docker images small: use `.dockerignore`, cache dependencies in layers.
- Make tests **independent** so they can run in parallel on CI.
- Keep the `main`/`master` branch always green (all tests passing).
- Treat a red pipeline as "stop and fix," not "ignore and continue."

---

## Common mistakes and troubleshooting

### Tests pass locally but fail in Jenkins/Docker

Likely causes:

- Chrome is not headless (no display on the server)
- Chrome not installed in the container
- Hard-coded local paths or `localhost` URLs
- Timing issues that only appear on slower servers

Fix:

- Add `--headless=new`, `--no-sandbox`, `--disable-dev-shm-usage`
- Ensure the image installs Chrome (this project's Dockerfile does)
- Use config/env vars for URLs
- Use explicit waits, not `Thread.sleep()`

### "session not created" / driver mismatch

Cause: Chrome and chromedriver versions do not match.

Fix: Use Selenium 4's built-in **Selenium Manager** (already used here) — it resolves the matching driver automatically. Do not commit a fixed chromedriver binary.

### Jenkins shows no test results

Cause: The pipeline did not publish the report.

Fix: Add `junit 'target/surefire-reports/*.xml'` in the `post`/`always` block.

### Docker build is slow every time

Cause: Dependencies re-download on every build.

Fix: Copy `pom.xml` and run `mvn dependency:go-offline` **before** copying the rest (layer caching) — exactly what this project's Dockerfile does.

### Container exits immediately with no useful output

Fix:

- Run `docker logs <container-id>` to see what happened
- Run interactively: `docker run --rm -it <image> bash`

### Secrets accidentally committed to Git

Fix:

- Remove them, rotate the credentials immediately (history keeps old values)
- Use Jenkins credentials or environment variables going forward
- Add sensitive files to `.gitignore`

---

## Practice exercises

### Exercise 1: Branch, commit, push

Goal:

- Create a `feature/` branch
- Add a small change and commit it
- Push it to the remote

Skills practiced:

- Everyday Git flow

### Exercise 2: Run the suite in Docker

Goal:

- Build the project image
- Run the full suite in a container
- Run a single test class by overriding the command

Skills practiced:

- Docker build and run

### Exercise 3: Make a test headless-ready

Goal:

- Add headless ChromeOptions to a test
- Confirm it runs without opening a visible browser

Skills practiced:

- CI-ready Selenium configuration

### Exercise 4: Read the reports

Goal:

- Run `mvn clean test`
- Find and open the Surefire report
- Use `grep` to list only the failures

Skills practiced:

- Locating and reading test reports
- Linux command line

### Exercise 5: Write a mini Jenkinsfile

Goal:

- Write a pipeline with Checkout, Build, and Test stages
- Add a `post` block that publishes `junit` results

Skills practiced:

- Pipeline as code

---

## Quick reference

| Task | Command |
|---|---|
| Clone repo | `git clone <url>` |
| New branch | `git checkout -b feature/x` |
| Commit + push | `git add . && git commit -m "..." && git push` |
| Run all tests | `mvn clean test` |
| Run one class | `mvn -B test -Dtest=LoginTests` |
| Build image | `docker build -t app .` |
| Run tests in Docker | `docker run --rm app` |
| Headless Chrome | `--headless=new --no-sandbox --disable-dev-shm-usage` |
| Start Grid | `docker run -d -p 4444:4444 selenium/standalone-chrome` |
| Follow a log | `tail -f target/surefire-reports/*.txt` |
| Publish results (Jenkins) | `junit 'target/surefire-reports/*.xml'` |

### The pipeline in one line

```text
git push → Jenkins → clone → mvn clean test (Docker, headless) → publish reports → deploy if green
```

---

## Student learning outcome

After completing this cheatsheet, students should be able to:

- Explain where automated tests fit in a CI/CD pipeline
- Use everyday Git commands and a branch-based workflow
- Describe what Jenkins does and read a simple `Jenkinsfile`
- Build and run this project's tests inside Docker
- Configure Selenium to run headless on a build server
- Locate and publish test reports and failure screenshots
- Use essential Linux commands on a build server
- Handle configuration and secrets without hard-coding them

DevOps knowledge turns a QA engineer from "someone who runs tests locally" into "someone whose tests protect every release automatically."
