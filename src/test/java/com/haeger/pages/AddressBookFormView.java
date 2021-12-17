package com.haeger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class AddressBookFormView {

    WebDriver driver;

    // Consstructor:
    public AddressBookFormView (WebDriver driver) {
        this.driver = driver;
    }

    // Page Elements
    @FindBy(id = "tfFirstName")
    WebElement firstNameInputField;

    @FindBy(id = "tfLastName")
    WebElement lastNameInputField;

    @FindBy(id = "tfEmail")
    WebElement emailInputField;

    @FindBy(className = "v-select-select")
    WebElement statusSelect;

    @FindBy(className = "v-datefield-textfield")
    WebElement birthdayInputField;

    @FindBy(id = "btnSave")
    WebElement saveButton;

    @FindBy(id = "btnDelete")
    WebElement deleteButton;


    // Page Functions
    public void inputFirstName(String firstName) {
        firstNameInputField.clear();
        firstNameInputField.sendKeys(firstName);
        return;
    }

    public void inputLastName(String lastName) {
        lastNameInputField.clear();
        lastNameInputField.sendKeys(lastName);
        return;
    }

    public void inputEmail(String email) {
        emailInputField.clear();
        emailInputField.sendKeys(email);
        return;
    }

    /**
     * Method to select a new status from the "Status" dropdown menu, based on the name of the option (what is visible in the dropdown).
     * @param newStatusName: String name of the new status.
     */
    public void changeStatusByStatusName(String newStatusName) throws InterruptedException {
        // open the options menu to make it interacteable:
        statusSelect.click();
        // find by option name:
        List <WebElement> optionsList = driver.findElements(By.tagName("option"));
        for (WebElement listItem : optionsList) {
            String itemInnerHTML = listItem.getText(); // getAttribute("value");
            // System.out.println("Checking dropdown list item: " + itemInnerHTML);
            if (itemInnerHTML.equals(newStatusName)) {
                // System.out.println("Found item " + newStatusName + " in dropdown list item: " + itemInnerHTML);
                // String cssSelector = String.format("option[value='%s']", newStatusValue);
                // WebElement selectField = statusSelect.findElement(By.cssSelector(cssSelector)); //"option[value='4']"));
                listItem.click();
                Thread.sleep(1000);
                break;
            }
        }
        return;
    }

    public void inputBirthday(String birthdayString) { // Date birthdate, Locale locale)
        // // Create formatted String for system locale from birthdate Date object:
        // DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT, locale);
        // String birthdayString = format.format(birthdate);
        // // System.out.println("Birthday input string for system locale: " + birthdayString);

        // Edit birthday:
        birthdayInputField.click();
        birthdayInputField.clear();
        birthdayInputField.sendKeys(birthdayString);
        return;
    }


    public void saveChanges() {
        saveButton.click();
        return;
    }


    public void deleteDataset() {
        deleteButton.click();
        return;
    }


    // readout methods (used to obtain current values for assertions):
    public String getFirstNameFromInputField() {
        String innerHTML = firstNameInputField.getAttribute("value");
        return innerHTML;
    }

    public String getLastNameFromInputField() {
        String innerHTML = lastNameInputField.getAttribute("value");
        return innerHTML;
    }
   
    public String getEmailFromInputField() {
        String innerHTML = emailInputField.getAttribute("value");
        return innerHTML;
    }

    public String getBirthdayFromInputField() {
        String innerHTML = birthdayInputField.getAttribute("value");
        return innerHTML;
    }

    public String getStatusFromDropdown() {
        List <WebElement> optionsList = driver.findElements(By.tagName("option"));
        String innerHTML = "";
        for (WebElement listItem : optionsList) {
            if (listItem.isSelected()) {
                innerHTML = listItem.getText();
                break;
            }
        }
        return innerHTML;
    }

    // public String oldGetStatusFromDropdown() {
    //     // find the option with value "1" (this is the currently active option):
    //     WebElement activeOption = statusSelect.findElement(By.cssSelector("option[value='1']"));
    //     String innerHTML = activeOption.getText();
    //     return innerHTML;
    // }
    
}