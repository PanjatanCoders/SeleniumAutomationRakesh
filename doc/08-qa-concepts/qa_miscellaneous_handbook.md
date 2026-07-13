# Miscellaneous QA Handbook

This handbook is a student-friendly collection of the essential QA concepts that every automation engineer must know but that do not fit neatly into a Selenium, TestNG, or Maven guide. It covers the testing pyramid, SDLC and STLC, test case fundamentals, the bug life cycle, severity vs priority, API testing basics, and a glossary of terms.

Think of this as the "everything else a QA engineer should know" reference — the theory and vocabulary that make you a complete professional, not just a script writer. Useful for daily work and for interviews.

---

## Table of contents

- Why these concepts matter
- Verification vs validation
- Quality assurance vs quality control vs testing
- The software testing pyramid
- Types of testing (functional and non-functional)
- SDLC (software development life cycle)
- STLC (software testing life cycle)
- Agile and the QA role
- Test scenario vs test case
- How to write a good test case
- The bug (defect) life cycle
- Severity vs priority
- What makes a good bug report
- Manual vs automation testing
- What (not) to automate
- API testing basics
- Smoke, sanity, and regression testing
- Cross-browser and responsive testing
- The tools landscape for QA
- QA glossary
- Best practices
- Practice exercises
- Quick reference
- Student learning outcome

---

## Why these concepts matter

Automation skill without QA fundamentals is like knowing how to drive without knowing the traffic rules. You can make the car move, but you cannot be trusted on the road.

These concepts help you:

- Decide **what** to test, not just **how**
- Communicate with developers and managers using shared vocabulary
- Write clear bug reports that get fixed quickly
- Understand where automation fits in the bigger picture
- Answer the theory questions in every QA interview

You have learned the tools. This handbook gives you the professional context around them.

---

## Verification vs validation

Two words that sound similar and confuse everyone. Learn the difference once.

| | Verification | Validation |
|---|---|---|
| Question | "Are we building the product **right**?" | "Are we building the **right** product?" |
| Focus | Process, documents, code | Actual working software |
| Examples | Reviews, walkthroughs, inspections | Running the app, executing tests |
| When | Before/during development | After building |

> Simple memory hook: **Verification = documents and reviews. Validation = actually running the software.** Selenium tests are validation.

---

## Quality assurance vs quality control vs testing

| Term | What it is | Nature |
|---|---|---|
| Quality Assurance (QA) | Preventing defects by improving the **process** | Proactive |
| Quality Control (QC) | Finding defects in the **product** | Reactive |
| Testing | The activity of executing the software to find defects | A part of QC |

In everyday speech people say "QA" for everything, but in interviews this distinction matters: QA prevents, QC detects, testing is the act of detecting.

---

## The software testing pyramid

The testing pyramid shows the ideal **balance** of test types: many fast low-level tests, few slow high-level tests.

```text
            /\
           /  \        UI / E2E tests  (Selenium)
          /----\       - few
         /      \      - slow, expensive, closest to the user
        /--------\     Integration / API tests
       /          \    - some
      /            \   - faster, test components together
     /--------------\  Unit tests
    /                \ - many
   /------------------\- fastest, cheapest, closest to the code
```

| Layer | Speed | Cost | How many | Example |
|---|---|---|---|---|
| Unit | Fastest | Cheapest | Most | Test one method in isolation |
| Integration / API | Medium | Medium | Some | Test the login API responds correctly |
| UI / End-to-End | Slowest | Most expensive | Fewest | Selenium logs in through the browser |

### Why the shape matters for you

Selenium tests sit at the **top** — powerful but slow and brittle. A common mistake is automating *everything* through the UI (an "ice-cream cone" anti-pattern: wide at the top). Professionals push testing **down** the pyramid: check business logic with unit/API tests, and reserve Selenium for true end-to-end user journeys.

---

## Types of testing (functional and non-functional)

### Functional testing — does it do what it should?

