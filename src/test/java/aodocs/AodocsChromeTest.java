package aodocs;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.driver.Browser;
import selenium.driver.WebDriverUtility;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AodocsChromeTest {
    private static WebDriver driver = null;
    private WebDriverWait wait = null;
    public static final long timeout = 5L;

    public static final String GOOGLE = "https://www.google.com";
    public static final String AODOCS = "aodocs";
    public static final String AODOCS_SITE = "www.aodocs.com";
    public static final String FIRST_NAME = "florian";
    public static final String DEMO = "Request a demo";


    public static final String EXPECTED_INPUT_ERROR = "Please complete this required field.";
    public static final String EXPECTED_EMAIL_ERROR = "Email must be formatted correctly.";
    public static final String EXPECTED_SELECT_ERROR = "Please select an option from the dropdown menu.";
    public static final String EXPECTED_GLOBAL_ERROR = "Please complete all required fields.";

    @BeforeEach()
    public void setUp() {
        driver = WebDriverUtility.getWebDriver(Browser.CHROME);
        wait = new WebDriverWait(driver, timeout);
    }

    @AfterEach()
    public void tearDown() {
        WebDriverUtility.closeWebDriver(driver);
    }

    @Test
    public void requestADemoOnChrome() {
        driver.get(GOOGLE);
        waitAndFindElement(By.xpath("//div[contains(text(), 'I agree')]")).click();
        WebElement search = driver.findElement(By.name("q"));
        search.sendKeys(AODOCS);
        search.submit();

        waitAndFindElement(By.xpath("//cite[contains(text(), '" + AODOCS_SITE + "')]")).click();
        waitAndFindElement(By.xpath("//a[contains(text(), '" + DEMO + "')]")).click();

        waitAndFindElement(By.name("firstname")).sendKeys(FIRST_NAME);
        waitAndFindElement(By.name("email")).sendKeys(RandomStringUtils.randomAlphanumeric(25));

        Select select = new Select(waitAndFindElement(By.name("company_size__c")));
        select.selectByIndex(ThreadLocalRandom.current().nextInt(1, 7));

        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, 450)");

        waitAndFindElement(By.xpath("//input[@value='Submit']")).click();

        String actualLastNameError = waitAndFindElement(By.xpath("//div[contains(@class, 'hs_lastname')]/ul//label")).getText();
        String actualEmailError = waitAndFindElement(By.xpath("//div[contains(@class, 'hs_email')]/ul//label")).getText();
        String actualCompanyError = waitAndFindElement(By.xpath("//div[contains(@class, 'hs_company')]/ul//label")).getText();
        String actualCountryError = waitAndFindElement(By.xpath("//div[contains(@class, 'hs_country')]/ul//label")).getText();
        String actualMessageError = waitAndFindElement(By.xpath("//div[contains(@class, 'hs_your_request__c')]/ul//label")).getText();
        String actualHearAboutUsError = waitAndFindElement(By.xpath("//div[contains(@class, 'hs_how_did_you_hear_about_us_')]/ul//label")).getText();
        String actualGlobalError = waitAndFindElement(By.xpath("//div[contains(@class, 'hs_error_rollup')]/ul//label")).getText();

        assertEquals(EXPECTED_INPUT_ERROR, actualLastNameError, "Validation on Last name failed");
        assertEquals(EXPECTED_EMAIL_ERROR, actualEmailError, "Validation on email failed");
        assertEquals(EXPECTED_INPUT_ERROR, actualCompanyError, "Validation on company failed");
        assertEquals(EXPECTED_SELECT_ERROR, actualCountryError, "Validation on country failed");
        assertEquals(EXPECTED_INPUT_ERROR, actualMessageError, "Validation on message failed");
        assertEquals(EXPECTED_SELECT_ERROR, actualHearAboutUsError, "Validation on Hear about us failed");
        assertEquals(EXPECTED_GLOBAL_ERROR, actualGlobalError, "Validation on Global error failed");
    }

    private WebElement browserTest(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    private WebElement waitAndFindElement(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
}