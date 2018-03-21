package com.buggy.screen;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static com.buggy.services.Utils.SCREEN_LOAD_TIMEOUT;

public class BaseScreen
{
    public void waitForPageReloads(final WebDriver driver)
    {
        waitForPageReloads(driver, SCREEN_LOAD_TIMEOUT);
    }

    public void waitForPageReloads(final WebDriver driver, final int timeOutInSeconds)
    {
        new WebDriverWait(driver, timeOutInSeconds).until(
                webDriver -> ((JavascriptExecutor)webDriver).executeScript("return document.readyState")
                        .equals("complete"));
    }
}
