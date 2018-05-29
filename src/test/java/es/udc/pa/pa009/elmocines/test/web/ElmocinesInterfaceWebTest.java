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

		executor.executeScript("document.getElementById('authentication_link').click();");

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("loginName")));

		WebElement loginName = driver.findElement(By.id("loginName"));
		loginName.clear();
		loginName.sendKeys(TEST_USER_LOGIN);

		WebElement password = driver.findElement(By.id("password"));
		password.clear();
		password.sendKeys(TEST_USER_PASSWORD);

		driver.findElement(By.id("submit")).click();

		wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("dropdown-toggle")));

		assertTrue(driver.findElement(By.className("dropdown-toggle")).getText().contains(TEST_USER_NAME));
	}

}
