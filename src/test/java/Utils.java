import org.apache.commons.io.FileUtils;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class Utils {

    public static ChromeDriver newChromeDriver(){
        System.setProperty("webdriver.chrome.driver", Constants.CHROMEDRIVER_PATH);
        ChromeDriver chromeDriver = new ChromeDriver();
        return chromeDriver;
    }

    public static FirefoxDriver newFireFoxDriver(){
        System.setProperty("webdriver.gecko.driver", Constants.FIREFOXDRIVER_PATH);
        FirefoxDriver firefoxDriver = new FirefoxDriver();
        return firefoxDriver;
    }
}
