package org.example.tests;

import org.example.pages.AvitoMainPage;
import org.example.pages.AvitoSearchResultsPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class AvitoTest {

    private static final Logger logger = LoggerFactory.getLogger(AvitoTest.class);
    private WebDriver driver;
    private AvitoMainPage mainPage;
    private AvitoSearchResultsPage searchResultsPage;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        mainPage = new AvitoMainPage(driver);
        searchResultsPage = new AvitoSearchResultsPage(driver);
    }

    @Test
    public void openAvito() {
        logger.info("Открываю Avito");
        mainPage.open();
        Assert.assertEquals(driver.getTitle(),"Авито: недвижимость, транспорт, работа, услуги, вещи","Страница Avito не открылась");
    }

    @Test(dependsOnMethods = {"openAvito"})
    public void selectCategory() {
        logger.info("Выбираю категорию 'Электроника' и подкатегорию 'Оргтехника и расходники'");
        mainPage.selectCategory("Электроника", "Оргтехника и расходники");
        assertPageTitleContains("оргтехники и расходных материалов", "Не выполнен переход на страницу с оргтехникой и расходниками");
    }

    @Test(dependsOnMethods = {"selectCategory"})
    public void searchForItem() {
        logger.info("Ввожу в поле 'Поиск по объявлениям' значение 'Принтер'");
        mainPage.searchFor("Принтер");
        assertPageTitleContains("Принтер", "Поиск по значению 'Принтер' не выполнен");
    }

    @Test(dependsOnMethods = {"searchForItem"})
    public void openCity() {
        logger.info("Нажимаю на поле 'Во всех регионах'");
        mainPage.openCitySelection();
        WebElement modal = driver.findElement(By.cssSelector("div[data-marker='popup-location/popup']"));
        Assert.assertTrue(modal.isDisplayed(), "Модальное окно для выбора города не отображается");
    }

    @Test(dependsOnMethods = {"openCity"})
    public void changeCity() {
        logger.info("Изменяю город на Владивосток");
        mainPage.setCity("Владивосток");
        WebElement cityElement = driver.findElement(By.cssSelector("span.buyer-location-nev1ty"));
        Assert.assertEquals(cityElement.getText(), "Владивосток", "Город не изменился");
    }

    @Test(dependsOnMethods = {"changeCity"})
    public void activateDeliveryCheckbox() {
        logger.info("Активирую чекбокс 'С Авито Доставкой'");
        searchResultsPage.activateDeliveryCheckbox();
        assertPageTitleContains("с доставкой", "Чекбокс 'С Авито Доставкой' не активирован");
    }

    @Test(dependsOnMethods = {"activateDeliveryCheckbox"})
    public void sortByPriceDescending() {
        logger.info("Сортирую по убыванию цены");
        searchResultsPage.sortByPriceDescending();
        WebElement sortTitle = driver.findElement(By.cssSelector("span[data-marker='sort/title']"));
        Assert.assertEquals(sortTitle.getText(),"Дороже","Сортировка по убыванию цены не применилась");
    }

    @Test(dependsOnMethods = {"sortByPriceDescending"})
    public void printTopThreePrinters() {
        logger.info("Вывожу в консоль название и стоимость 3х самых дорогих принтеров");
        searchResultsPage.printTopThreePrinters();
    }

    private void assertPageTitleContains(String expectedText, String errorMessage) {
        WebElement pageTitle = driver.findElement(By.cssSelector("h1[data-marker='page-title/text']"));
        String titleText = pageTitle.getText();
        Assert.assertTrue(titleText.contains(expectedText), errorMessage);
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
