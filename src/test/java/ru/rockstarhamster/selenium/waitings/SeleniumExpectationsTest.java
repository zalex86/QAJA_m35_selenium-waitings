package ru.rockstarhamster.selenium.waitings;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SeleniumExpectationsTest {
    private static final String URL = "https://www.aviasales.ru/search/LED1102MOW13021?request_source=search_form&payment_method=all";
    private static final String FILTER_BUTTON_CLASS = "filter-group";
    private static final String PREDICTION_FIRST_COLUMN_CLASS = "prediction-advice";


    private static WebDriver webDriver;

    @BeforeAll
    public static void setUpWebDriver() {
        System.setProperty("webdriver.chrome.driver", "/Users/AleksanDR/IdeaProjects/QAJA_m35_selenium-waitings/src/test/resources/chromedriver.exe");
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // неявное (implicit) ожидание - браузер ждет 10 сек.
        // implicitlyWait - глобально устанавливается и время ожидания известно, есть общие задержки загрузки страниц

    }

    @Test
    public void filters_appear() {
        // без использования ожиданий - ошибка
        webDriver.get(URL);
        assertDoesNotThrow(() -> webDriver.findElement(By.className(FILTER_BUTTON_CLASS)));
    }

    @Test
    public void prediction_bar_appears() {
        webDriver.get(URL);
        // явное ожидание - работает в контексте опред.теста, нужно проврить время загрузки опред.эл-та
        // timeoutException - если через n сек по требованию элементр на странице не загрузился
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60)); // явное ожидание (explicit), 60 - максимальное кол-во сек ожидания
        assertDoesNotThrow(() -> wait.until(ExpectedConditions.elementToBeClickable(By.className(PREDICTION_FIRST_COLUMN_CLASS))));
        // или presenceOfElementLocated() - эл-т есть в DOM
    }

    @Test
    public void dropdownItemAppears() {
        webDriver.get(URL);
        webDriver.findElement(By.className("h_uOQxZa7wOkty3_3R18")).click();
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(60));
        assertDoesNotThrow(() -> wait.until(ExpectedConditions.elementToBeClickable(By.className("mmLLaMXemwMa8J2qsgtX"))));
    }

    @AfterAll
    public static void finalizeWebDriver() {
        webDriver.quit();
    }
}