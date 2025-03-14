package org.example.steps;

import enums.SortType;
import enums.SubCategory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.ru.*;
import org.example.pages.AvitoMainPage;
import org.example.pages.AvitoSearchResultsPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import java.time.Duration;

public class AvitoSteps {

    private static final Logger logger = LoggerFactory.getLogger(AvitoSteps.class);
    private WebDriver driver;
    private AvitoMainPage mainPage;
    private AvitoSearchResultsPage searchResultsPage;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        mainPage = new AvitoMainPage(driver);
        searchResultsPage = new AvitoSearchResultsPage(driver);
    }

    @ParameterType(".*")
    public SubCategory subCategory(String subcategory) {
        for (SubCategory  c : SubCategory.values()) {
            if (c.getDisplayName().equals(subcategory)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Enum for " + subcategory + " not found");
    }

    @ParameterType(".*")
    public SortType sortType(String sortType) {
        for (SortType c : SortType.values()) {
            if (c.getDisplayName().equals(sortType)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Enum for " + sortType + " not found");
    }

   @Дано("открыт ресурс авито")
    public void openAvito() {
        logger.info("Открываю Avito");
        mainPage.open();
        Assert.assertEquals(driver.getTitle(),"Авито: недвижимость, транспорт, работа, услуги, вещи","Страница Avito не открылась");
    }

    @Когда("я выбираю категорию {string} и подкатегорию {subCategory}")
    public void selectCategory(String category, SubCategory subcategory) {
        logger.info("Выбираю категорию '{}' и подкатегорию '{}'", category, subcategory.getDisplayName());
        mainPage.selectCategory(subcategory);
    }

    @И("я ввожу в поле поиска {string}")
    public void searchForItem(String query) {
        logger.info("Ввожу в поле 'Поиск по объявлениям' значение '{}'", query);
        mainPage.searchFor(query);
    }

    @И("я кликаю по выпадающему списку региона")
    public void openCity() {
        logger.info("Нажимаю на поле 'Во всех регионах'");
        mainPage.openCitySelection();
    }

    @И("я ввожу в поле регион {string}")
    public void changeCity(String cityName) {
        logger.info("Изменяю город на '{}'", cityName);
        mainPage.setCity(cityName);
    }

    @И("я активирую чекбокс с Авито Доставкой")
    public void activateDeliveryCheckbox() {
        logger.info("Активирую чекбокс 'С Авито Доставкой'");
        searchResultsPage.activateDeliveryCheckbox();
    }

    @И("я выбираю в выпадающем списке значение {sortType}")
    public void sortBy(SortType sortType) {
        logger.info("Выбираю сортировку '{}'", sortType.getDisplayName());
        searchResultsPage.sortBy(sortType);
    }

    @Тогда("открылась страница результатов по запросам {string}, {string}, {string}")
    public void verifyResultsPage(String query, String city, String checkbox) {
        logger.info("Проверяю, что открыта страница результатов по запросам '{}', '{}', '{}'", query, city, checkbox);
        Assert.assertTrue(searchResultsPage.getPageTitle().contains(query) &&
                        searchResultsPage.getPageTitle().contains(city) &&
                        searchResultsPage.getPageTitle().contains(checkbox),
                "Не все фильтры применены корректно");
    }

    @И("в консоль выведено значение названия и цены {int} первых товаров")
    public void printTopThreePrinters(int count) {
        logger.info("Вывожу в консоль название и стоимость {} самых дорогих принтеров", count);
        searchResultsPage.printTopThreePrinters(count);
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
