package com.buggy.screen;

import com.buggy.config.WebDriverConfig;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.buggy.services.Utils.SCREEN_LOAD_TIMEOUT;

public class DashboardScreen extends BaseScreen
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardScreen.class);

    private final WebDriverConfig config;

    @FindBy(xpath = "//*[text()='Counter']")
    private WebElement counterText;

    @FindBy(xpath = "//*[@id='counter_value']")
    private WebElement counterValue;

    @FindBy(xpath = "//*[text()='Increment']")
    private WebElement counterIncrementButton;

    @FindBy(xpath = "//*[@id='title']")
    private WebElement newItemTitle;

    @FindBy(xpath = "//*[@id='title']/following-sibling::p[@class='help-block']")
    private WebElement newItemTitleValidationMessage;

    @FindBy(xpath = "//*[text()='Add'] ")
    private WebElement itemListAddButton;

    @FindBy(xpath = "//*[@class='alert alert-warning']")
    private WebElement errorMessage;

    @FindBy(xpath = "//*[text()='Logout']")
    private WebElement logoutLink;

    @FindAll({@FindBy(xpath = "//*[text()='Items list']/following-sibling::ol/li")})
    private List<WebElement> itemsList;

    @FindBy(xpath = "//*[text()='Remove']")
    private WebElement removeItemLink;

    public DashboardScreen(final WebDriverConfig config)
    {
        this.config = config;
        PageFactory.initElements(config.getDriver(), this);
    }

    public void incrementButtonClick()
    {
        config.clickElement(counterIncrementButton);
        waitForPageReloads(config.getDriver(), SCREEN_LOAD_TIMEOUT);
    }

    public int getCounterValue()
    {
        return Integer.parseInt(counterValue.getText());
    }

    public void itemListAddNewElement(final String title)
    {
        config.typeText(newItemTitle, title);
        config.clickElement(itemListAddButton);
        waitForPageReloads(config.getDriver(), SCREEN_LOAD_TIMEOUT);
        newItemTitle.clear();
    }

    public boolean isItemFound(final String title)
    {
        try
        {
            LOGGER.info("Try to find item with title '{}'", title);
            getItemFromList(title);
            return true;
        }
        catch (final NoSuchElementException exc)
        {
            LOGGER.info("Item with title '{}' not found", title);
            LOGGER.info(exc.getMessage(), exc);
        }
        return false;
    }

    public boolean checkItemsMaxValueError(final String message)
    {
        try
        {
            return errorMessage.isDisplayed() && StringUtils.containsIgnoreCase(errorMessage.getText(), message);
        }
        catch (final NoSuchElementException exc)
        {
            LOGGER.info(exc.getMessage(), exc);
        }
        return false;
    }

    public boolean isValidationMessageShown(final String message)
    {
        try
        {
            return newItemTitleValidationMessage.isDisplayed() && StringUtils.equalsIgnoreCase(
                    newItemTitleValidationMessage.getText(), message);
        }
        catch (final NoSuchElementException exc)
        {
            LOGGER.info(exc.getMessage(), exc);
        }
        return false;
    }

    public void removeItemFromList(final String title)
    {
        config.clickElement(getRemoveItemLink(title));
        waitForPageReloads(config.getDriver());
    }

    public void removeAllItemsFromList()
    {
        while (itemsList.size() != 0)
        {
            config.clickElement(removeItemLink);
            waitForPageReloads(config.getDriver());
        }
    }

    public boolean dashboardOpened()
    {
        return counterText.isDisplayed();
    }

    public void logout()
    {
        config.clickElement(logoutLink);
        waitForPageReloads(config.getDriver());
    }

    public int getItemsListSize()
    {
        return itemsList.size();
    }

    private WebElement getRemoveItemLink(final String title)
    {
        return getItemFromList(title).findElement(By.linkText("Remove"));
    }

    private WebElement getItemFromList(final String title)
    {
        if (!itemsList.isEmpty())
        {
            for (final WebElement item : itemsList)
            {
                if (StringUtils.containsIgnoreCase(item.getText(), title))
                {
                    LOGGER.info("Item with title '{}' has been found", title);
                    return item;
                }
            }
            throw new NoSuchElementException(String.format("Element with title '%s' not found", title));
        }
        throw new InvalidArgumentException("Item list is empty");
    }
}
