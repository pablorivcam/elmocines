package es.udc.pa.pa009.elmocines.test.web;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElmocinesIT {

	public static final String TEST_USER_NAME="test_user";
	public static final String TEST_USER_PASSWORD="testpass";
	
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
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.id("loginName")));

        driver.findElement(By.id("loginName")).clear();
        executor.executeScript(
                "document.getElementById('loginName').value='"+TEST_USER_NAME+";");

        driver.findElement(By.id("password")).clear();
        executor.executeScript(
                "document.getElementById('password').value='"+TEST_USER_PASSWORD+"';");

        driver.findElement(By.id("submit")).click();

        wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.className("dropdown-toggle")));

        assertTrue(driver.findElement(By.className("dropdown-toggle")).getText()
                .contains(TEST_USER_NAME));
    }

}
