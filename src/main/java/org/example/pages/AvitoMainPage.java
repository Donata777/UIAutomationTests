package org.example.pages;


import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AvitoMainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    @FindBy(xpath = "//button[@data-marker='top-rubricator/all-categories']")
    private WebElement categoryDropdown;

    @FindBy(xpath = "//p[text()='Электроника']")
    private WebElement mainCategory;

    @FindBy(xpath = "//a[@data-name='Оргтехника и расходники']")
    private WebElement subCategory;

    @FindBy(xpath = "//input[@data-marker='search-form/suggest/input']")
    private WebElement searchInput;

    @FindBy(xpath = "//div[@data-marker='search-form/change-location']")
    private WebElement cityChangeButton;

    @FindBy(xpath = "//div[@data-marker='popup-location/popup']")
    private WebElement citySelectionModal;

    @FindBy(xpath = "//input[@data-marker='popup-location/region/search-input']")
    private WebElement cityInput;
    
    private final By cityListbox = By.xpath("//div[@role='listbox']/button[contains(@data-marker, 'popup-location/region/custom-option')]");

    @FindBy(xpath = "//div[@role='listbox']/button[contains(@data-marker, 'popup-location/region/custom-option')][1]")
    private WebElement firstCityOption;

    @FindBy(xpath = "//button[@data-marker='popup-location/save-button']")
    private WebElement showButton;

    @FindBy(xpath = "//span[@class='buyer-location-nev1ty']")
    private WebElement selectedCity;


    public AvitoMainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public void open() {
        driver.get("https://www.avito.ru/");
    }

    public void selectCategory() {
        categoryDropdown.click();
        wait.until(ExpectedConditions.visibilityOf(mainCategory));
        actions.moveToElement(mainCategory).perform();
        subCategory.click();
    }

    public void searchFor(String query) {
        searchInput.sendKeys(query);
        searchInput.sendKeys(Keys.ENTER);
    }

    public void openCitySelection() {
        cityChangeButton.click();
    }

    public boolean isCitySelectionModalDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOf(citySelectionModal)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void setCity(String cityName) {
        wait.until(ExpectedConditions.visibilityOf(cityInput));
        cityInput.click();
        cityInput.clear();
        cityInput.sendKeys(cityName);
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(cityListbox, 1));
        wait.until(ExpectedConditions.elementToBeClickable(firstCityOption)).click();
        wait.until(ExpectedConditions.attributeToBe(cityInput, "value", cityName));
        wait.until(ExpectedConditions.elementToBeClickable(showButton)).click();
        wait.until(ExpectedConditions.textToBePresentInElement(selectedCity, cityName));
    }
}