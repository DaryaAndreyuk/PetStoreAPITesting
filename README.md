# Pet Store API Automation Testing

## Portfolio project on automation of test scripts using API tests for [Swagger Pet Store](https://petstore.swagger.io/#/)

## Contents:
- [Technology stack used](https://github.com/DaryaAndreyuk/PetStoreAPITesting/tree/fb_DaryaAndreyuk_8_AddReadMe?tab=readme-ov-file#technology-stack-used)
- [Running the Tests from the terminal](https://github.com/DaryaAndreyuk/PetStoreAPITesting/tree/fb_DaryaAndreyuk_8_AddReadMe?tab=readme-ov-file#running-the-tests)
- [Viewing Allure Reports](https://github.com/DaryaAndreyuk/PetStoreAPITesting/tree/fb_DaryaAndreyuk_8_AddReadMe?tab=readme-ov-file#viewing-allure-reports)

## Technology stack used

<img src="images/Intelij_IDEA.svg" alt="IDEA" width="10%" height="40"/> <img src="images/Allure_Report.svg" alt="Allure_Report" width="10%" height="40"/> <img src="images/Gradle.svg" alt="Gradle" width="10%" height="40"/> <img src="images/Java.svg" alt="Java" width="10%" height="40"/> <img src="images/RestAssured.svg" alt="RestAssured" width="10%" height="40"/> <img src="images/testng.png" alt="testng" width="60" height="40"/>

In this project, automated tests are written in `Java` using the `Rest-Assured` framework for API testing.

- `Gradle` is used as the build tool.
- `TestNG` is the testing framework.
- `Lombok` is utilized to simplify model creation in the API tests.
- `Allure` Rest-Assured listener is applied for generating test reports.

## Running the Tests

To execute the tests, run the following command in the CLI:

`gradlew test`

## Viewing Allure Reports

After running the tests, you can view the Allure reports by executing: 

`gradlew allureServe` 

## Example of Allure Report

<img src="images/AllureExample.png" alt="AllureReportExample" />

## Report with test results

<img src="images/Allure-ReportExample.png" alt="AllureReportExample1" />

If you have any comments or suggestions, please feel free to contact me at: [dasha.andreyuk@gmail.com](dasha.andreyuk@gmail.com)