| Type | Checks |
|---|---|
| Unit testing | Individual methods/functions |
| Integration testing | Modules working together |
| System testing | The whole application end to end |
| Smoke testing | The build is stable enough to test |
| Sanity testing | A specific fix works |
| Regression testing | Old features still work after changes |
| User acceptance testing (UAT) | The business is happy |

### Non-functional testing — how well does it do it?

| Type | Checks |
|---|---|
| Performance testing | Speed and responsiveness |
| Load testing | Behavior under expected load |
| Stress testing | Behavior beyond normal limits |
| Security testing | Vulnerabilities and access control |
| Usability testing | Ease of use |
| Compatibility testing | Works across browsers/devices/OS |
| Accessibility testing | Usable by people with disabilities |

Selenium is used mostly for **functional** testing (system, regression, smoke) through the UI.

---

## SDLC (software development life cycle)

SDLC is the overall process of building software.

```text
Requirements → Design → Development → Testing → Deployment → Maintenance
```

| Phase | What happens | QA involvement |
|---|---|---|
| Requirements | Decide what to build | Review requirements, ask questions |
| Design | Plan architecture and UI | Start test planning |
| Development | Write the code | Write test cases, prepare automation |
| Testing | Verify the software | Execute tests, log bugs |
| Deployment | Release to users | Smoke test the release |
| Maintenance | Fix and improve | Regression testing |

> Key idea: QA is **not** just a phase at the end. Good QA starts at requirements — the earlier a defect is found, the cheaper it is to fix.

---

## STLC (software testing life cycle)

STLC is the testing-specific process that runs *inside* SDLC.

```text
Requirement Analysis → Test Planning → Test Case Design →
Environment Setup → Test Execution → Test Closure
```

| Phase | Output |
|---|---|
| Requirement analysis | List of what is testable |
| Test planning | Test strategy, scope, effort, tools |
| Test case design | Written test cases and test data |
| Environment setup | Test environment ready |
| Test execution | Run tests, log defects |
| Test closure | Reports, metrics, lessons learned |

---

## Agile and the QA role

Most teams today work in **Agile** — building software in short cycles called **sprints** (usually 1–4 weeks) instead of one giant release.

| Agile idea | What it means for QA |
|---|---|
| Sprint | A short delivery cycle; test within it |
| User story | A small feature described from the user's view |
| Definition of Done | Includes "tested" — QA is part of "done" |
| Daily standup | Short daily sync; report test progress/blockers |
| Continuous testing | Tests run continuously in CI, not just at the end |
| Shift-left | Test earlier, closer to development |

In Agile, QA is embedded in the team from day one. Automation is what makes testing keep pace with rapid sprints.

---

## Test scenario vs test case

These are often confused. A **scenario** is the high-level idea; a **test case** is the detailed steps.

| | Test scenario | Test case |
|---|---|---|
| Level | High-level | Detailed |
| Answers | "What to test" | "How to test, step by step" |
| Example | "Verify user login" | The exact steps, data, and expected result below |

### Example scenario → test cases

**Scenario:** Verify user login.

Broken into test cases:

- TC1: Login with valid username and valid password → dashboard shown
- TC2: Login with valid username and wrong password → error message
- TC3: Login with empty fields → validation messages
- TC4: Login with a locked account → account-locked message

---

## How to write a good test case

A test case is a documented set of steps to verify one thing. A standard template:

| Field | Example |
|---|---|
| Test Case ID | TC_LOGIN_01 |
| Title | Valid login |
| Preconditions | User account exists |
| Test data | user: student1 / pass: password123 |
| Steps | 1. Open login page 2. Enter username 3. Enter password 4. Click Login |
| Expected result | Dashboard page is displayed |
| Actual result | (filled during execution) |
| Status | Pass / Fail |
| Priority | High |

### Qualities of a good test case

