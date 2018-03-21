package com.buggy.screen;

import com.buggy.config.WebDriverConfig;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginScreen extends BaseScreen
{
    private final WebDriverConfig config;

    @FindBy(xpath = "//*[text()='Please login']")
    private WebElement screenTitle;

    @FindBy(xpath = "//*[@id=\"email\"]")
    private WebElement emailField;

    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement passwordField;

    @FindBy(xpath = "//*[text()='Sign In!']")
    private WebElement signInButton;

    public LoginScreen(final WebDriverConfig config)
    {
        this.config = config;
        PageFactory.initElements(config.getDriver(), this);
    }

    public DashboardScreen login(final String loginEmail, final String password)
    {
        config.typeText(emailField, loginEmail);
        config.typeText(passwordField, password);
        config.clickElement(signInButton);

        return new DashboardScreen(config);
    }

    public void openLoginScreen(final String siteUrl)
    {
        config.navigateTo(siteUrl);
        waitForPageReloads(config.getDriver());
    }

}
