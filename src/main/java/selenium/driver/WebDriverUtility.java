package selenium.driver;

import com.microsoft.edge.seleniumtools.EdgeDriver;
import com.microsoft.edge.seleniumtools.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.apache.commons.lang3.SystemUtils;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 *  Web driver class useful to create drivers of any registered browsers.
 *  Based on WebDriverManager to get automatically the corresponding web driver.
 */
public class WebDriverUtility {

    /**
     *  Create a webdriver based on the required browser
     *
     * @param browser One of the Browser provided
     * @return the webdriver to use for tests
     */
    public static WebDriver getWebDriver(Browser browser) {
        WebDriver webDriver;
        switch (browser) {
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver(getFireFoxOptions());
                break;
            case CHROME:
                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver(getChromeOptions());
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                webDriver = new EdgeDriver(getEdgeOptions());
                break;
            default:
                throw new RuntimeException("Unsupported Web Driver: " + browser);
        }
        webDriver.manage().window().maximize();

        return webDriver;
    }

    /**
     * Close the provided webdriver
     *
     * @param webDriver the webdriver to close
     */
    public static void closeWebDriver(WebDriver webDriver) {
        if (webDriver != null)
            webDriver.quit();
    }

    /**
     *  Get all registered browsers. Useful to start tests on all browsers
     *  For Windows : Chrome, Firefox, Edge
     *  For Linux : Chrome, Firefox
     *  TODO For Mac : Chrome, Firefox, Safari
     *
     * @return Stream web browsers
     */
    public static Stream<WebDriver> getAll() {
        if(SystemUtils.IS_OS_WINDOWS)
            return Arrays.stream(Browser.values()).map(WebDriverUtility::getWebDriver);
        else {
            Browser[] browsers = {Browser.CHROME, Browser.FIREFOX};
            return Arrays.stream(browsers).map(WebDriverUtility::getWebDriver);
        }
    }

    /**
     * Self-explanatory :)
     * Important : Change language to English
     *
     * @return options
     */
    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        // To start chrome in english
        options.addArguments("lang=en-GB");
        // To start chrome without security warning
        options.addArguments("disable-infobars");
        // TODO Use Chrome in headless mode + disable some options for CI tools :
        // options.addArguments("--no-sandbox", "--disable-setuid-sandbox", "--headless", "--disable-gpu", "--disable-setuid-sandbox", "--disable-dev-shm-usage");
        return options;
    }

    /**
     * Self-explanatory :)
     * Important : Change language to English
     *
     * @return options
     */
    private static FirefoxOptions getFireFoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("intl.accept_languages", "en-GB");
        options.setProfile(profile);
        return options;
    }

    /**
     * Self-explanatory :)
     * Important : Change language to English
     *
     * @return options
     */
    private static EdgeOptions getEdgeOptions() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("lang=en-GB");
        return options;
    }

}
