package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class AvitoTest {

    private static final Logger logger = LoggerFactory.getLogger(AvitoTest.class);
    protected WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Test
    public void testAvito() {

        logger.info("Открываю Avito");
        driver.get("https://www.avito.ru/");

        logger.info("Выбираю категорию 'Электроника'");
        WebElement categoryDropdown = driver.findElement(By.cssSelector("button[data-marker ='top-rubricator/all-categories']"));
        categoryDropdown.click();
        WebElement electronicsCategory = driver.findElement(By.xpath("//p[text()='Электроника']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(electronicsCategory).perform();

        logger.info("Выбираю подкатегорию 'Оргтехника и расходники'");
        WebElement officeEquipment = driver.findElement(By.cssSelector("a[data-name='Оргтехника и расходники']"));
        officeEquipment.click();

        logger.info("Ввожу в поле 'Поиск по объявлениям' 'Принтер'");
        WebElement searchInput = driver.findElement(By.cssSelector("input[data-marker='search-form/suggest/input']"));
        searchInput.sendKeys("Принтер");
        searchInput.sendKeys(Keys.ENTER);

        logger.info("Изменяю город на Владивосток");
        WebElement city = driver.findElement(By.cssSelector("div[data-marker='search-form/change-location']"));
        city.click();
        WebElement cityInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[data-marker='popup-location/region/search-input']")));
        cityInput.click();
        cityInput.clear();
        cityInput.sendKeys("Владивосток");

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
                By.xpath("//div[@role='listbox']/button[contains(@data-marker, 'popup-location/region/custom-option')]"), 1));

        WebElement firstCityOption = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[@role='listbox']/button[contains(@data-marker, 'popup-location/region/custom-option')][1]")));
        firstCityOption.click();

        WebElement showButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("button[data-marker='popup-location/save-button']")));
        showButton.click();

        logger.info("Прокручиваю до чекбокса");
       WebElement randomField = driver.findElement(By.cssSelector("input[data-marker='params[149569]/input']"));
       Actions actionMove = new Actions(driver);
       actionMove.moveToElement(randomField).perform();

        logger.info("Активирую чекбокс 'С Авито Доставкой'");
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("label[data-marker='d/checkbox/1']")));

        if(!checkbox.isSelected()) {
            checkbox.click();
        }

        logger.info("Применяю изменения");
        driver.findElement(By.cssSelector("button[data-marker='search-filters/submit-button']")).click();

        logger.info("Сортирую по убыванию цены");
        WebElement sortDropdown = driver.findElement(By.xpath("(//div[contains(@class, 'sort-sort_wrapper')]//div[@aria-haspopup='true'])[1]"));
        sortDropdown.click();

        WebElement sortByPriceDesc = driver.findElement(By.xpath("//div[contains(text(),'Дороже')]"));
        sortByPriceDesc.click();

        logger.info("Вывожу в консоль название и стоимость 3х самых дорогих принтеров");
        List<WebElement> printerNames = driver.findElements(By.xpath("//div[@data-marker='catalog-serp']/div[@data-marker='item']//a[@data-marker='item-title']"));
        List<WebElement> printerPrices = driver.findElements(By.xpath("//div[@data-marker='catalog-serp']/div[@data-marker='item']//p[@data-marker='item-price']"));

        for (int i = 0; i < 3; i++) {
            String name = printerNames.get(i).getText();
            String price = printerPrices.get(i).getText();
            System.out.println((i + 1) + ". " + name + " - " + price);
        }
    }

    @AfterEach
    public void tearDown() {
            driver.quit();
    }
}
