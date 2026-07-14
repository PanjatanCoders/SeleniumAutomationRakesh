# Selenium Automation Course — Study Docs

This folder holds the study handbooks for the Java + Selenium + TestNG automation course. Docs are organized in **learning order** — follow the folders top to bottom.

Each `*_handbook.md` follows the same structure: why it matters → concepts with examples → best practices → common mistakes → practice exercises → quick reference → learning outcome. Files named without an extension (like `tutorial`) are raw whiteboard notes from live sessions.

---

## Learning path

### 01 — Java
Foundations of the language used throughout the course.

- [Java_Basics_Tutorial.pdf](01-java/Java_Basics_Tutorial.pdf) — Java fundamentals
- [Exception_Handling_Story.pdf](01-java/Exception_Handling_Story.pdf) — Exception handling explained

### 02 — Selenium Locators
Finding elements on a page.

- [css_locator.md](02-selenium-locators/css_locator.md) — CSS selector handbook
- [xpath_locator_handbook.md](02-selenium-locators/xpath_locator_handbook.md) — XPath handbook
- [css_locator](02-selenium-locators/css_locator) — raw session notes

### 03 — Selenium Interactions
Driving the browser and handling dynamic behavior.

- [keyboard_mouse_actions_handbook.md](03-selenium-interactions/keyboard_mouse_actions_handbook.md) — Actions class (keyboard & mouse)
- [javascript_executor_handbook.md](03-selenium-interactions/javascript_executor_handbook.md) — JavaScriptExecutor
- [file_upload_download_guide.md](03-selenium-interactions/file_upload_download_guide.md) — File upload & download
- [alerts_frames_windows_handbook.md](03-selenium-interactions/alerts_frames_windows_handbook.md) — Alerts, frames, windows
- [waits_handbook.md](03-selenium-interactions/waits_handbook.md) — Implicit, explicit & fluent waits

### 04 — TestNG
Organizing tests into a real test framework.

- [testng_handbook.md](04-testng/testng_handbook.md) — TestNG handbook
- [tutorial](04-testng/tutorial) — raw session notes (suite/test/class structure)

### 05 — Maven & Build
How the project is built and tests are run.

- [maven_lifecycle_handbook.md](05-maven-build/maven_lifecycle_handbook.md) — Maven and its lifecycles
- [Maven_Tutorial](05-maven-build/Maven_Tutorial) — raw session notes

### 06 — Frameworks
Designing a maintainable automation framework.

- [framework_approaches_handbook.md](06-frameworks/framework_approaches_handbook.md) — Framework approaches & how to choose
- [page_object_model_handbook.md](06-frameworks/page_object_model_handbook.md) — Page Object Model in depth (robustness, components, parallel-safe driver)

### 07 — DevOps
Running tests automatically in a delivery pipeline.

- [devops_for_qa_cheatsheet.md](07-devops/devops_for_qa_cheatsheet.md) — DevOps cheatsheet for QA (Git, Jenkins, Docker)

### 08 — QA Concepts
The professional fundamentals every QA engineer needs.

- [qa_miscellaneous_handbook.md](08-qa-concepts/qa_miscellaneous_handbook.md) — Miscellaneous QA handbook

---

## Course stack

| Tool | Version |
|---|---|
| Java | 25 |
| Maven | 3.9 |
| Selenium | 4.45.0 |
| TestNG | 7.12.0 |
| Build/CI | Docker + Jenkins |

> Suite file: `testng_regression.xml` (run via the Surefire plugin in `pom.xml`).
