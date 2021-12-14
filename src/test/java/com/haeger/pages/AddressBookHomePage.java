package com.haeger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddressBookHomePage {

    WebDriver driver;

    //Page URL
    private static String PAGE_URL="http://192.168.0.10:8080/";

    @FindBy(id = "tfFilter")
    WebElement searchField;

    // @FindBy(xpath = "/html/body/div[1]/div[3]/form/div[1]/div[1]/div[2]/div[2]/div[5]/center/input[2]")
    // WebElement searchButton;

    public AddressBookHomePage (WebDriver driver) {
        this.driver = driver;
        driver.get(PAGE_URL);
    }

    public void search(String searchTerm) {
        searchField.sendKeys(searchTerm);
        // searchField.sendKeys(" ");
        // WebElement insetSearchButton = driver.findElement(By.xpath("/html/body/div[1]/div[3]/form/div[1]/div[1]/div[2]/div[2]/div[5]/center/input[1]"));
        // WebElement searchButtonNow = driver.findElement(By.className("gNO89b"));
        // searchButtonNow.click();
        return; // PageFactory.initElements(driver,
                // GoogleSearchResultPage.class);
    }

    // public void closeModalBeforeYouContinueDialog() {
    //     //*[@id="L2AGLb"]/div
        
    //     WebElement closeModalButton = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[3]/span/div/div/div/div[3]/button[2]")); 
    //     //"/html/body/div[2]/div[2]/div[3]/span/div/div/div[3]/button[2]/div"));
    //     closeModalButton.click();
    // }


}