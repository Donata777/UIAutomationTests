package org.example.tests;

import org.example.pages.AvitoMainPage;
import org.example.pages.AvitoSearchResultsPage;
import org.openqa.selenium.WebDriver;
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
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        mainPage = new AvitoMainPage(driver);
        searchResultsPage = new AvitoSearchResultsPage(driver);
    }

    @Test
    public void avitoTestFlow() {
        openAvito();
        selectCategory();
        searchForItem();
        openCity();
        changeCity();
        activateDeliveryCheckbox();
        sortByPriceDescending();
        printTopThreePrinters();
    }

    public void openAvito() {
        logger.info("Открываю Avito");
        mainPage.open();
        Assert.assertEquals(driver.getTitle(),"Авито: недвижимость, транспорт, работа, услуги, вещи","Страница Avito не открылась");
    }

    public void selectCategory() {
        logger.info("Выбираю категорию 'Электроника' и подкатегорию 'Оргтехника и расходники'");
        mainPage.selectCategory();
        Assert.assertTrue(searchResultsPage.getPageTitle().contains("оргтехники и расходных материалов"), "Не выполнен переход на страницу с оргтехникой и расходниками");
    }

    public void searchForItem() {
        logger.info("Ввожу в поле 'Поиск по объявлениям' значение 'Принтер'");
        mainPage.searchFor("Принтер");
    }

    public void openCity() {
        logger.info("Нажимаю на поле 'Во всех регионах'");
        mainPage.openCitySelection();
        Assert.assertTrue(mainPage.isCitySelectionModalDisplayed(), "Модальное окно для выбора города не отображается");
    }

    public void changeCity() {
        logger.info("Изменяю город на Владивосток");
        mainPage.setCity("Владивосток");
    }

    public void activateDeliveryCheckbox() {
        logger.info("Активирую чекбокс 'С Авито Доставкой'");
        searchResultsPage.activateDeliveryCheckbox();
    }

    public void sortByPriceDescending() {
        logger.info("Сортирую по убыванию цены");
        searchResultsPage.sortByPriceDescending();
        Assert.assertEquals(searchResultsPage.getSortTitle(), "Дороже", "Сортировка по убыванию цены не применилась");
    }

    public void printTopThreePrinters() {
        logger.info("Вывожу в консоль название и стоимость 3х самых дорогих принтеров");
        searchResultsPage.printTopThreePrinters();
        Assert.assertTrue(searchResultsPage.getPageTitle().contains("Принтер") &&
                        searchResultsPage.getPageTitle().contains("Владивостоке") &&
                        searchResultsPage.getPageTitle().contains("с доставкой"),
                "Не все фильтры применены корректно");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
