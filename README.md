# Pet Store API Automation Testing

## Portfolio project on automation of test scripts using API tests for [Swagger Pet Store](https://petstore.swagger.io/#/)

## Contents:

- [Technology stack used](https://github.com/DaryaAndreyuk/PetStoreAPITesting?tab=readme-ov-file#technology-stack-used)
- [Running the Tests from the terminal](https://github.com/DaryaAndreyuk/PetStoreAPITesting?tab=readme-ov-file#running-the-tests)
- [Viewing Allure Reports](https://github.com/DaryaAndreyuk/PetStoreAPITesting?tab=readme-ov-file#running-the-tests)
- [Build with Jenkins](https://github.com/DaryaAndreyuk/PetStoreAPITesting?tab=readme-ov-file#build-with-jenkins)

## Technology stack used

<p align="center" dir="auto">
<a href="https://www.jetbrains.com/idea/" rel="nofollow"><img width="11%" title="IntelliJ IDEA" src="images/Intelij_IDEA.svg" alt="Intellij_IDEA" style="max-width: 100%;"></a>
<a href="https://www.java.com/" rel="nofollow"><img width="11%" title="Java" src="images/Java.svg" alt="Java" style="max-width: 100%;"></a>
<a href="https://allurereport.org/" rel="nofollow"><img width="11%" title="Allure Report" src="images/Allure_Report.svg" alt="Allure_Report" style="max-width: 100%;"></a>
<a href="https://gradle.org/" rel="nofollow"><img width="11%" title="Gradle" src="images/Gradle.svg" alt="Gradle" style="max-width: 100%;"></a>
<a href="https://www.jenkins.io/" rel="nofollow"><img width="11%" title="Jenkins" src="images/Jenkins.svg" alt="Jenkins" style="max-width: 100%;"></a>
<a href="https://github.com/"><img width="12%" title="GitHub" src="images/GitHub.svg" alt="GitHub" style="max-width: 100%;"></a>
<a href="https://rest-assured.io/" rel="nofollow"><img width="11%" title="Rest Assured" src="images/RestAssured.svg" alt="RestAssured" style="max-width: 100%;"></a>
<a href="https://testng.org/" rel="nofollow"><img width="12%" title="TestNG" src="images/TestNG.png" alt="TestNG" style="max-width: 100%;"></a>

</p>

In this project, automated tests are written in `Java 21` using the `Rest-Assured` framework for API testing.

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

## Build with Jenkins

<img src="images/Jenkins-screen.png" alt="JenkinsScreen" />

## See Allure Report from Jenkins

<img src="images/JenkinsAllure-screen.png" alt="JenkinsAllureScreen" />


If you have any comments or suggestions, please feel free to contact me
at: [dasha.andreyuk@gmail.com](dasha.andreyuk@gmail.com)
