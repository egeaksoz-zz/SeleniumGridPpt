import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class SeleniumGridParalllel {

    private ThreadLocal<RemoteWebDriver> driver = null;

    @BeforeMethod
    @Parameters("browser")
    public void beforeMethod(String browser) throws MalformedURLException {

        driver = new ThreadLocal<RemoteWebDriver>();
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if(browser.equals("chrome")){
            ChromeOptions options = new ChromeOptions();
            options.addArguments("start-maximized");
            capabilities = DesiredCapabilities.chrome();
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        }else if(browser.equals("firefox")){
            FirefoxProfile profile = new FirefoxProfile();
            capabilities = DesiredCapabilities.firefox();
            capabilities.setCapability(FirefoxDriver.PROFILE,profile);
            capabilities.setCapability("marionette",true);
        }

        driver.set(new RemoteWebDriver(new URL("http://192.168.19.224:4444/wd/hub"),capabilities));
    }

    @Test
    public void chromeAmazonTest(){
        driver.get().navigate().to("https://www.amazon.com");
    }

    @Test
    public void firefoxAmazonTest(){
        driver.get().manage().window().maximize();
        driver.get().navigate().to("https://www.amazon.com");
    }

    @AfterMethod
    public void afterMethod(){
        driver.get().quit();
    }

}