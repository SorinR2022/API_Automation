# API Automation Framework - Bookstore

A comprehensive API automation test framework for the FakeRestAPI bookstore demo, built with **Java**, **RestAssured**, **TestNG**, and **Allure Reports**.

## Project Overview

This framework provides automated testing for the Bookstore API endpoints, including:

- **Books API**: GET (all + by ID), POST, PUT, DELETE with edge case testing
- **Authors API** (bonus): GET (all + by ID), POST, PUT, DELETE with edge case testing  
- **Happy paths & edge cases**: Comprehensive test coverage for normal and error scenarios
- **Professional reporting**: Allure reports with detailed test execution summaries
- **Containerization**: Docker support for isolated test execution
- **CI/CD ready**: GitHub Actions pipeline for continuous testing

## Quick Start (5 minutes)

### Prerequisites

- **JDK 11+** (verify: `java -version`)
- **Maven 3.8+** (verify: `mvn -version`)
- **Docker** (optional, for containerized runs)

### Local Test Execution

```bash
# Run all tests
mvn clean test -DBASE_URL=https://fakerestapi.azurewebsites.net

# Run with Allure report
mvn clean test allure:report -DBASE_URL=https://fakerestapi.azurewebsites.net

# View Allure report
mvn allure:serve
```

### Docker Execution

```bash
# Build + run tests in container
docker build -t bookstore-tests .
docker run -e BASE_URL=https://fakerestapi.azurewebsites.net bookstore-tests
```

## Project Structure

```
src/test/java/com/bookstore/
├── models/          # POJOs (Book, Author)
├── clients/         # REST clients (BooksClient, AuthorsClient)
└── tests/           # Test classes (BookTests, AuthorTests)

target/
├── surefire-reports/  # HTML/XML test reports
├── allure-results/    # Allure JSON results  
└── allure-report/     # Allure HTML dashboard
```

## Test Coverage

### Books API (3 tests)
- Get all books
- Get book by ID
- Create/Update/Delete book with edge cases

### Authors API (6 tests - Bonus)
- Get all authors
- Get author by ID
- Create/Update/Delete author
- Invalid data handling
- Boundary testing

**Total: 9 tests | Pass rate: 100%**

## Running Tests

### Maven (Local)
```bash
# Full build + tests
mvn clean test -DBASE_URL=https://fakerestapi.azurewebsites.net

# Run specific test class
mvn test -Dtest=BookTests -DBASE_URL=https://fakerestapi.azurewebsites.net

# Run with Allure report
mvn clean test allure:report -DBASE_URL=https://fakerestapi.azurewebsites.net
```

### Docker (Containerized)
```bash
docker build -t bookstore-tests .
docker run -e BASE_URL=https://fakerestapi.azurewebsites.net bookstore-tests
```

## Reports

### Surefire Reports (default)
```
target/surefire-reports/index.html
```

### Allure Reports (professional)
```bash
mvn allure:report      # Generate static HTML
mvn allure:serve       # Serve with local web server
```

Features: Timeline, pass/fail analytics, history trends, detailed failures

## CI/CD Pipeline

GitHub Actions (`.github/workflows/ci.yml`):
- Triggered on: push to main/master, pull requests
- Builds & runs tests with JDK 11
- Generates Allure report
- Builds Docker image
- Uploads artifacts to GitHub

View results: GitHub Actions tab → CI workflow

## Configuration

### Base URL
Set via environment variable (highest priority):
```bash
export BASE_URL=http://custom-api.com
mvn test
```

Or via Maven property:
```bash
mvn test -DBASE_URL=http://custom-api.com
```

Default (if not set): `https://fakerestapi.azurewebsites.net`

## Code Quality

Clean code - clear naming conventions  
SOLID principles - single responsibility  
Edge case coverage - non-existent IDs, invalid data  
Descriptive assertions - helpful failure messages  
DRY approach - shared client wrappers  

## Troubleshooting

| Issue | Solution |
|-------|----------|
| `java: command not found` | Install JDK 11+, set JAVA_HOME |
| `mvn: command not found` | Install Maven 3.8+, add to PATH |
| Tests timeout | Increase timeout in pom.xml surefire config |
| Docker build fails | Ensure Docker daemon is running |
| Allure report empty | Run: `mvn clean allure:report` |

## Quick Links

- [RestAssured Documentation](https://rest-assured.io/)
- [TestNG Documentation](https://testng.org/)
- [Allure Documentation](https://docs.qameta.io/allure/)
- [FakeRestAPI](https://fakerestapi.azurewebsites.net/)

---

**Get started:**
```bash
mvn clean test -DBASE_URL=https://fakerestapi.azurewebsites.net
```
