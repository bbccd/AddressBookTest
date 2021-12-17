package com.haeger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByClassName;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.haeger.AddressBookFormView;

public class AddressBookHomePage {

    WebDriver driver;

    //Page URL
    private static String PAGE_URL="http://192.168.0.10:8080/";

    @FindBy(id = "tfFilter")
    WebElement searchField;

    @FindBy(id = "btnClearFilter")
    WebElement searchResetButton;

    @FindBy(id = "addNewCustomer")
    WebElement addNewCustomerButton;

    public AddressBookHomePage (WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
    }


    // Page Functions
    public void search(String searchTerm) {
        searchField.click();
        searchField.clear();
        searchField.sendKeys(searchTerm);
        return;
    }

    public void resetSearch() {
        searchResetButton.click();
    }

    public AddressBookFormView addNewCustomer() {
        addNewCustomerButton.click();
        return PageFactory.initElements(driver, AddressBookFormView.class);
    }


    /**
     * Method filters the database for a full name (first and last name, with a SPACE in between), and clicks on the first result row.
     * It then returns an instance of the Page Object Model for the edit form view.
     * TODO: Assumingly will crash if no items are found (since empty list is not handled properly)
     * @param firstName: String. MUST be full first name to match
     * @param lastName: String.
     * @return AddressBookFormView: an instance of the Page Object Model for the edit form view
     * @throws InterruptedException
     */
    public AddressBookFormView filterByFullNameAndOpenEditFormViewFromFilterResultRowByFullName(String firstName, String lastName) throws InterruptedException {
        String fullName = firstName + " " + lastName;
        resetSearch();
        search(fullName);
        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);
        List <WebElement> filterResultsList = driver.findElements(By.cssSelector("tr.v-grid-row-has-data"));
        if (!filterResultsList.isEmpty()) {
            System.out.println("Counting filter results: " + filterResultsList.size());
            WebElement nameTableItem = filterResultsList.get(0);
            nameTableItem.click();
        }
        return PageFactory.initElements(driver, AddressBookFormView.class);
    }


    /**
     * Method filters the customer list by a last name, and then finds the first row in which this supplied String is contained in the column "last name"
     * by exact match, and clicks on it to open the edit functionalty UI. 
     * It then returns an instance of the Page Object Model for the edit form view.
     * USE CASE: when data set shall be found by last name. It still selects the first available row with that last name.
     * NOTE: The full last name must be supplied, because the search of the correct row finds it by exact String comparison ("equals").
     * TODO: Will crash if no items are found (since empty list is not handled properly)
     * @param lastName: String. MUST be full last name to match!
     * @return AddressBookFormView: an instance of the Page Object Model for the edit form view
     * @throws InterruptedException
     */
    public AddressBookFormView filterByLastNameAndOpenEditFormViewFromFilterResultRowByLastName(String lastName) throws InterruptedException {
        resetSearch();
        search(lastName);
        // wait for 1 s to allow results to arrive:
        Thread.sleep(1000);
        AddressBookFormView form = openEditFormViewFromFilterResultRowByLastName(lastName);
        return form;
    }


    /**
     * Helper function to click on the first available filter result row on which the supplied string is contained in the column "last name"
     * by exact String match. (NOTE: do parts of names can be matched! I.e.: last name "Andersson" can only be found by String "Andersson", not e.g. "Ander"!)
     * @param lastName: String
     * @return AddressBookFormView: an instance of the Page Object Model for the edit form view
     * @throws InterruptedException
     */
    private AddressBookFormView openEditFormViewFromFilterResultRowByLastName(String lastName) throws InterruptedException {
        List <WebElement> filterResultsList = driver.findElements(By.cssSelector("tr.v-grid-row-has-data"));
        if (!filterResultsList.isEmpty()) {
            // iterate through list items ("rows" of table):
            for (WebElement listItem : filterResultsList) {
                List <WebElement> tableRowDataItems = listItem.findElements(By.cssSelector("td.v-grid-cell"));
                // get 2nd column of current row ("table data" item) - this is the column listing the "last name":
                String itemInnerHTML = tableRowDataItems.get(1).getText();
                    if (itemInnerHTML.equals(lastName)) {
                        listItem.click();
                        Thread.sleep(1000);
                        break;
                    }
            }
        }
        return PageFactory.initElements(driver, AddressBookFormView.class);
    }

    

    // readout methods (used to obtain current values for assertions):
    public int countFilterResults() {
        // List <WebElement> filterResultsList = driver.findElements(By.className("v-grid-row-has-data"));
        // List <WebElement> filterResultsList = driver.findElements(By.xpath("//table/tbody/tr [@class='v-grid-row-has-data']"));
        List <WebElement> filterResultsList = driver.findElements(By.cssSelector("tr.v-grid-row-has-data"));
        // System.out.println("Counting filter results: " + filterResultsList.size());
        return filterResultsList.size();
    }

}