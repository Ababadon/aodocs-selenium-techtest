package aodocs;

import config.TestConfig;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.driver.WebDriverUtility;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AodocsTest {
    private static WebDriver driver = null;
    private static WebDriverWait wait = null;
    public static final long timeout = 5L;

    private static TestConfig cfg = null;

    @BeforeEach()
    public void setUp() {
        cfg = ConfigFactory.create(TestConfig.class);
    }

    @AfterEach()
    public void tearDown() {
        WebDriverUtility.closeWebDriver(driver);
    }

    @ParameterizedTest
    @MethodSource("selenium.driver.WebDriverUtility#getAll")
    public void requestADemoTest(final WebDriver webDriver ) {
        driver = webDriver;
        wait = new WebDriverWait(driver, timeout);

        // Open google
        driver.get(cfg.google());

        // Approve cookies in Google
        waitAndFindElement(By.xpath("//div[contains(text(), 'I agree')]")).click();
        // / ! \ On Linux, even with all English options, the displayed language is French, so use this "ugly" id...
        //waitAndFindElement(By.id("L2AGLb")).click();

        // Search aodocs
        WebElement search = waitAndFindElement(By.name("q"));
        search.sendKeys(cfg.company());
        search.submit();

        // Open website corresponding to www.aodocs.com
        waitAndFindElement(By.xpath("//cite[contains(text(), '" + cfg.companySite() + "')]")).click();

        // Request a demo
        waitAndFindElement(By.xpath("//a[contains(text(), '" + cfg.demo() + "')]")).click();

        // Fill the form with my first name
        waitAndFindElement(By.name("firstname")).sendKeys(cfg.firstName());

        // Fill the form with a random string in the email (random means [a-zA-Z0-9])
        waitAndFindElement(By.name("email")).sendKeys(RandomStringUtils.randomAlphanumeric(25));

        // Choose a random value in Company size available in the options
        Select select = new Select(waitAndFindElement(By.name("company_size__c")));
        select.selectByIndex(ThreadLocalRandom.current().nextInt(1, 7));

        // Scroll down to have the Submit button into view
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, 450)");

        // Submit the form
        waitAndFindElement(By.xpath("//input[@value='Submit']")).click();

        // Get all error messages
        String actualLastNameError = waitAndFindElement(By.xpath("//div[contains(@class, 'hs_lastname')]/ul//label")).getText();
        String actualEmailError = waitAndFindElement(By.xpath("//div[contains(@class, 'hs_email')]/ul//label")).getText();
        String actualCompanyError = waitAndFindElement(By.xpath("//div[contains(@class, 'hs_company')]/ul//label")).getText();
        String actualCountryError = waitAndFindElement(By.xpath("//div[contains(@class, 'hs_country')]/ul//label")).getText();
        String actualMessageError = waitAndFindElement(By.xpath("//div[contains(@class, 'hs_your_request__c')]/ul//label")).getText();
        String actualHearAboutUsError = waitAndFindElement(By.xpath("//div[contains(@class, 'hs_how_did_you_hear_about_us_')]/ul//label")).getText();
        String actualGlobalError = waitAndFindElement(By.xpath("//div[contains(@class, 'hs_error_rollup')]/ul//label")).getText();

        assertEquals(cfg.expectedInputError(), actualLastNameError, "Validation on Last name failed");
        assertEquals(cfg.expectedEmailError(), actualEmailError, "Validation on email failed");
        assertEquals(cfg.expectedInputError(), actualCompanyError, "Validation on company failed");
        assertEquals(cfg.expectedSelectError(), actualCountryError, "Validation on country failed");
        assertEquals(cfg.expectedInputError(), actualMessageError, "Validation on message failed");
        assertEquals(cfg.expectedSelectError(), actualHearAboutUsError, "Validation on Hear about us failed");
        assertEquals(cfg.expectedGlobalError(), actualGlobalError, "Validation on Global error failed");
    }

    /**
     * Override findElement with a wait configured by a timeout
     *
     * @param by : the way to find the element
     * @return the web element
     */
    private WebElement waitAndFindElement(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

}
