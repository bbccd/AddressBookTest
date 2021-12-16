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

    public AddressBookHomePage (WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
    }

    public void search(String searchTerm) {
        searchField.click();
        searchField.clear();
        searchField.sendKeys(searchTerm);
        return;
    }

    public int countFilterResults() {
        // List <WebElement> filterResultsList = driver.findElements(By.className("v-grid-row-has-data"));
        // List <WebElement> filterResultsList = driver.findElements(By.xpath("//table/tbody/tr [@class='v-grid-row-has-data']"));
        List <WebElement> filterResultsList = driver.findElements(By.cssSelector("tr.v-grid-row-has-data"));
        System.out.println("Counting filter results: " + filterResultsList.size());
        return filterResultsList.size();
    }

    public void resetSearch() {
        searchResetButton.click();
    }

    /**
     * Method filters the customer list by a last name, and clicks on the first row in which the search string is contained 
     * in the column "last name" to open the edit function. 
     * It then returns an instance of the Page Object Model for the edit form view.
     * USE CASE: when data set shall be found by last name. It still selects the first available row.
     * TODO: Will crash if no items are found (since empty list is not handled properly)
     * @param lastName: String. Could be just a few characters, or full last name
     * @return
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
     * Method filters the customer list by a name, and clicks on the first row to open the edit function. 
     * It then returns an instance of the Page Object Model for the edit form view.
     * Filtering searches both first and last name. So, if only a few charcters are supplied, this may return a number of data sets.
     * On the other hand, a full name (first name, folled by last name, with space in between) returns that data set.
     * USE CASES: 
     *      1. when the exact dataset to edit is not crucial (just anything to edit).
     *      2. searching for exact name (first and last name)
     * @param name: String. Could be just a few characters, or first and last name (divided by space)
     * @return
     */
    public AddressBookFormView openEditFormViewFromFilterResultRowByName(String name) {
        List <WebElement> filterResultsList = driver.findElements(By.cssSelector("tr.v-grid-row-has-data"));
        if (!filterResultsList.isEmpty()) {
            System.out.println("Counting filter results: " + filterResultsList.size());
            WebElement nameTableItem = filterResultsList.get(0);
            nameTableItem.click();
        }
        return PageFactory.initElements(driver, AddressBookFormView.class);
    }

    public AddressBookFormView filterByFullNameAndOpenEditFormViewFromFilterResultRowByFullName(String firstName, String lastName) throws InterruptedException {
        String fullName = firstName + " " + lastName;
        System.out.println("Filtering for full name: " + fullName);
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

    public AddressBookFormView openEditFormViewFromFilterResultRowByLastName(String lastName) throws InterruptedException {
        List <WebElement> filterResultsList = driver.findElements(By.cssSelector("tr.v-grid-row-has-data"));
        if (!filterResultsList.isEmpty()) {
            System.out.println("Counting filter results: " + filterResultsList.size());
            // iterate through list items ("rows" of table):
            for (WebElement listItem : filterResultsList) {
                List <WebElement> tableRowDataItems = listItem.findElements(By.cssSelector("td.v-grid-cell"));
                // iterate through columns of current row ("table data" items):
                WebElement lastNameColumnItem = tableRowDataItems.get(1);
                // for (WebElement columnItem : filterResultsList) {
                    String itemInnerHTML = lastNameColumnItem.getText(); // getAttribute("value");
                    System.out.println("Checking table data item: " + itemInnerHTML);
                    if (itemInnerHTML.equals(lastName)) {
                        System.out.println("Found item " + lastName + " in dropdown list item: " + itemInnerHTML);
                        // String cssSelector = String.format("option[value='%s']", newStatusValue);
                        // WebElement selectField = statusSelect.findElement(By.cssSelector(cssSelector)); //"option[value='4']"));
                        listItem.click();
                        Thread.sleep(1000);
                        break;
                    }
                // }
            }
        }
        // if nothing was found, open first row (IS THIS GOOD?):
        //WebElement nameTableItem = filterResultsList.get(0);
        //nameTableItem.click();
        return PageFactory.initElements(driver, AddressBookFormView.class);
    }

}