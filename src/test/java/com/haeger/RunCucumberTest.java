package com.haeger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.net.URL;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("com.haeger")
public class RunCucumberTest {
//    WebDriver driver;
//
//    @BeforeEach
//    public void beforeMethod() throws IOException {
//        nodeURL = "http://192.168.0.4:4444";
//        ChromeOptions chromeOptions = new ChromeOptions();
////        DesiredCapabilities capability = DesiredCapabilities.chrome();
////        capability.setBrowserName("chrome");
////        capability.setPlatform(Platform.MAC);
//        driver = new RemoteWebDriver(new URL(nodeURL), chromeOptions);
//    }
//
//    @AfterEach
//    public void afterMethod() {
//        driver.quit();
//    }
}
