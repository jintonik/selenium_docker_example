package com.buggy.screen;

import com.buggy.config.WebDriverConfig;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class WelcomeScreen
{
    private final WebDriverConfig config;

    @FindBy(xpath = "//*[text()='Добрый день!']")
    private WebElement welcomeText;

    @FindBy(xpath = "//*[@id='bs-example-navbar-collapse-1']//a[@href='/login']")
    private WebElement loginOrRegisterLink;

    public WelcomeScreen(final WebDriverConfig config)
    {
        this.config = config;
        PageFactory.initElements(config.getDriver(), this);
    }

    public void openLoginScreen()
    {
        config.clickElement(loginOrRegisterLink);
    }

}
