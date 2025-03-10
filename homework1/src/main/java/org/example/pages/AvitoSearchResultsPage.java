package org.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AvitoSearchResultsPage {
    private final WebDriverWait wait;
    private final Actions actions;

    @FindBy(xpath = "//span[text()='Что-то важное для вас']")
    private WebElement randomField;

    @FindBy(xpath = "//label[@data-marker='d/checkbox/1']")
    private WebElement deliveryCheckbox;

    @FindBy(xpath = "//button[@data-marker='search-filters/submit-button']")
    private WebElement submitButton;

    @FindBy(xpath = "(//div[contains(@class, 'sort-sort_wrapper')]//div[@aria-haspopup='true'])[1]")
    private WebElement sortDropdown;

    @FindBy(xpath = "//div[contains(text(),'Дороже')]")
    private WebElement sortByPriceDesc;

    @FindBy(xpath = "//span[@data-marker='sort/title']")
    private WebElement sortTitle;

    @FindBy(xpath = "//div[@data-marker='catalog-serp']/div[@data-marker='item']//a[@data-marker='item-title']")
    private List<WebElement> printerNames;

    @FindBy(xpath = "//div[@data-marker='catalog-serp']/div[@data-marker='item']//p[@data-marker='item-price']")
    private List<WebElement> printerPrices;

    @FindBy(xpath = "//h1[@data-marker='page-title/text']")
    private WebElement pageTitle;

    public AvitoSearchResultsPage(WebDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public void activateDeliveryCheckbox() {
        actions.moveToElement(randomField).perform();
        wait.until(ExpectedConditions.elementToBeClickable(deliveryCheckbox));

        if (!deliveryCheckbox.isSelected()) {
            deliveryCheckbox.click();
        }

        submitButton.click();
    }

    public void sortByPriceDescending() {
        sortDropdown.click();
        sortByPriceDesc.click();
    }

    public String getSortTitle() {
        return wait.until(ExpectedConditions.visibilityOf(sortTitle)).getText();
    }

    public void printTopThreePrinters() {
        wait.until(ExpectedConditions.visibilityOfAllElements(printerNames));
        wait.until(ExpectedConditions.visibilityOfAllElements(printerPrices));

        for (int i = 0; i < 3; i++) {
            String name = printerNames.get(i).getText();
            String price = printerPrices.get(i).getText();
            System.out.println((i + 1) + ". " + name + " - " + price);
        }
    }

    public String getPageTitle() {
        return wait.until(ExpectedConditions.visibilityOf(pageTitle)).getText();
    }
}