- **Clear** — anyone can follow the steps
- **Specific** — one test case verifies one thing
- **Repeatable** — same steps give the same result
- **Independent** — does not depend on another test case
- **Traceable** — links back to a requirement

> These qualities map directly onto good automated tests: one purpose, independent, repeatable, with a clear assertion.

---

## The bug (defect) life cycle

A defect (bug) moves through defined states from discovery to closure.

```text
New → Assigned → Open → Fixed → Retest → Verified → Closed
                          │                    │
                          │                    └── (still broken) → Reopened
                          └── (not a real bug) → Rejected / Deferred / Duplicate
```

| Status | Meaning |
|---|---|
| New | Bug just reported |
| Assigned | Given to a developer |
| Open | Developer is working on it |
| Fixed | Developer says it is resolved |
| Retest | QA re-tests the fix |
| Verified | QA confirms it is fixed |
| Closed | Done, bug is resolved |
| Reopened | Retest failed; bug is still there |
| Rejected | Not a valid bug |
| Duplicate | Already reported |
| Deferred | Valid, but fixed later |

The QA engineer owns the **report → retest → verify/reopen → close** part of this cycle.

---

## Severity vs priority

The most common QA interview question. Two independent measures of a bug.

| | Severity | Priority |
|---|---|---|
| Measures | Technical impact on the system | Business urgency to fix |
| Decided by | QA / testers | Product owner / manager |
| Question | "How badly does it break things?" | "How soon must it be fixed?" |

### The four classic combinations

| Combination | Example |
|---|---|
| High severity, High priority | App crashes on login — nobody can use it |
| High severity, Low priority | App crashes on a rarely used admin report |
| Low severity, High priority | Company logo/name spelled wrong on the homepage |
| Low severity, Low priority | Minor text misalignment on a hidden page |

> Memory hook: **Severity = how much it hurts. Priority = how fast we must fix it.** A tiny typo in the company name is low severity but high priority.

---

## What makes a good bug report

A developer can only fix what they can reproduce. A strong bug report contains:

| Field | Why it matters |
|---|---|
| Clear title | Summarizes the problem instantly |
| Steps to reproduce | Exact steps to trigger the bug |
| Expected result | What should happen |
| Actual result | What actually happened |
| Environment | Browser, OS, version, URL |
| Severity & priority | Helps triage |
| Screenshot / video / logs | Proof and context |
| Test data used | Exact inputs |

### Example

```text
Title: Login button does nothing with valid credentials on Chrome

Steps:
1. Go to https://example.com/login
2. Enter username "student1" and password "password123"
3. Click "Login"

Expected: User is taken to the Dashboard
Actual:   Nothing happens; no error, no navigation

Environment: Chrome 141, Windows 11, build 1.0-SNAPSHOT
Severity: High   Priority: High
Attachment: login_failure.png
```

> This is exactly why automation captures a screenshot on failure — it becomes evidence in the bug report.

---

## Manual vs automation testing

| | Manual testing | Automation testing |
|---|---|---|
| Who runs it | A human | A script (Selenium) |
| Speed | Slow | Fast |
| Best for | Exploratory, usability, one-off | Repetitive, regression, data-driven |
| Reliability | Human error possible | Consistent |
| Upfront cost | Low | Higher (writing scripts) |
| Long-term cost | High (repeated effort) | Low (reuse) |

Automation does **not** replace manual testing. Exploratory testing, usability, and brand-new features still need human eyes. Automation frees humans from repetitive checks so they can do the thinking.

---

## What (not) to automate

### Good candidates for automation

- Regression tests (run repeatedly)
- Smoke tests (run on every build)
- Data-driven tests (same steps, many inputs)
- Tedious, repetitive workflows
- Stable features that rarely change

### Poor candidates for automation

- Brand-new features still changing daily
- Tests run only once
- Usability and "does it look nice" checks
- Captcha, OTP, and other deliberately human-only flows
- Areas with constant UI churn (locators break constantly)

