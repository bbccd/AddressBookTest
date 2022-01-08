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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StepDefinitions {

    WebDriver driver;
    com.haeger.AddressBookHomePage homePage;
    com.haeger.AddressBookFormView editView;

    String nodeURL;
    Locale systemLocale = Locale.getDefault();

    // Define valid test data for a new customer that is guaranteed not to exist:
    String newLastName;  // we use UUIDs as name values; they are practically guaranteed not to exist
    String newFirstName;
    String newEmail;
    String newStatus; // Customer // ImportedLead  // ClosedLost
    String newBirthdayString;

    @Before
    public void openAddressBook() throws MalformedURLException {
        nodeURL = "http://192.168.0.4:4444";
        ChromeOptions chromeOptions = new ChromeOptions();
        driver = new RemoteWebDriver(new URL(nodeURL), chromeOptions);
    }

    @Before
    public void initializeRandomNewCustomerParameters() throws ParseException {
        // Define random values for creation of new data sets in test cases:
        newLastName = UUID.randomUUID().toString();  // we use UUIDs as name values; they are practically guaranteed not to exist
        newFirstName = UUID.randomUUID().toString();
        newEmail = newFirstName + "." + newLastName + "@hotmail.com";
        newStatus = "ImportedLead"; // Customer // ImportedLead  // ClosedLost
        Date newBirthday = new SimpleDateFormat("dd/MM/yyyy").parse("11/09/1963");
        newBirthdayString = getFormattedDateString(newBirthday, systemLocale);
    }

    @After()
    public void closeBrowser() {
        driver.quit();
    }

    // GIVEN
    @Given("the address book")
    public void the_address_book() throws MalformedURLException {
        homePage = PageFactory.initElements(driver, com.haeger.AddressBookHomePage.class);
        // wait for 1 s to allow page to load:
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Filter steps:
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



    @Then("I should find a large number of results in the results list")
    public void i_should_find_a_large_number_of_results_in_the_results_list() throws IOException {
        // assert that datasets are displayed again in list:
        int numberOfResults = homePage.countFilterResults();
        assertTrue(numberOfResults > 9);

        // Create screenshot:
        File scrFile  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./target/screenshot_resetFilterResetsResultsList.png"));
    }


    // Administration steps:
    @When("I click on Add New Customer")
    public void i_click_on_add_new_customer() throws InterruptedException {

        // wait for 1 s for page to load:
        Thread.sleep(1000);

        // click "Add New Customer" button to open the editor (form) view:
        editView = homePage.addNewCustomer();

        // wait for 1 s for page to load:
        Thread.sleep(1000);
    }


    @And("input a random first name")
    public void inputARandomFirstName() {
        editView.inputFirstName(newFirstName);
    }

    @And("input a random last name")
    public void inputARandomLastName() {
        editView.inputLastName(newLastName);
    }

    @And("input a random birthday")
    public void inputARandomBirthday() {
        editView.inputBirthday(newBirthdayString);
    }

    @And("input a random email address")
    public void inputARandomEmailAddress() {
        editView.inputEmail(newEmail);
    }

    @And("input a status of Imported Lead")
    public void inputAStatusOfImportedLead() throws InterruptedException {
        editView.changeStatusByStatusName(newStatus);
    }

    @And("click Save")
    public void clickSave() throws InterruptedException, IOException {

        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // Create screenshot:
        File scrFile0  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile0, new File("./target/screenshot_createNewCustomer_Assert_AndDelete_editing.png"));

        editView.saveChanges();

        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);
    }

    @And("filter again by the random first and last names")
    public void filterAgainByTheRandomFirstAndLastNames() throws InterruptedException {
        editView = homePage.filterByFullNameAndOpenEditFormViewFromFilterResultRowByFullName(newFirstName, newLastName);

        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);
    }

    @Then("I should find exactly one result")
    public void iShouldFindExactlyOneResult() throws InterruptedException, IOException {
        // Assert that we got exactly one filter result for the new full name (proving that exactly one data set has been created):
        int numberOfFilterResults = homePage.countFilterResults();
        assertEquals(numberOfFilterResults, 1);

        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // Create screenshot:
        File scrFile2  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile2, new File("./target/screenshot_createNewCustomer_Assert_AndDelete_created.png"));
    }

    @And("all values match the random ones used")
    public void allValuesMatchTheRandomOnesUsed() {
        // assert that all values have been stored:
        assertEquals(editView.getFirstNameFromInputField(), newFirstName);
        assertEquals(editView.getLastNameFromInputField(), newLastName);
        assertEquals(editView.getEmailFromInputField(), newEmail);
        assertEquals(editView.getStatusFromDropdown(), newStatus);
        // Assert.assertEquals(newEditView.getBirthdayFromInputField(), "05/23/1971" ); // deactivated due to issues with String formatting
    }

    /**
     * Helper method to convert Date object to string formatted correctly for the address book input field.
     * TODO: not really working well enough (shall also be used for assertions, but while input field accepts formats such as 07/31/1977
     * , a readout sometimes returns sloppy-formatted values with single digit components such as "7/4/22"; also the requirements and treatment
     * of year values is not yet understood - ASK DEVELOPMENT!)...
     * @param date
     * @param locale
     * @return
     */
    private String getFormattedDateString(Date date, Locale locale) {
        // Create formatted String for system locale from birthdate Date object:
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        String dateString = format.format(date);
        // System.out.println("Birthday input string for system locale: " + dateString);
        return dateString;
    }
}
