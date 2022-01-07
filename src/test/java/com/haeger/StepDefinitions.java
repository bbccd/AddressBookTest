package com.haeger;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {

    WebDriver driver;
    com.haeger.AddressBookHomePage homePage;

    String nodeURL;

    @Before
    public void openAddressBook() throws MalformedURLException {
        nodeURL = "http://192.168.0.4:4444";
        ChromeOptions chromeOptions = new ChromeOptions();
        driver = new RemoteWebDriver(new URL(nodeURL), chromeOptions);
    }

    @Given("the address book")
    public void the_address_book() throws MalformedURLException {
        homePage = PageFactory.initElements(driver, com.haeger.AddressBookHomePage.class);
    }

    @When("I filter for an existing customer by last name")
    public void i_filter_for_an_existing_customer_by_last_name() {

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

    @When("I filter for an existing customer by first name")
    public void i_filter_for_an_existing_customer_by_first_name() {

        String searchTermExistingCustomerFirstName = "Katelyn";
        // wait for 1 s to allow page to load:
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // execute the search:
        homePage.search(searchTermExistingCustomerFirstName);
        // wait for 1 s to allow results to arrive:
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @When("I filter for a non-existent customer by a name that is an arbitrary UUID")
    public void i_filter_for_a_non_existent_customer_by_a_name_that_is_an_arbitrary_UUID() {

        // create UUID as name of customer guaranteed not to exist
        String uuid = UUID.randomUUID().toString();

        // wait for 1 s to allow page to load:
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // execute the search:
        homePage.search(uuid);

        // wait for 1 s to allow page to load:
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @And("reset the filter")
    public void reset_the_filter() throws InterruptedException {
        // reset search filter:
        homePage.resetSearch();
        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);
    }

    @Then("I should find at least one result")
    public void i_should_find_at_least_one_result() throws IOException {
        // assert that results were found:
        int numberOfResults = homePage.countFilterResults();
        assertTrue(numberOfResults >= 1);

        // Create screenshot:
        File scrFile  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./target/screenshot_findExistingCustomerByName.png"));
    }

    @Then("I should find exactly 0 results")
    public void i_should_find_exactly_0_results() throws IOException {
        // assert that no results were found:
        int numberOfResults = homePage.countFilterResults();
        assertTrue(numberOfResults == 0);

        // Create screenshot:
        File scrFile  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./target/screenshot_filterNonExistingCustomerComesUpEmpty.png"));
    }

    @After()
    public void closeBrowser() {
        driver.quit();
    }

    @Then("I should find a large number of results in the results list")
    public void i_should_find_a_large_number_of_results_in_the_results_list() throws IOException {
        // assert that datasets are displayed again in list:
        int numberOfResults = homePage.countFilterResults();
        assertTrue(numberOfResults > 9);

        // Create screenshot:
        File scrFile  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./target/screenshot_resetFilterResetsResultsList.png"));
    }
}
