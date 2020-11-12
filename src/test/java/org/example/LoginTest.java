package org.example;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
public class LoginTest {
    public static LoginPage loginPage;
    public static ProfilePage profilePage;
    public static WebDriver driver;
    /**
     * осуществление первоначальной настройки
     */
    @BeforeClass
    public static void setup() {
        //определение пути до драйвера и его настройка
        System.setProperty("webdriver.chrome.driver", ConfProperties.getProperty("chromedriver"));
        //создание экземпляра драйвера
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        profilePage = new ProfilePage(driver);
        //окно разворачивается на полный экран
        driver.manage().window().maximize();
        //задержка на выполнение теста = 10 сек.
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        //получение ссылки на страницу входа из файла настроек
        driver.get(ConfProperties.getProperty("loginpage"));
    }
    /**
     * тестовый метод для осуществления аутентификации
     */
    @Test
    public void loginTest() {
        //получение доступа к методам класса LoginPage для взаимодействия с элементами страницы
        //значение login/password берутся из файла настроек по аналогии с chromedriver
        //и loginpage
        //вводим логин
        loginPage.inputLogin(ConfProperties.getProperty("login"));
        //вводим пароль
        loginPage.inputPasswd(ConfProperties.getProperty("password"));
        //нажимаем кнопку входа
        loginPage.clickLoginBtn();
        //получаем отображаемый логин
        String user = profilePage.getUserName();
        //и сравниваем его с логином из файла настроек
        Assert.assertEquals(ConfProperties.getProperty("login"), user);
        //осуществление выхода из аккаунта
        profilePage.entryMenu();
        profilePage.userLogout();
            //driver.quit();
    }
    /**
     * Попытка входа с некорректыми данными
     */
    @Test
    public void fakeloginTest() {
        //вводим некорректный логин
        loginPage.inputLogin(ConfProperties.getProperty("fakelogin"));
        //вводим некорректный пароль
        loginPage.inputPasswd(ConfProperties.getProperty("fakepassword"));
        //нажимаем кнопку входа
        loginPage.clickLoginBtn();
    }
    /**
     * Переход по ссылке в форму регистрации доступа в панель управления
     */
    @Test
    public void registrTest() {
        //нажимаем кнопку Зарегистрироваться
        loginPage.clickRegistrBtn();
    }
    /**
     * Закрытие окна браузера
     */
    @AfterClass
    public static void tearDown() {
        driver.quit(); }
}