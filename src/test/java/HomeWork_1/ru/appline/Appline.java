package HomeWork_1.ru.appline;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class Appline {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private Select selectSubdivisionDropDown;
    private String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

    @Before
    public void before() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/webdriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        wait = new WebDriverWait(driver, Duration.ofSeconds(30), Duration.ofSeconds(2));
        actions = new Actions(driver);
        String baseUrl = "http://training.appline.ru/user/login";
        driver.get(baseUrl);
    }

    @Test
    public void main() {
        //регистрация
        waitUtilElementToBeVisible(By.xpath("//h2[text()='Логин']"));
        WebElement registrationLoginField = driver.findElement(By.xpath("//input[contains(@placeholder,'Имя пользователя или Email')]"));
        WebElement registrationPasswordField = driver.findElement(By.xpath("//input[contains(@placeholder,'Пароль')]"));
        WebElement registrationEnterButton = driver.findElement(By.xpath("//button[@type='submit' and text()='Войти']"));

        registrationLoginField.clear();
        registrationLoginField.sendKeys("Taraskina Valeriya");
        registrationPasswordField.clear();
        registrationPasswordField.sendKeys("testing");
        waitUtilElementToBeClickable(registrationEnterButton);
        registrationEnterButton.click();

        //ждем пока страница прогрузиться и пропадет элемент с загрузкой
        waitUtilElementToBeInvisible(By.className("loader-mask"));

        //Проверка наличия на странице заголовка Панель быстрого запуска
        waitUtilElementToBeVisible(By.xpath("//h1[contains(@class,'oro-subtitle')]"));
        WebElement basePageTitle = driver.findElement(By.xpath("//h1[contains(@class,'oro-subtitle')]"));
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Панель быстрого запуска", basePageTitle.getText());

        //Переход на страницу Командировки
        WebElement expensesField = driver.findElement(By.xpath("//div[@id='main-menu']/ul/li/a/span[text()='Расходы']"));
        actions.moveToElement(expensesField).perform();

        waitUtilElementToBeVisible(By.xpath("//ul[contains(@class,'dropdown-menu')]/li/a/span[text()='Командировки']"));
        WebElement businessTripsField = driver.findElement(By.xpath("//ul[contains(@class,'dropdown-menu')]/li/a/span[text()='Командировки']"));
        businessTripsField.click();

        //ждем пока страница прогрузиться и пропадет элемент с загрузкой
        waitUtilElementToBeInvisible(By.className("loader-mask"));

        //Клик на кнопку Создать командировку
        waitUtilElementToBeVisible(By.xpath("//a[@title='Создать командировку']"));
        WebElement createBusinessTripButton = driver.findElement(By.xpath("//a[@title='Создать командировку']"));
        createBusinessTripButton.click();

        //Проверка наличия на странице заголовка Создать командировку
        waitUtilElementToBeVisible(By.xpath("//h1[contains(@class,'user-name')]"));
        WebElement createBusinessTripPageTitle = driver.findElement(By.xpath("//h1[contains(@class,'user-name')]"));
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Создать командировку", createBusinessTripPageTitle.getText());

        //Выбор поля: Подразделение - выбрать Отдел внутренней разработки
        WebElement subdivisionDropDown = driver.findElement(By.xpath("//div[contains(@class,'selector')]/span[text()='Выберите подразделение']/following-sibling::select"));
        selectSubdivisionDropDown = new Select(subdivisionDropDown);
        selectSubdivisionDropDown.selectByVisibleText("Отдел внутренней разработки");

        //Выбор поля: Принимающая организация
        WebElement openListField = driver.findElement(By.xpath("//label[text()='Выбрать организацию из списка']/following-sibling::div/div/a[text()='Открыть список']"));
        openListField.click();
        WebElement specifyOrganizationField = driver.findElement(By.xpath("//span[text()='Укажите организацию']"));
        specifyOrganizationField.click();
        WebElement inputOrganizationField = driver.findElement(By.xpath("//div[contains(@class,'select2-search')]/input[contains(@class,'select2-input')]"));
        inputOrganizationField.sendKeys("Академия Тестирования");
        WebElement choosedOrganizationField = driver.findElement(By.xpath("//div[contains(@class,'select2-result-label')]"));
        choosedOrganizationField.click();

        //Выбрать чекбокс "Заказ билетов"
        WebElement taskField = driver.findElement(By.xpath("//div[contains(@data-name,'field__tasks')]/div/input[@type='checkbox']/following-sibling::label[text()='Заказ билетов']"));
        taskField.click();

        //Указать город выбытия и прибытия
        WebElement departureCityField = driver.findElement(By.xpath("//input[contains(@data-name,'field__departure-city')]"));
        WebElement arrivalCityField = driver.findElement(By.xpath("//input[contains(@data-name,'field__arrival-city')]"));
        departureCityField.clear();
        departureCityField.sendKeys("Москва");
        arrivalCityField.clear();
        arrivalCityField.sendKeys("Хабаровск");

        //Выбрать дату выбытия
        WebElement departureDatePlanField = driver.findElement(By.xpath("//input[contains(@name,'date_selector_crm_business_trip_departureDatePlan')]"));
        departureDatePlanField.click();
        WebElement buttonTodayInDepartureDatePlanField = driver.findElement(By.xpath("//button[contains(@data-handler,'today')]"));
        buttonTodayInDepartureDatePlanField.click();

        //Выбрать дату прибытия
        WebElement returnDatePlanField = driver.findElement(By.xpath("//input[contains(@name,'date_selector_crm_business_trip_returnDatePlan')]"));
        returnDatePlanField.click();
        WebElement buttonTodayInReturnDatePlanField = driver.findElement(By.xpath("//button[contains(@data-handler,'today')]"));
        buttonTodayInReturnDatePlanField.click();

        //Проверка, что Подразделение соответствует заполненому
        WebElement subdivisionText = subdivisionDropDown.findElement(By.xpath("./preceding-sibling::span"));
        Assert.assertEquals("Поле Подразделение не соответствует заполненому",
                "Отдел внутренней разработки", subdivisionText.getText());

        //Проверка, что Принимающая организация соответствует заполненому
        WebElement companyText = driver.findElement(By.xpath("//span[contains(@class,'select2-chosen')]"));
        Assert.assertEquals("Поле Принимающая организация не соответствует заполненому",
                "Академия Тестирования", companyText.getText());

        //Проверка, что Чекбокс в задачах выбран
        WebElement checkBoxTask = driver.findElement(By.xpath("//input[contains(@data-name,'field__1')]"));
        Assert.assertTrue("Чекбокс должен быть выбран.", checkBoxTask.isSelected());

        //Проверка, что Город выбытия соответствует заполненому
        Assert.assertEquals("Поле Город выбытия не соответствует заполненому", "Москва", departureCityField.getAttribute("value"));
        //Проверка, что Город прибытия соответствует заполненому
        Assert.assertEquals("Поле Город прибытия не соответствует заполненому", "Хабаровск", arrivalCityField.getAttribute("value"));

        //Проверка, что Дата выбытия соответствует заполненому
        Assert.assertEquals("Поле Дата выбытия не соответствует заполненому", todayDate, departureDatePlanField.getAttribute("value"));
        //Проверка, что Дата прибытия соответствует заполненому
        Assert.assertEquals("Поле Дата прибытия не соответствует заполненому", todayDate, returnDatePlanField.getAttribute("value"));

        //Нажать "Сохранить и закрыть"
        WebElement saveAndCloseButton = driver.findElement(By.xpath("//button[contains(text(),'Сохранить и закрыть')]"));
        saveAndCloseButton.click();

        //Проверка, что сообщение об ошибке соответствует -- Список командируемых сотрудников не может быть пустым
        WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Список командируемых сотрудников не может быть пустым')]")));
        Assert.assertEquals("Сообщение об ошибке не соответствует ожидаемому.", "Список командируемых сотрудников не может быть пустым", errorMessage.getText());
    }

    @After
    public void after() {
        driver.quit();
    }

    private void waitUtilElementToBeInvisible(By locator) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    private void waitUtilElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void waitUtilElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }
}

