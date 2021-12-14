package com.haeger;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

public class NavigationTest {
    WebDriver driver;

    String url = "192.168.0.10";
    String port = "8080";
    String searchTerm = "Ols";

    @BeforeMethod
    public void beforeMethod() throws IOException {
        // System.setProperty("webdriver.gecko.driver", "./src/test/resources/drivers/geckodriver");

        // set path of Chromedriver executable:
        // System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver");
        // initialize new WebDriver session:
        driver = new ChromeDriver(); // FirefoxDriver(); // ChromeDriver();
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }

    @Test
    public void navigateToAUrl() throws IOException  {

        AddressBookHomePage homePage = PageFactory.initElements(driver, AddressBookHomePage.class);
        // close Google's modal "Before You Continue" dialog, so that we can interact with the search:
        // homePage.closeModalBeforeYouContinueDialog();
        // Validate page title:
        // Assert.assertEquals(driver.getTitle(), "Google");
        // do the search:
        homePage.search(searchTerm);
        // Create screenshot:
        File scrFile  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./target/screenshot.png"));
        // Validate page title:
        // Assert.assertTrue(driver.getTitle().contains(searchTerm));
    }
}
