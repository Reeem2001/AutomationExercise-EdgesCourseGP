# AutomationExercise - Edges Course GP

A comprehensive Selenium WebDriver automation testing project for the **AutomationExercise** website, built as part of an educational course project. This project demonstrates best practices in test automation, including test organization, data management, reporting, and CI/CD integration.

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
- [Running Tests](#running-tests)
- [Test Modules](#test-modules)
- [Reporting](#reporting)
- [Directory Details](#directory-details)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)

## 🎯 Project Overview

This project automates end-to-end testing of the **AutomationExercise** website, covering critical user journeys and functionalities including:

- User registration and login
- Product browsing and filtering
- Shopping cart management
- Checkout process
- Contact form submission
- User account management

The project uses a **Page Object Model (POM)** design pattern for maintainability and scalability, with **TestNG** for test management and **Allure** for comprehensive test reporting.

## 📦 Prerequisites

Before getting started, ensure you have the following installed:

- **Java Development Kit (JDK)**: Java 24 or higher
  - Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/)
  - Verify installation: `java -version`

- **Maven**: 3.8.0 or higher
  - Download from [Apache Maven](https://maven.apache.org/download.cgi)
  - Verify installation: `mvn -version`

- **Git**: For cloning the repository
  - Download from [Git](https://git-scm.com/downloads)

- **Browser Drivers** (automatic with WebDriver Manager):
  - Chrome/Chromium (default)
  - Firefox, Edge, Safari (configurable)

## 📂 Project Structure

```
AutomationExercise-EdgesCourseGP/
├── AutomationExerciseWebsiteProject/
│   ├── src/
│   │   ├── main/
│   │   │   └── java/                          # Main application code (if any)
│   │   └── test/
│   │       └── java/
│   │           ├── TestCases/                 # Test classes
│   │           │   ├── HomePageTest.java
│   │           │   ├── RegisterTest.java
│   │           │   ├── LoginLogoutTest.java
│   │           │   ├── AllProductsPageTest.java
│   │           │   ├── ProductPageTest.java
│   │           │   ├── CartPageTest.java
│   │           │   ├── CheckoutPageTest.java
│   │           │   ├── ContactUsFormTest.java
│   │           │   └── TestCasesPageTest.java
│   │           └── Helper/                    # Helper classes & utilities
│   │               ├── HelperClass.java       # Common utilities
│   │               ├── User.java              # User data model
│   │               ├── RegisteredUser.java    # Registered user model
│   │               ├── FullUserData.java      # Complete user data
│   │               ├── Product.java           # Product data model
│   │               ├── ContactUsForm.java     # Contact form data
│   │               └── Reviews.java           # Reviews data model
│   ├── pom.xml                                # Maven configuration
│   ├── testng.xml                             # TestNG configuration
│   ├── allure-results/                        # Allure test results (generated)
│   ├── target/                                # Compiled classes & reports (generated)
│   └── Test+Generate.txt                      # Test & report generation instructions
├── Data/                                      # Test data files
│   └── testfile.txt
├── Downloads/                                 # Downloaded test data/invoices
│   ├── invoice.txt
│   └── invoice (1-7).txt
├── AllureScreenshots/                         # Screenshots for Allure reports
└── README.md                                  # This file
```

## 🚀 Installation & Setup

### Step 1: Clone the Repository

```bash
git clone https://github.com/yourusername/AutomationExercise-EdgesCourseGP.git
cd AutomationExercise-EdgesCourseGP
```

### Step 2: Navigate to Project Directory

```bash
cd AutomationExerciseWebsiteProject
```

### Step 3: Build the Project

```bash
mvn clean install
```

This command will:
- Clean previous builds
- Download all dependencies
- Compile the project
- Execute any initialization steps

### Step 4: Verify Setup

```bash
mvn -version
java -version
```

## 🧪 Running Tests

### Run All Tests

```bash
mvn clean test
```

### Run Specific Test Class

```bash
mvn test -Dtest=HomePageTest
mvn test -Dtest=RegisterTest
mvn test -Dtest=LoginLogoutTest
```

### Run Tests with TestNG Configuration

```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

### Run Tests with Specific Groups (if configured)

```bash
mvn test -Dgroups=smoke
mvn test -Dgroups=regression
```

### Run Tests in Parallel

```bash
mvn test -DparallelMode=methods -DthreadCount=4
```

## 📊 Test Modules

| Test Class | Purpose | Coverage |
|-----------|---------|----------|
| **HomePageTest** | Home page functionality | Page load, navigation, basic interactions |
| **RegisterTest** | User registration flow | Form validation, account creation |
| **LoginLogoutTest** | Authentication | Login, logout, session management |
| **AllProductsPageTest** | Product listing & filtering | Browse products, filter, search |
| **ProductPageTest** | Individual product details | Product info, reviews, related products |
| **CartPageTest** | Shopping cart operations | Add/remove items, quantity management |
| **CheckoutPageTest** | Payment & order flow | Checkout process, order confirmation |
| **ContactUsFormTest** | Contact form submission | Form validation, message sending |
| **TestCasesPageTest** | Test cases page navigation | Test case links and navigation |

## 📈 Reporting

### Generate Allure Report

After running tests, generate and view the Allure report:

```bash
# Generate Allure report
mvn allure:report

# Serve the report locally (opens in browser)
mvn allure:serve
```

The report will include:
- Test execution summary
- Pass/fail statistics
- Test duration metrics
- Screenshots on failure
- Detailed test logs
- Trends and analytics

### Report Artifacts

- **Allure Results**: `target/allure-results/` (raw JSON data)
- **Allure Report**: `target/site/allure-maven-plugin/` (generated HTML)
- **Screenshots**: `AllureScreenshots/` (failure screenshots)

## 📝 Directory Details

### Helper Classes (`src/test/java/Helper`)

- **HelperClass.java**: Reusable utilities for common operations
- **User.java**: User data model for login tests
- **RegisteredUser.java**: Model for registered user information
- **FullUserData.java**: Complete user profile data
- **Product.java**: Product information model
- **ContactUsForm.java**: Contact form submission data
- **Reviews.java**: Product review data model

### Test Data (`Data/` & `Downloads/`)

- **Data/testfile.txt**: Test input data
- **Downloads/**: Generated/downloaded test artifacts (invoices, etc.)

### Test Results (`allure-results/`)

Contains generated JSON reports from test executions for Allure reporting.

## 🛠️ Technologies Used

| Technology | Version | Purpose |
|-----------|---------|---------|
| **Java** | 24+ | Programming language |
| **Selenium WebDriver** | 4.34.0 | Browser automation |
| **TestNG** | 7.11.0 | Test framework |
| **Maven** | 3.8.0+ | Build automation |
| **Allure** | 2.24.0 | Test reporting |
| **AspectJ** | 1.9.20.1 | AOP for Allure integration |
| **WebDriver Manager** | Latest | Automatic driver management |

## 🐛 Troubleshooting

### Common Issues

**Issue**: Tests fail due to missing ChromeDriver
```bash
# Solution: WebDriver Manager handles this automatically
# If issues persist, clear cache and rebuild
mvn clean install
```

**Issue**: Allure report not generating
```bash
# Solution: Ensure allure-maven-plugin is installed
mvn allure:report
```

**Issue**: Tests timeout
```bash
# Increase timeout in test configuration or HelperClass
# Typical timeouts: Implicit 10s, Explicit 15s
```

**Issue**: Port already in use for Allure serve
```bash
# Use a different port
mvn allure:serve -Dport=8888
```

## 📚 Best Practices

- **Page Object Model**: All UI interactions encapsulated in page classes
- **Data-Driven Testing**: Test data separated from test logic
- **Wait Strategies**: Proper use of implicit/explicit waits to handle dynamic content
- **Reporting**: Comprehensive test reporting with screenshots on failure
- **Code Organization**: Clear separation of concerns with helper classes
- **Reusable Components**: Common functionality abstracted into utility methods

## 🤝 Contributing

1. Create a feature branch: `git checkout -b feature/new-test`
2. Make your changes and commit: `git commit -am 'Add new test'`
3. Push to the branch: `git push origin feature/new-test`
4. Submit a pull request

## 📄 License

This project is part of an educational course and follows the course's licensing terms.

## 📧 Support

For issues, questions, or contributions, please create an issue in the repository or contact the project maintainer.

---

**Last Updated**: March 2026
**Course**: Edges Course GP
**Target Website**: [AutomationExercise.com](https://www.automationexercise.com/)