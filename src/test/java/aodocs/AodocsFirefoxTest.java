package aodocs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.WebDriver;
import selenium.driver.Browser;
import selenium.driver.WebDriverUtility;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class AodocsFirefoxTest {
    private static WebDriver webDriver = null;
    public static final String GOOGLE = "https://www.google.com";
    public static final String AODOCS = "aodocs";


    @BeforeEach()
    public void setUp() {
        webDriver = WebDriverUtility.getWebDriver(Browser.FIREFOX);
    }

    @AfterEach()
    public void tearDown() {
        WebDriverUtility.closeWebDriver(webDriver);
    }

    @Test
    public void requestADemo() {
        webDriver.get(GOOGLE);
        webDriver.findElement(By.id("L2AGLb")).click();

        WebElement search = webDriver.findElement(By.name("q"));
        search.sendKeys(AODOCS);
        search.submit();

        assertTrue(true);
    }
}