> Rule of thumb: automate what is **stable, repetitive, and high-value**. Do not automate for the sake of automating.

---

## API testing basics

Behind every web UI are **APIs** — the messages the browser sends to the server. Testing APIs directly is faster and more stable than UI testing, and it sits in the middle of the testing pyramid.

### Core concepts

| Term | Meaning |
|---|---|
| API | Interface for programs to talk to each other |
| Endpoint | A specific URL the API exposes |
| Request | What you send to the API |
| Response | What the API sends back |
| Status code | A number describing the result (200, 404, 500) |
| Payload / Body | The data sent or received (often JSON) |
| Header | Metadata (auth token, content type) |

### HTTP methods

| Method | Purpose |
|---|---|
| GET | Read data |
| POST | Create data |
| PUT | Replace data |
| PATCH | Update part of data |
| DELETE | Remove data |

### Common status codes

| Code | Meaning |
|---|---|
| 200 | OK (success) |
| 201 | Created |
| 400 | Bad request (client error) |
| 401 | Unauthorized (not logged in) |
| 403 | Forbidden (no permission) |
| 404 | Not found |
| 500 | Server error |

### Example: a JSON response

```json
{
  "id": 101,
  "username": "student1",
  "role": "learner",
  "active": true
}
```

QA engineers test APIs with tools like **Postman** (manual/exploratory) or libraries like **REST Assured** (Java automation, pairs naturally with TestNG). Learning API testing is the natural next step after UI automation.

---

## Smoke, sanity, and regression testing

Three terms constantly mixed up. Learn them clearly.

| Type | Question it answers | When | Depth |
|---|---|---|---|
| Smoke | "Is the build stable enough to test at all?" | After every new build | Shallow, broad |
| Sanity | "Does this specific fix/feature work?" | After a small change | Narrow, focused |
| Regression | "Did this change break anything old?" | After changes, before release | Wide, deep |

### Simple analogy

- **Smoke:** Turn on the car — does the engine start at all?
- **Sanity:** You fixed the radio — does the radio now work?
- **Regression:** After fixing the radio, do the brakes, lights, and horn still work?

In automation, `smoke` and `regression` are common TestNG **groups** (you learned groups in the TestNG handbook).

---

## Cross-browser and responsive testing

Applications must work everywhere users are.

| Testing type | Verifies |
|---|---|
| Cross-browser | Works on Chrome, Firefox, Edge, Safari |
| Cross-platform | Works on Windows, macOS, Linux |
| Responsive | Works on desktop, tablet, and mobile screen sizes |

Selenium supports cross-browser testing by swapping the driver (`ChromeDriver`, `FirefoxDriver`, `EdgeDriver`). **Selenium Grid** and cloud services (BrowserStack, Sauce Labs) run tests across many browser/OS combinations at once.

```java
// Responsive check by resizing the window
driver.manage().window().setSize(new Dimension(375, 812)); // iPhone-ish size
```

---

## The tools landscape for QA

A map of common tools so the vocabulary is familiar. You already use several.

| Category | Tools | This project uses |
|---|---|---|
| Language | Java, Python, JavaScript | Java |
| UI automation | Selenium, Playwright, Cypress | Selenium |
| Test runner | TestNG, JUnit | TestNG |
| Build tool | Maven, Gradle | Maven |
| API testing | Postman, REST Assured | — (next step) |
| BDD | Cucumber, SpecFlow | — (optional) |
| Reporting | Extent, Allure, Surefire | Surefire |
| Version control | Git, GitHub, GitLab | Git + GitHub |
| CI/CD | Jenkins, GitHub Actions, GitLab CI | Jenkins |
| Containers | Docker | Docker |
| Test management | Jira, TestRail, Zephyr | — |
| Cross-browser cloud | Selenium Grid, BrowserStack | Grid (optional) |
| Performance | JMeter, Gatling | — |

---

## QA glossary

