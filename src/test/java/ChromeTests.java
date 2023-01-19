import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import static org.openqa.selenium.support.locators.RelativeLocator.with;
import static org.testng.Assert.*;

import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class ChromeTests {

    private static WebDriver driver;
    String ex1 = "https://dgotlieb.github.io/WebCalculator/";
    String ex5 = "https://dgotlieb.github.io/Actions";

    String ex6 = "https://dgotlieb.github.io/Controllers";

    String ex8 = "https://www.themarker.com/";
    @BeforeMethod
    public void initTestsChorme(){
        driver = Utils.newChromeDriver();
    }

    @Test
    public void ex1Test() {
        driver.get(ex1);
        WebElement sevenDigit = driver.findElement(By.id("seven"));
        WebElement sixDigit = driver.findElement(By.id("six"));
        WebElement fiveDigit = driver.findElement(By.id("five"));
        WebElement addBtn = driver.findElement(By.id("add"));
        WebElement equalBtn = driver.findElement(By.id("equal"));

        //print rectangle height and width
        System.out.println("height "+sevenDigit.getRect().height);
        System.out.println("width "+sevenDigit.getRect().width);

        //assert element visible
        assertTrue(sixDigit.isDisplayed());

        //perform calc action
        String twelve = "12";
        sevenDigit.click();
        addBtn.click();
        fiveDigit.click();
        equalBtn.click();
        String result = driver.findElement(By.id("screen")).getText();
        assertEquals(result, twelve);

    }

    @Test
    public void ex2Test() {
        String webTitle = "Calculator";
        String webURL = ex1;
        driver.get(ex1);
        String titleResult = driver.getTitle();
        assertEquals(webTitle,titleResult);
        String urlResult = driver.getCurrentUrl();
        assertEquals(webURL,urlResult);
    }

    @Test
    public void ex3Test() {
        driver.get(ex1);
        String webTitlefirst = driver.getTitle();
        driver.navigate().refresh();
        String webTitlesecond = driver.getTitle();
        assertEquals(webTitlefirst,webTitlesecond);
    }

    @Test
    public void ex4Test() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-extensions");
        WebDriver driver1 = new ChromeDriver(options);
        driver1.get(ex1);
    }

    @Test
    public void ex5Test() {
        driver.get(ex5);
        //take a screenshot
        takeElementScreenshot(driver.findElement(By.cssSelector("div[id='div1']")));

        //double click
        //WebElement myElement = driver.findElement(withTagName("p").above(By.id("drag1")));
        WebElement doubleClickLabel = driver.findElement(By.xpath("/html/body/p[2]"));
        Actions myAction = new Actions(driver);
        myAction.doubleClick(doubleClickLabel);
        myAction.perform();
        String doubleClickExpected = "You double clicked";
        String doubleClickSuccess = driver.findElement(By.id("demo")).getText();
        assertEquals(doubleClickSuccess,doubleClickExpected);

        //hover over
        Actions hoverAction = new Actions(driver);
        WebElement xImg = driver.findElement(By.cssSelector("img[id='close']"));
        hoverAction.moveToElement(xImg);

//        //select 2 items from list
//        WebElement pizza = driver.findElement(By.cssSelector("option[value='pizza']"));
//        WebElement burger = driver.findElement(By.cssSelector("option[value='burger']"));
//        WebElement pasta = driver.findElement(By.cssSelector("option[value='pasta']"));
//        Actions selectItems = new Actions(driver);
//        selectItems.clickAndHold(pizza).clickAndHold(burger);
//        selectItems.build().perform();
//        assertTrue(((WebElement) pizza).isSelected());
//        assertTrue(((WebElement) burger).isSelected());
//        assertFalse(((WebElement) pasta).isSelected());

        //upload file
        String filePath = "C:\\Users\\eknmt\\IdeaProjects\\Web Drivers\\chromedriver_win32\\LICENSE.chromedriver";
        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(filePath);
        String uploadExpected = "Upload item";
        String uploadResult = driver.findElement(By.xpath("/html/body/p[6]")).getText();
        assertEquals(uploadResult,uploadExpected);

        //scroll to element
        WebElement scrollTo = driver.findElement(By.id("clickMe"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", scrollTo);



    }

    @Test
    public void ex6Test(){
        driver.get(ex6);

        //select radio button
        WebElement rBtnCheese = driver.findElement(By.cssSelector("input[value='Cheese']"));
        rBtnCheese.click();
        assertTrue(rBtnCheese.isSelected());

        //Select Dropdown option
        Select food = new Select(driver.findElement(By.name("dropdownmenu")));
        food.selectByValue("Milk");
        String dropdownExpected = "Milk";
        assertEquals(driver.findElement(By.name("dropdownmenu")).getAttribute("value"),dropdownExpected);
        ArrayList <String> foodList = new ArrayList<>();
        for (int i=0; i<3; i++){
            food.selectByIndex(i);
            foodList.add(driver.findElement(By.name("dropdownmenu")).getAttribute("value"));
            System.out.println(foodList.get(i));
        }
    }

    @Test
    public void ex7Test(){
        driver.get(ex1);
        WebElement twoDigit = driver.findElement(By.id("two"));
        WebElement sixDigit = driver.findElement(By.id("six"));

        //print rectangle height and width
        System.out.println("height "+twoDigit.getRect().height);
        System.out.println("width "+sixDigit.getRect().width);
    }

    @Test
    public void ex8Test() throws IOException {
        driver.get(ex8);
        String pageSource =driver.getPageSource();
        writeToFile("source.html",pageSource);
        Scanner input = new Scanner( new File( "source.html" ) );
        int count = 0;
        while ( input.hasNextLine() ) {
            String answer = input.nextLine();
            answer = answer.toLowerCase();
            for (int i = 0; i < answer.length() - 4; i++) {
                String news = answer.substring(i, i + 4);
                if (news.equalsIgnoreCase("news")) {
                    count++;
                }

            }
        }
        System.out.println(count);
    }

    @Test
    public void ex9Test() {
        driver.get(ex1);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.print()");
    }

    @AfterMethod
    public void tearDown(){
        driver.quit();
    }


    private static void takeElementScreenshot(WebElement element){
        File screenShotFile = element.getScreenshotAs(OutputType.FILE); // take the screenshot
        try {
            FileUtils.copyFile(screenShotFile, new File("result.png")); // save screenshot to disk
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void writeToFile (String fileName, String content) throws IOException {
        File myObj = new File(fileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.write(content);
        writer.close();

    }

}
