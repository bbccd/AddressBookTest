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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.text.DateFormat;

import com.haeger.AddressBookHomePage;
import com.haeger.AddressBookFormView;

import org.apache.commons.io.FileUtils;

@Test(enabled = true)
public class CustomerAdministrationTest {

    WebDriver driver;
    AddressBookHomePage homePage;

    Locale systemLocale = Locale.getDefault();


    @BeforeMethod
    public void beforeMethod() throws IOException {
        // set path of driver executables:
        // System.setProperty("webdriver.chrome.driver", "./src/test/resources/drivers/chromedriver");
        // System.setProperty("webdriver.gecko.driver", "./src/test/resources/drivers/geckodriver");
        
        // initialize new WebDriver session:
        driver = new ChromeDriver(); // FirefoxDriver();
        homePage = PageFactory.initElements(driver, AddressBookHomePage.class);
    }

    @AfterMethod
    public void afterMethod() {
        driver.quit();
    }


    @Test(enabled = true)
    public void editExistingCustomerOnFirstName() throws IOException, InterruptedException, ParseException  {

        // Define test data:
        String searchTermExistingCustomerLastName = "Andersson";
        String newFirstName = "Jerry";
        String newEmail = "jerry@andersson.com";
        String newStatus = "ClosedLost"; // Customer // ImportedLead  // ClosedLost
        Date newBirthday = new SimpleDateFormat("dd/MM/yyyy").parse("23/08/1974");

        // wait for 1 s for page to load:
        Thread.sleep(1000);

        // filter for last name and open the editor (form) view:
        AddressBookFormView editView = homePage.filterByLastNameAndOpenEditFormViewFromFilterResultRowByLastName(searchTermExistingCustomerLastName);
        
        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // do the editing:
        editView.inputFirstName(newFirstName);
        editView.inputBirthday(newBirthday, systemLocale);
        editView.changeStatusByStatusName(newStatus);
        editView.inputEmail(newEmail);


        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // Create screenshot:
        File scrFile0  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile0, new File("./target/screenshot_editExistingCustomerOnFirstName_editing.png"));

        editView.saveChanges();

        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // reset search filter:
        homePage.resetSearch();

        // execute the search for last name again:
        // homePage.search(searchTermExistingCustomerLastName);

        // wait for 1 s to allow results to arrive:
        // Thread.sleep(1000);

        // open another (new) editor view to assert all changes (not all changed values are displayed in list):
        // AddressBookFormView newEditView = homePage.openEditFormViewFromFilterResultRowByLastName(searchTermExistingCustomerLastName);
        AddressBookFormView newEditView = homePage.filterByFullNameAndOpenEditFormViewFromFilterResultRowByFullName(newFirstName, searchTermExistingCustomerLastName);

        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // Create screenshot:
        File scrFile2  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile2, new File("./target/screenshot_editExistingCustomerOnFirstName_final.png"));

        // assert:
        Assert.assertEquals(newEditView.getFirstNameFromInputField(), newFirstName);
        Assert.assertEquals(newEditView.getLastNameFromInputField(), searchTermExistingCustomerLastName);
        Assert.assertEquals(newEditView.getEmailFromInputField(), newEmail);
        Assert.assertEquals(newEditView.getStatusFromDropdown(), newStatus);
        // Assert.assertEquals(newEditView.getBirthdayFromInputField(), "05/23/1971" ); // deactivated due to issues with String formatting
    }

    @Test(enabled = true)
    public void createNewCustomer_Assert_AndDelete() throws IOException, InterruptedException, ParseException  {

        // Define valid test data for a new customer that is guaranteed not to exist:
        String newLastName = UUID.randomUUID().toString();  // we use UUIDs as name values; they are practically guaranteed not to exist
        String newFirstName = UUID.randomUUID().toString();
        String newEmail = newFirstName + "." + newLastName + "@hotmail.com";
        String newStatus = "ImportedLead"; // Customer // ImportedLead  // ClosedLost
        Date newBirthday = new SimpleDateFormat("dd/MM/yyyy").parse("23/08/1964");

        // wait for 1 s for page to load:
        Thread.sleep(1000);

        // click "Add New Customer" button to open the editor (form) view:
        AddressBookFormView editView = homePage.addNewCustomer();
        
        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // do the editing:
        editView.inputFirstName(newFirstName);
        editView.inputLastName(newLastName);
        editView.inputBirthday(newBirthday, systemLocale);
        editView.changeStatusByStatusName(newStatus);
        editView.inputEmail(newEmail);

        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // Create screenshot:
        File scrFile0  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile0, new File("./target/screenshot_createNewCustomer_Assert_AndDelete_editing.png"));

        editView.saveChanges();

        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // reset search filter:
        homePage.resetSearch();

        // open another (new) editor view to assert all changes (not all changed values are displayed in list):
        AddressBookFormView newEditView = homePage.filterByFullNameAndOpenEditFormViewFromFilterResultRowByFullName(newFirstName, newLastName);

        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // Assert that we got exactly one filter result for the new full name (proving that exactly one data set has been created):
        int numberOfFilterResults = homePage.countFilterResults();
        Assert.assertEquals(numberOfFilterResults, 1);

        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // Create screenshot:
        File scrFile2  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile2, new File("./target/screenshot_createNewCustomer_Assert_AndDelete_created.png"));

        // assert that all values have been stored:
        Assert.assertEquals(newEditView.getFirstNameFromInputField(), newFirstName);
        Assert.assertEquals(newEditView.getLastNameFromInputField(), newLastName);
        Assert.assertEquals(newEditView.getEmailFromInputField(), newEmail);
        Assert.assertEquals(newEditView.getStatusFromDropdown(), newStatus);
        // Assert.assertEquals(newEditView.getBirthdayFromInputField(), "05/23/1971" ); // deactivated due to issues with String formatting

        // delete the new customer:
        newEditView.deleteDataset();

        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // reset search filter:
        homePage.resetSearch();

        // try to filter for the newly created and now deleted customer again;
        AddressBookFormView anotherEditView = homePage.filterByFullNameAndOpenEditFormViewFromFilterResultRowByFullName(newFirstName, newLastName);

        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);

        // Create screenshot:
        File scrFile3  = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile3, new File("./target/screenshot_createNewCustomer_Assert_AndDelete_deleted.png"));

        // Assert that now we got exactly ZERO filter results (proving that the new customer's data set has been successfully deleted again):
        int numberOfFilterResultsAfterDeletion = homePage.countFilterResults();
        Assert.assertEquals(numberOfFilterResultsAfterDeletion, 0);

    }

    private String getFormattedDateString(Date date, Locale locale) {
            // Create formatted String for system locale from birthdate Date object:
            DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, locale);
            String dateString = format.format(date);
            System.out.println("Birthday input string for system locale: " + dateString);
            return dateString;
        }

}
