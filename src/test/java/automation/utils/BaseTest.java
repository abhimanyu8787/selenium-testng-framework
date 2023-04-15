package automation.utils;

import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeMethod;

import automation.genericfunctions.Utility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

    public WebDriver driver;

    public WebDriver initializeDriver() throws Exception {
        ExecutionMode executionMode = ExecutionMode.valueOf(Utility.getGlobalProperties("ExecutionMode"));
        switch (executionMode) {
        case Local: {
            String browserName = Utility.getGlobalProperties("Browser");

            if (browserName.contains("chrome")) {
                WebDriverManager.chromedriver().setup();
                String[] chromeConfig = { "--ignore-certificate-errors", "--ignore-ssl-errors=yes", "--no-sandbox",
                        "start-maximized" };
                String userDir = System.getProperty("user.dir");
                String downloadPath = userDir + "\\src\\test\\resources\\downloads";
                System.out.println("Download path is being set to: " + downloadPath);
                ChromeOptions options = new ChromeOptions();
                options.addArguments(chromeConfig);
                options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.default_content_settings.popups", 0);
                prefs.put("download.default_directory", downloadPath);
                options.setExperimentalOption("prefs", prefs);

                driver = new ChromeDriver(options);

            } else if (browserName.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            } else if (browserName.equalsIgnoreCase("edge")) {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            }
            break;
        }
        case Remote: {
            DesiredCapabilities cap = new DesiredCapabilities();
            cap.setBrowserName("chrome");
            cap.setPlatform(Platform.LINUX);
            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), cap);
            break;
        }
        case SeleniumGrid: {
            String host = Utility.getGlobalProperties("Host");
            String port = Utility.getGlobalProperties("Port");
            String browser = Utility.getGlobalProperties("NodeBrowser");

            String completeUrl = "http://" + host + ":" + port + "/wd/hub";
            System.out.println("Selenium Hub URL: "+completeUrl);

            if (browser.equalsIgnoreCase("Chrome")) {
                String[] chromeConfig = { "--ignore-certificate-errors", "--ignore-ssl-errors=yes", "--no-sandbox",
                        "start-maximized" };
                String userDir = System.getProperty("user.dir");
                String downloadPath = userDir + "\\src\\test\\resources\\downloads";
                System.out.println("Download path is being set to: " + downloadPath);
                ChromeOptions options = new ChromeOptions();
                options.addArguments(chromeConfig);
                options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
                Map<String, Object> prefs = new HashMap<String, Object>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                prefs.put("profile.default_content_settings.popups", 0);
                prefs.put("download.default_directory", downloadPath);
                options.setExperimentalOption("prefs", prefs);
                options.setCapability("browserName", "chrome");
                options.setCapability("platformName", "linux");
                driver = new RemoteWebDriver(new URL(completeUrl), options);
                break;
            }

        }
        default:
            throw new Exception("Unhandled Execution Mode!");
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
        return driver;

    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File file = new File(System.getProperty("user.dir") + "//reports//" + testCaseName + ".png");
        FileUtils.copyFile(source, file);
        return System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";

    }

    @BeforeMethod
    public void getWebDriver() throws Exception {
        driver = initializeDriver();
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
