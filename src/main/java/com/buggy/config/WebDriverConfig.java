package com.buggy.config;

import com.buggy.services.Utils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class WebDriverConfig
{
    private static final Logger LOGGER = LoggerFactory.getLogger(WebDriverConfig.class);

    private final WebDriver driver;

    public WebDriverConfig(final WebDriver driver)
    {
        this.driver = driver;
        driver.manage().timeouts().pageLoadTimeout(Utils.SCREEN_LOAD_TIMEOUT, TimeUnit.SECONDS);
    }

    public void close()
    {
        LOGGER.info("Close WebDriver");
        driver.close();
    }

    public void navigateTo(final String url)
    {
        LOGGER.info("Navigate to '{}'", url);
        driver.navigate().to(url);
    }

    public void clickElement(final WebElement element)
    {
        LOGGER.info("Click on the '{}'", element.getText());
        element.click();
    }

    public void typeText(final WebElement element, final String text)
    {
        LOGGER.info("Type: '{}'", text);
        element.sendKeys(text);
    }

    public WebDriver getDriver()
    {
        return driver;
    }
}
