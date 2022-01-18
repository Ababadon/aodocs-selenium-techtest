# AODocs Selenium technical test

See original request [here](#original-test-description)

### Prerequisites
* Java JDK: 10 or higher
* Maven: 3.3.9 or higher

### Tested With
* JDK 11
* MVN 3.3.9
* Chrome version : 
* Firefox version : 
* Edge version : 

All Tests are performed on Chrome, Firefox and Edge sequentially  
*TODO*: Run them in parallel.

### Run locally
Run:  
```mvn test```

Results reports:  
TBD

### Run with docker
TBD

### Continuous integration
TBD

# Original Test Description

This project is about assessing QA automation abilities.

## Getting Started

### Prerequisites
* Java JDK: 10 or higher
* Maven: 3.3.9 or higher

## Test Objectives
The marketing team ask you to write an automation test to validate how prospect access to our website, and the form to request a demo of the product.
 
The test steps are:
 1. Search AODocs in Google
 2. In the result, open the website www.aodocs.com
 3. Into the website click on the "Request a demo" button
 4. Fill the form with:
     * your first name
     * set empty in the "Last Name" field
     * fill a random string in the "Your Email" field
     * choose a value in Company Size
 5. Check the error messages

## Notes
In this project you can find a utility class "WebDriverUtility" to help you to start a browser locally.
To write the test, you need to use Junit 5 already configured in pom.xml
