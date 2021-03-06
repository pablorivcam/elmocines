package es.udc.pa.pa009.elmocines.test.web;

import static es.udc.pa.pa009.elmocines.model.util.GlobalNames.SPRING_CONFIG_FILE;
import static es.udc.pa.pa009.elmocines.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class ElmocinesInterfaceWebTest {

	public static final String TEST_USER_LOGIN = "client";
	public static final String TEST_USER_PASSWORD = "admin";
	public static final String TEST_USER_NAME = "Cliente";

	public static final String TEST_USER_LOGIN2 = "testClient";
	public static final String TEST_USER_PASSWORD2 = "testPass";
	public static final String TEST_USER_NAME2 = "Test";
	public static final String TEST_EMAIL_NAME2 = "testclient@udc.es";
	public static final String TEST_USER_LAST_NAME2 = "Cliente";

	public static final String TEST_CREDIT_CARD_NUMBER = "1234567";
	public static final String TEST_CREDIT_CARD_EXPIRATION_DATE = "Jun 6, 2025";
	public static final String TEST_LOCATIONS_AMMOUNT = "2";

	private WebDriver driver;
	private String baseUrl;
	private JavascriptExecutor executor;

	@Before
	public void openFirefox() {
		System.setProperty("webdriver.gecko.driver", "geckodriver");

		driver = new FirefoxDriver();

		driver.manage().window().maximize();
		baseUrl = "http://localhost:9090/elmocines/";
		executor = (JavascriptExecutor) driver;
	}

	@After
	public void closeFirefox() {
		driver.quit();
	}

	@Test
	public void authenticationTest() {
		driver.get(baseUrl);

		// Página principal
		executor.executeScript("document.getElementById('authentication_link').click();");

		// Página de Login
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginName")));

		WebElement loginName = driver.findElement(By.id("loginName"));
		loginName.clear();
		loginName.sendKeys(TEST_USER_LOGIN);

		WebElement password = driver.findElement(By.id("password"));
		password.clear();
		password.sendKeys(TEST_USER_PASSWORD);

		driver.findElement(By.id("submit")).click();

		// Página Principal
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dropdown-toggle")));

		assertTrue(driver.findElement(By.className("dropdown-toggle")).getText().contains(TEST_USER_NAME));
	}

	@Test
	public void authenticationWithRegisterTest() {
		driver.get(baseUrl);

		// Página principal
		executor.executeScript("document.getElementById('authentication_link').click();");

		// Página de Login
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginName")));

		executor.executeScript("document.getElementById('registerLink').click();");

		// Página de Registro
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

		WebElement loginName = driver.findElement(By.id("loginName"));
		loginName.clear();
		loginName.sendKeys(TEST_USER_LOGIN2);

		WebElement password = driver.findElement(By.id("password"));
		password.clear();
		password.sendKeys(TEST_USER_PASSWORD2);

		WebElement retypePassword = driver.findElement(By.id("retypePassword"));
		retypePassword.clear();
		retypePassword.sendKeys(TEST_USER_PASSWORD2);

		WebElement firstName = driver.findElement(By.id("firstName"));
		firstName.clear();
		firstName.sendKeys(TEST_USER_NAME2);

		WebElement lastName = driver.findElement(By.id("lastName"));
		lastName.clear();
		lastName.sendKeys(TEST_USER_LAST_NAME2);

		WebElement email = driver.findElement(By.id("email"));
		email.clear();
		email.sendKeys(TEST_EMAIL_NAME2);

		driver.findElement(By.id("submit")).click();

		// Página Principal
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dropdown-toggle")));

		assertTrue(driver.findElement(By.className("dropdown-toggle")).getText().contains(TEST_USER_NAME2));
	}

	@Test
	public void purchaseTicketTest() {
		driver.get(baseUrl);

		// Página principal
		executor.executeScript("document.getElementById('searchBillboardBtn').click();");

		// Página de cartelera
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("favourite_cinema_link")));

		executor.executeScript("document.getElementById('favourite_cinema_link').click();");

		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("favourite_cinema_setted")));

		String cinemaName = driver.findElement(By.id("cinema_billboard_name")).getText();

		executor.executeScript("document.getElementById('index_page').click();");

		// Página principal
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("cinema_name")));

		assertTrue(driver.findElement(By.id("cinema_name")).getText().contains(cinemaName));

		executor.executeScript("document.getElementById('sessionLink1').click();");

		// Página de sesión
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("lenght_label")));

		executor.executeScript("document.getElementById('authentication_link_from_session').click();");

		// Página de login
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginName")));

		WebElement loginName = driver.findElement(By.id("loginName"));
		loginName.clear();
		loginName.sendKeys(TEST_USER_LOGIN);

		WebElement password = driver.findElement(By.id("password"));
		password.clear();
		password.sendKeys(TEST_USER_PASSWORD);

		driver.findElement(By.id("submit")).click();

		// Página de sesión
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("creditCardNumber")));

		String movie_title = driver.findElement(By.id("movie_title")).getText();

		WebElement creditCardNumber = driver.findElement(By.id("creditCardNumber"));
		creditCardNumber.clear();
		creditCardNumber.sendKeys(TEST_CREDIT_CARD_NUMBER);

		WebElement cardExpirationdDate = driver.findElement(By.id("cardExpiredDate"));
		cardExpirationdDate.clear();
		cardExpirationdDate.sendKeys(TEST_CREDIT_CARD_EXPIRATION_DATE);

		WebElement locationsAmount = driver.findElement(By.id("locationsAmount"));
		locationsAmount.clear();
		locationsAmount.sendKeys(TEST_LOCATIONS_AMMOUNT);

		driver.findElement(By.id("submit_purchase_button")).click();

		// Página de compra
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("purchase_info")));

		executor.executeScript("document.getElementById('dropdown_link').click();");
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user_purchases_link")));
		executor.executeScript("document.getElementById('user_purchases_link').click();");

		// Página de mis compras
		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("next_link")));
		executor.executeScript("document.getElementById('next_link').click();");

		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("movie_title11")));
		executor.executeScript("document.getElementById('next_link').click();");

		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("movie_title22")));

		assertTrue(driver.findElement(By.id("movie_title22")).getText().contains(movie_title));

	}

}
