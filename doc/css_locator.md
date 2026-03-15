# CSS Selectors — Quick Reference & Study Guide

This file converts the short `css_locator` notes into a useful study guide for CSS selectors, with extra topics, examples, and Selenium (Java) usage tips.

---

## Table of contents

- Overview
- Basic selectors
- Attribute selectors
- Combinators (relationships)
- Structural & positional selectors
- Pseudo-classes and pseudo-elements
- Grouping and chaining
- Specificity and how to calculate it
- Escaping special characters in selectors
- CSS selectors in Selenium (Java) — examples
- Best practices for test automation
- Troubleshooting tips
- Quick reference cheat sheet
- Further reading

---

## Overview

CSS selectors are patterns used to select HTML elements. They're widely used in styling, but also extremely useful for locating elements in test automation (Selenium). They are typically faster and more readable than XPath for many tasks, but XPath has some capabilities CSS doesn't (e.g., selecting parents or certain text-based matches).

This guide summarizes the most useful selectors and gives practical automation tips.

---

## Basic selectors

- Select by id:
  - CSS: `#myId`
  - Example HTML: `<div id="login">` -> `#login`

- Select by class:
  - CSS: `.myClass`
  - Example: `<button class="primary">` -> `.primary`

- Select by tag name (element type):
  - CSS: `input`, `div`, `a`, `button`

- Tag + id:
  - `input#email` selects an `input` with id `email`.

- Tag + class:
  - `button.primary` selects `button` elements with class `primary`.

---

## Attribute selectors

- Exact match:
  - `tag[attr='value']` — e.g. `input[name='email']`

- Attribute presence (has attribute):
  - `tag[title]` — elements with a `title` attribute

- Prefix (starts with):
  - `tag[attr^='start']` — e.g. `div[id^='loginAlert']` matches id starting with `loginAlert`

- Suffix (ends with):
  - `tag[attr$='end']` — e.g. `div[id$='Alert']` matches id ending with `Alert`

- Substring (contains):
  - `tag[attr*='part']` — e.g. `div[id*='loginAlert']`

- Multiple attributes:
  - `textarea[name='bio'][placeholder='Tell us about yourself']` — matches elements with both attributes

- Case-sensitivity: attribute value matching is generally case-sensitive in HTML, but behavior may depend on document language and browser — prefer exact match for reliability.

Notes: quote attribute values with single or double quotes. When using selectors in languages like Java, remember to escape quotes inside string literals.

---

## Combinators (relationships between elements)

- Descendant (space):
  - `#loginForm button` selects any `button` inside element with id `loginForm` (any depth)

- Child (`>`):
  - `#loginForm > button` selects `button` elements that are direct children of `#loginForm`

- Adjacent sibling (`+`):
  - `label + input` selects the `input` immediately following a `label`

- General sibling (`~`):
  - `h2 ~ p` selects every `p` that is a sibling after an `h2`

Use these to be more specific about element relationships.

---

## Structural & positional selectors

- `:nth-child(n)` — 1-based index among all children (of parent). Accepts a number or formula `an+b` (e.g., `2n` for even):
  - `li:nth-child(3)` selects 3rd child of its parent
  - `li:nth-child(odd)` or `li:nth-child(2n+1)` — odd children
  - `li:nth-child(2n)` — even children

- `:nth-of-type(n)` — index among siblings of same element type

- `:first-child`, `:last-child`, `:only-child`

- `:first-of-type`, `:last-of-type`, `:only-of-type`

- `:empty` — element has no children (including text)

Note: `:nth-child()` counts element nodes including those with different tags; `:nth-of-type()` counts elements of the same tag.

---

## Pseudo-classes and pseudo-elements

Common pseudo-classes:
- `:hover`, `:active`, `:focus` — states (useful for CSS, limited use in Selenium)
- `:disabled`, `:checked`, `:required`, `:optional` — form state
- `:not(selector)` — negation, e.g., `input:not([type='submit'])`
- `:enabled` — opposite of `:disabled`

Common pseudo-elements:
- `::before`, `::after`, `::first-letter`, `::first-line` — used in CSS content, not selectable by Selenium's CSS selectors (Selenium selects elements, not pseudo elements)

