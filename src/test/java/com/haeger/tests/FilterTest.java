package com.haeger;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
// import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import com.haeger.AddressBookHomePage;

import org.apache.commons.io.FileUtils;

@Test(enabled = true)
public class FilterTest {

    WebDriver driver;
    AddressBookHomePage homePage;

    String nodeURL;

    @BeforeMethod
    public void beforeMethod() throws IOException {
        // set path of driver executables:
        // System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver");
        // System.setProperty("webdriver.gecko.driver", "./src/test/resources/drivers/geckodriver");
        
        // initialize new WebDriver session:
//        driver = new ChromeDriver(); // FirefoxDriver();

        // set up Selenium Grid (i.e., remote web driver):
        nodeURL = "http://192.168.0.7:4444";
        ChromeOptions chromeOptions = new ChromeOptions();
//        DesiredCapabilities capability = DesiredCapabilities.chrome();
//        capability.setBrowserName("chrome");
//        capability.setPlatform(Platform.MAC);
        driver = new RemoteWebDriver(new URL(nodeURL), chromeOptions);

        homePage = PageFactory.initElements(driver, AddressBookHomePage.class);
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }

    public void findExistingCustomerByLastName() throws IOException, InterruptedException  {

        String searchTermExistingCustomerLastName = "Olsen";

        // wait for 1 s to allow page to load:
        Thread.sleep(1000);

        // execute the search:
        homePage.search(searchTermExistingCustomerLastName);

        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // assert that results were found:
        int numberOfResults = homePage.countFilterResults();
        Assert.assertTrue(numberOfResults >= 1);

        // Create screenshot:
        File scrFile  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./target/screenshot_findExistingCustomerByLastName.png"));
    }


    public void findExistingCustomerByFirstName() throws IOException, InterruptedException  {

        String searchTermExistingCustomerFirstName = "Katelyn";

        // wait for 1 s to allow page to load:
        Thread.sleep(1000);

        // execute the search:
        homePage.search(searchTermExistingCustomerFirstName);

        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // assert that results were found:
        int numberOfResults = homePage.countFilterResults();
        Assert.assertTrue(numberOfResults >= 1);

        // Create screenshot:
        File scrFile  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./target/screenshot_findExistingCustomerByFirstName.png"));
    }


    public void filterNonExistingCustomerComesUpEmpty() throws IOException, InterruptedException  {

        // create UUID as name of customer guaranteed not to exist
        String uuid = UUID.randomUUID().toString();

        // wait for 1 s to allow page to load:
        Thread.sleep(1000);

        // execute the search:
        homePage.search(uuid);

        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // assert that no results were found:
        int numberOfResults = homePage.countFilterResults();
        Assert.assertTrue(numberOfResults == 0);

        // Create screenshot:
        File scrFile  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./target/screenshot_filterNonExistingCustomerComesUpEmpty.png"));
    }


    public void resetFilterResetsResultsList() throws IOException, InterruptedException  {

        // create random UUID as name of customer that is guaranteed not to exist
        String uuid = UUID.randomUUID().toString();

        // wait for 1 s to allow page to load:
        Thread.sleep(1000);

        // execute the search:
        homePage.search(uuid);

        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // assert that no results were found:
        int numberOfResultsDuringFilter = homePage.countFilterResults();
        Assert.assertTrue(numberOfResultsDuringFilter == 0);

        // reset search filter:
        homePage.resetSearch();

        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // assert that datasets are displayed again in list:
        int numberOfResults = homePage.countFilterResults();
        Assert.assertTrue(numberOfResults > 9);

        // Create screenshot:
        File scrFile  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./target/screenshot_resetFilterResetsResultsList.png"));
    }
}
