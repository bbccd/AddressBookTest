package com.haeger.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddressBookSearchResultView {

    WebDriver driver;

    public AddressBookSearchResultView (WebDriver driver) {
        this.driver = driver;
        //Initialise Elements
        // PageFactory.initElements(driver, this);
    }

    public void login(String username, String pwd) {
//        email.sendKeys(username);
//        password.sendKeys(pwd);
//        submit.click();
//        return PageFactory.initElements(driver,
//                AllPostsPage.class);
    }

    
}