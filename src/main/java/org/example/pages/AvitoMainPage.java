package org.example.pages;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AvitoMainPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Actions actions;

    public AvitoMainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.actions = new Actions(driver);
    }

    public void open() {
        driver.get("https://www.avito.ru/");
    }

    public void selectCategory(String mainCategory, String subCategory) {
        WebElement categoryDropdown = driver.findElement(By.cssSelector("button[data-marker='top-rubricator/all-categories']"));
        categoryDropdown.click();

        WebElement  mainCat = driver.findElement(By.xpath("//p[text()='" + mainCategory + "']"));
        actions.moveToElement(mainCat).perform();

        WebElement subCat = driver.findElement(By.cssSelector("a[data-name='" + subCategory + "']"));
        subCat.click();
    }

    public void searchFor(String query) {
        WebElement searchInput = driver.findElement(By.cssSelector("input[data-marker='search-form/suggest/input']"));
        searchInput.sendKeys(query);
        searchInput.sendKeys(Keys.ENTER);
    }

    public void openCitySelection() {
        WebElement city = driver.findElement(By.cssSelector("div[data-marker='search-form/change-location']"));
        city.click();
    }

    public void setCity(String cityName) {
        WebElement cityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[data-marker='popup-location/region/search-input']")));
        cityInput.click();
        cityInput.clear();
        cityInput.sendKeys(cityName);

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.xpath("//div[@role='listbox']/button[contains(@data-marker, 'popup-location/region/custom-option')]"), 1));

        WebElement firstCityOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@role='listbox']/button[contains(@data-marker, 'popup-location/region/custom-option')][1]")));
        firstCityOption.click();

        WebElement showButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[data-marker='popup-location/save-button']")));
        showButton.click();
    }
}
