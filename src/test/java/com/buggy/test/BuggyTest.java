package com.buggy.test;

import com.buggy.base.Parameters;
import com.buggy.config.WebDriverConfig;
import com.buggy.screen.DashboardScreen;
import com.buggy.screen.LoginScreen;
import com.buggy.screen.WelcomeScreen;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testcontainers.containers.BrowserWebDriverContainer;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.testcontainers.containers.BrowserWebDriverContainer.VncRecordingMode.RECORD_ALL;

public class BuggyTest
{
    @Rule
    public BrowserWebDriverContainer chrome =
            new BrowserWebDriverContainer()
                    .withDesiredCapabilities(DesiredCapabilities.chrome())
                    .withRecordingMode(RECORD_ALL, new File("build"));

    private LoginScreen loginScreen;

    private DashboardScreen dashboardScreen;

    private WelcomeScreen welcomeScreen;

    private String timeStamp;

    @Before
    public void setUp()
    {
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(
                Calendar.getInstance().getTime());

        final WebDriverConfig config = new WebDriverConfig(chrome.getWebDriver());
        loginScreen = new LoginScreen(config);
        dashboardScreen = new DashboardScreen(config);
        welcomeScreen = new WelcomeScreen(config);

        loginScreen.openLoginScreen(Parameters.getSiteUrl());
        dashboardScreen = loginScreen.login(Parameters.getLoginEmail(), Parameters.getLoginPassword());
    }

    @Test
    public void counterIncrement()
    {
        final int counterValue = dashboardScreen.getCounterValue();
        dashboardScreen.incrementButtonClick();

        Assert.assertEquals(counterValue + 1, dashboardScreen.getCounterValue());
    }

    @Test
    public void counterIncrement_SaveResult()
    {
        dashboardScreen.incrementButtonClick();
        final int counterValue = dashboardScreen.getCounterValue();
        dashboardScreen.logout();
        welcomeScreen.openLoginScreen();
        dashboardScreen = loginScreen.login(Parameters.getLoginEmail(), Parameters.getLoginPassword());

        Assert.assertEquals(counterValue, dashboardScreen.getCounterValue());
    }

    @Test
    public void addNewItem_ItemAdded()
    {
        final String testItemName = String.format("Test string %s", timeStamp);
        dashboardScreen.itemListAddNewElement(testItemName);

        Assert.assertTrue(dashboardScreen.isItemFound(testItemName));
    }

    @Test
    public void addNewItem_SaveResult()
    {
        final String testItemName = String.format("Test string %s", timeStamp);
        dashboardScreen.itemListAddNewElement(testItemName);
        dashboardScreen.logout();
        welcomeScreen.openLoginScreen();
        dashboardScreen = loginScreen.login(Parameters.getLoginEmail(), Parameters.getLoginPassword());

        Assert.assertTrue(dashboardScreen.isItemFound(testItemName));
    }

    @Test
    public void addNewItem_EmptyTitle()
    {
        final String testItemName = "";
        dashboardScreen.itemListAddNewElement(testItemName);

        Assert.assertTrue(dashboardScreen.isValidationMessageShown("Field must be between 3 and 40 characters long."));
    }

    @Test
    public void addNewItem_TooLongName()
    {
        final String testItemName = String.format(
                "To long test string to get text field name validation error: Test string %s", timeStamp);
        dashboardScreen.itemListAddNewElement(testItemName);

        Assert.assertTrue(dashboardScreen.isValidationMessageShown("Field must be between 3 and 40 characters long."));
    }

    @Test
    public void addItems_MaxValue()
    {
        do
        {
            final String testItemName = String.format("Test string %s", timeStamp);
            dashboardScreen.itemListAddNewElement(testItemName);
        }
        while (!dashboardScreen.checkItemsMaxValueError("Cannot add more than 10 items"));

        Assert.assertEquals(10, dashboardScreen.getItemsListSize());

        dashboardScreen.removeAllItemsFromList();
    }

    @Test
    public void removeItemFromList()
    {
        final String testItemName = String.format("Test string %s", timeStamp);
        dashboardScreen.itemListAddNewElement(testItemName);
        dashboardScreen.removeItemFromList(testItemName);

        Assert.assertFalse(dashboardScreen.isItemFound(testItemName));
    }

    @Test
    public void removeItemFromList_SaveResult()
    {
        final String testItemName = String.format("Test string %s", timeStamp);
        dashboardScreen.itemListAddNewElement(testItemName);
        dashboardScreen.removeItemFromList(testItemName);

        dashboardScreen.logout();
        welcomeScreen.openLoginScreen();
        dashboardScreen = loginScreen.login(Parameters.getLoginEmail(), Parameters.getLoginPassword());

        Assert.assertFalse(dashboardScreen.isItemFound(testItemName));
    }
}
