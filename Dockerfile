# Selenium + TestNG automation suite (Java 25 / Maven)
#
# Build:  docker build -t selenium-automation-rakesh .
# Run:    docker run --rm selenium-automation-rakesh
#         docker run --rm selenium-automation-rakesh mvn -B test -Dtest=LoginTests

FROM maven:3.9-eclipse-temurin-25

# ---------------------------------------------------------------------------
# Install Google Chrome. Selenium 4.45's built-in Selenium Manager resolves the
# matching chromedriver automatically at runtime, so no driver binary is needed.
# ---------------------------------------------------------------------------
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        gnupg \
        wget \
        ca-certificates \
        fonts-liberation \
    && wget -q -O /tmp/chrome.deb \
        https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb \
    && apt-get install -y --no-install-recommends /tmp/chrome.deb \
    && rm -f /tmp/chrome.deb \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# ---------------------------------------------------------------------------
# Resolve dependencies first so they are cached in their own layer and only
# re-downloaded when pom.xml changes.
# ---------------------------------------------------------------------------
COPY pom.xml ./
RUN mvn -B dependency:go-offline

# Copy the rest of the project.
COPY . .

# Run the TestNG suite defined in pom.xml (surefire -> testng_regression.xml).
# Browser tests must run headless in a container; e.g. drive Chrome with
# ChromeOptions().addArguments("--headless=new", "--no-sandbox").
CMD ["mvn", "-B", "test"]