Notes:
- `:contains(text)` is NOT part of standard CSS (it's from jQuery). For text-based selection use XPath in Selenium.
- `:has()` is a relational selector from newer CSS specifications (CSS4) and is not widely supported across all browsers; Selenium doesn't reliably support it for locating elements today.

---

## Grouping and chaining selectors

- Grouping (comma): Select multiple patterns with a single rule
  - `input[type='text'], textarea` matches both `input[type='text']` and `textarea`

- Chaining multiple conditions:
  - `input.form-control[name='email'].required` — element must match all chains

---

## Specificity (how browsers pick which rule applies)

Specificity determines which selector wins when multiple rules match. For automation, specificity helps you design unique selectors.

A common numeric model (higher wins):
- Inline styles: 1000 (style="...")
- IDs: 100
- Classes, attributes, pseudo-classes: 10
- Element names and pseudo-elements: 1

Examples:
- `#menu` -> specificity = 100
- `.nav .item` -> 10 + 10 = 20
- `ul li a` -> 1 + 1 + 1 = 3

When building locators, prefer IDs if they are stable (they are most specific). If IDs are dynamic, prefer meaningful class/attribute-based selectors.

---

## Escaping special characters in selectors

IDs or class names can contain characters that are not valid in a plain selector (spaces, periods, colons, etc.).

- You can escape special characters with a backslash in CSS:
  - If ID is `foo:bar`, use `#foo\:bar` in pure CSS.

- In Java string literals, you must escape the backslash as well:
  - `By.cssSelector("#foo\\:bar")`

Alternatively, select by attribute: `[id='foo:bar']` which avoids escaping.

---

## CSS selectors in Selenium (Java) — examples

Selenium WebDriver supports CSS selectors via `By.cssSelector(...)`.

Examples:

- By id:
  - `driver.findElement(By.cssSelector("#login"));`

- By class:
  - `driver.findElements(By.cssSelector(".nav-item"));`

- By attribute:
  - `driver.findElement(By.cssSelector("input[name='email']"));`

- Tag + id/class:
  - `driver.findElement(By.cssSelector("input#email"));`
  - `driver.findElement(By.cssSelector("button.submit.primary"));`

- Starts-with / ends-with / contains:
  - `driver.findElement(By.cssSelector("div[id^='loginAlert']")); // starts-with`
  - `driver.findElement(By.cssSelector("div[id$='Alert']")); // ends-with`
  - `driver.findElement(By.cssSelector("div[id*='loginAlert']")); // contains`

- Child and descendant:
  - `driver.findElement(By.cssSelector("#loginForm > button")); // direct child`
  - `driver.findElement(By.cssSelector("#loginForm button")); // any depth`

- Multiple attribute matching:
  - `driver.findElement(By.cssSelector("textarea[name='bio'][placeholder='Tell us about yourself']"));`

- Negation (not):
  - `driver.findElement(By.cssSelector("input:not([type='submit'])"));`

- Escaped ID in Java:
  - `driver.findElement(By.cssSelector("#foo\\:bar"));`

Remember: in Java strings use `\` to represent a single backslash.

---

## Best practices for test automation selectors

1. Prefer stable attributes (id, data-* attributes, unique class names). Example: `data-test`, `data-testid` are meant for test automation.
2. Avoid brittle selectors that depend on visual layout or deep DOM paths (e.g., `div > div > div > ul > li[6]`).
3. Prefer attributes over positional selectors where possible (e.g., `input[name='username']` instead of `form > div:nth-child(2) > input`).
4. Keep selectors readable and maintainable — add comments or helper methods in tests for complex selectors.
5. Use `By.cssSelector` for speed and readability, but use XPath when you need text matching or parent traversal.
6. Use `:not()` to exclude unwanted matches rather than complex logic in tests.

---

## Troubleshooting tips

- When selector fails, open browser devtools and test it in the Console using `document.querySelectorAll("your selector")`.
- If `querySelectorAll` returns elements but Selenium doesn't, ensure you aren't using features unsupported by the browser or Selenium's CSS engine.
- If ids/classes contain spaces or unusual characters use attribute selector: `[id='value with spaces']`.
- If locator intermittently fails, check for dynamic elements (ajax, delayed rendering) — add waits (explicit waits) in Selenium.

---

## Quick reference (cheat sheet)

- `#id` — by id
- `.class` — by class
- `tag` — by tag name
- `tag#id` — tag with id
- `tag.class` — tag with class
- `tag[attr='value']` — attribute equals
- `tag[attr^='start']` — attribute starts with
- `tag[attr$='end']` — attribute ends with
- `tag[attr*='substring']` — attribute contains
- `parent > child` — direct child
- `ancestor descendant` — descendant
- `A + B` — adjacent sibling
- `A ~ B` — general sibling
- `:nth-child(n)`, `:nth-of-type(n)` — positional
- `:not(selector)` — negate

---

## Examples (practical)

1. Locate the search input by name:
   - `By.cssSelector("input[name='q']")`

2. Locate a submit button inside the login form (direct child):
   - `By.cssSelector("#loginForm > button[type='submit']")`

3. Locate a menu item that contains a dynamic id prefix:
   - `By.cssSelector("li[id^='menu_']")`

4. Locate the 2nd visible row in a table body of `table#customers`:
   - `By.cssSelector("table#customers tbody tr:nth-child(2)")`

5. Exclude checkboxes used for bulk selection:
   - `By.cssSelector("input[type='checkbox']:not(.bulk)")`

---

## Further reading

- MDN CSS Selectors: https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Selectors
- Selenium docs for locators: https://www.selenium.dev/documentation/webdriver/elements/locators/

---

If you'd like, I can also:
- Add a small Java test class in `src/test/java/...` demonstrating these selectors in live WebDriver code.
- Generate a printable cheat-sheet PDF from this markdown.


