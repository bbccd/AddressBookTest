package com.haeger;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {

    WebDriver driver;
    com.haeger.AddressBookHomePage homePage;

    String nodeURL;

    @Given("the address book")
    public void the_address_book() throws MalformedURLException {
        nodeURL = "http://192.168.0.4:4444";
        ChromeOptions chromeOptions = new ChromeOptions();
        driver = new RemoteWebDriver(new URL(nodeURL), chromeOptions);
        homePage = PageFactory.initElements(driver, com.haeger.AddressBookHomePage.class);
    }

    @When("I filter for an existing user by last name")
    public void i_filter_for_an_existing_user_by_last_name() {

        String searchTermExistingCustomerLastName = "Olsen";
        // wait for 1 s to allow page to load:
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // execute the search:
        homePage.search(searchTermExistingCustomerLastName);
        // wait for 1 s to allow results to arrive:
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("I should find at least one result")
    public void i_should_find_at_least_one_result() throws IOException {
        // assert that results were found:
        int numberOfResults = homePage.countFilterResults();
        assertTrue(numberOfResults >= 1);

        // Create screenshot:
        File scrFile  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./target/screenshot_findExistingCustomerByLastName.png"));
    }

    @After()
    public void closeBrowser() {
        driver.quit();
    }
}