| Term | Meaning |
|---|---|
| Test case | Documented steps to verify one thing |
| Test scenario | High-level "what to test" |
| Test suite | A collection of test cases |
| Test plan | Document describing scope, approach, resources |
| Defect / Bug | A flaw where actual ≠ expected behavior |
| Severity | Technical impact of a bug |
| Priority | Business urgency to fix a bug |
| Regression | Re-testing that old features still work |
| Smoke test | Quick check that a build is stable |
| Sanity test | Focused check that a fix works |
| Flaky test | A test that passes and fails inconsistently |
| Assertion | A check that a result matches expectation |
| Test coverage | How much of the app is exercised by tests |
| Root cause | The underlying reason a defect occurred |
| Reproduce | To make a bug happen again on demand |
| Environment | Where tests run (dev, test, staging, prod) |
| Baseline | An agreed reference point for comparison |
| Shift-left | Testing earlier in the process |
| Exploratory testing | Unscripted, investigative manual testing |
| UAT | User acceptance testing by the business |
| SLA | Service level agreement (e.g. bug fix time) |

---

## Best practices

- Learn the **why** behind a test, not just the steps.
- Test early — the earlier a bug is found, the cheaper it is to fix.
- Write bug reports a developer can reproduce without asking you.
- Keep test cases independent and focused on one thing.
- Know severity and priority are **different** — never confuse them.
- Push tests **down** the pyramid; do not automate everything through the UI.
- Automate the stable and repetitive; keep humans on exploration and usability.
- Use shared vocabulary so developers and managers understand you.
- Capture evidence (screenshots, logs) for every failure.
- Keep learning: API testing is the natural next step after UI automation.

---

## Practice exercises

### Exercise 1: Severity vs priority

Goal:

- For five sample bugs, assign severity and priority
- Justify each in one sentence

Skills practiced:

- Distinguishing technical impact from business urgency

### Exercise 2: Scenario to test cases

Goal:

- Take the scenario "Verify search functionality"
- Break it into at least four detailed test cases

Skills practiced:

- Test case design

### Exercise 3: Write a bug report

Goal:

- Find any real bug (or use a broken sample)
- Write a full bug report using the template

Skills practiced:

- Clear defect reporting

### Exercise 4: Place tests on the pyramid

Goal:

- Given a list of checks, decide whether each is best as a unit, API, or UI test
- Explain why

Skills practiced:

- Test strategy thinking

### Exercise 5: What to automate

Goal:

- Given ten features, mark each as "automate" or "keep manual"
- Justify the decisions

Skills practiced:

- Automation decision making

---

## Quick reference

| Concept | One-liner |
|---|---|
| Verification | Building it right (reviews) |
| Validation | Building the right thing (running it) |
| QA vs QC | QA prevents, QC detects |
| Testing pyramid | Many unit, some API, few UI tests |
| SDLC | Requirements → Design → Dev → Test → Deploy → Maintain |
| STLC | Analyze → Plan → Design → Setup → Execute → Close |
| Scenario vs case | What to test vs how to test |
| Bug life cycle | New → Assigned → Fixed → Retest → Verified → Closed |
| Severity | How badly it breaks |
| Priority | How soon to fix |
| Smoke | Is the build testable? |
| Sanity | Does the fix work? |
| Regression | Did we break anything old? |
| Automate | Stable, repetitive, high-value tests |

---

## Student learning outcome

After completing this handbook, students should be able to:

- Explain verification vs validation and QA vs QC vs testing
- Describe the testing pyramid and why UI tests should be few
- Walk through SDLC and STLC and where QA fits
- Distinguish a test scenario from a test case and write both well
- Explain the bug life cycle and write a reproducible bug report
- Clearly separate severity from priority with examples
- Understand API testing basics and HTTP status codes
- Tell smoke, sanity, and regression testing apart
- Use professional QA vocabulary confidently

These fundamentals are what turn a script writer into a well-rounded QA engineer — and they are the questions every interview will ask.
