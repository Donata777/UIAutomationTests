package org.example.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class AvitoSearchResultsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    public AvitoSearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
    }

    public void activateDeliveryCheckbox() {
        WebElement randomField = driver.findElement(By.cssSelector("input[data-marker='params[149569]/input']"));
        actions.moveToElement(randomField).perform();

        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[data-marker='d/checkbox/1']")));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        driver.findElement(By.cssSelector("button[data-marker='search-filters/submit-button']")).click();
    }


    public void sortByPriceDescending() {
        WebElement sortDropdown = driver.findElement(By.xpath("(//div[contains(@class, 'sort-sort_wrapper')]//div[@aria-haspopup='true'])[1]"));
        sortDropdown.click();

        WebElement sortByPriceDesc = driver.findElement(By.xpath("//div[contains(text(),'Дороже')]"));
        sortByPriceDesc.click();
    }

    public void printTopThreePrinters() {
        List<WebElement> printerNames = driver.findElements(By.xpath("//div[@data-marker='catalog-serp']/div[@data-marker='item']//a[@data-marker='item-title']"));
        List<WebElement> printerPrices = driver.findElements(By.xpath("//div[@data-marker='catalog-serp']/div[@data-marker='item']//p[@data-marker='item-price']"));

        for (int i = 0; i < 3; i++) {
            String name = printerNames.get(i).getText();
            String price = printerPrices.get(i).getText();
            System.out.println((i + 1) + ". " + name + " - " + price);
        }
    }
}
