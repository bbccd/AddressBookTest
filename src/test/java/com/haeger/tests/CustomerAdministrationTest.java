package com.haeger;

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
import java.util.UUID;

import com.haeger.AddressBookHomePage;

import org.apache.commons.io.FileUtils;

public class CustomerAdministrationTest {

    WebDriver driver;
    AddressBookHomePage addressBookHomePage;

    // String url = "192.168.0.10";
    // String port = "8080";
    String searchTermExistingCustomerLastName = "Olsen";
    String searchTermExistingCustomerFirstName = "Katelyn";

    @BeforeMethod
    public void beforeMethod() throws IOException {
        // set path of driver executables:
        // System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver");
        // System.setProperty("webdriver.gecko.driver", "./src/test/resources/drivers/geckodriver");
        
        // initialize new WebDriver session:
        driver = new ChromeDriver(); // FirefoxDriver();
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }


    @Test
    public void findExistingCustomerByFirstName() throws IOException, InterruptedException  {
        AddressBookHomePage homePage = PageFactory.initElements(driver, AddressBookHomePage.class);

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




}
