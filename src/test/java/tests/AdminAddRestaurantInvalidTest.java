package tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminAddRestaurantInvalidTest {

    @Test
    public void testInvalidRestaurantAdd() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        try {
            // Set window size to ensure elements are visible
            driver.manage().window().setSize(new Dimension(1920, 1080));

            // Navigate to the page
            driver.get("http://localhost:55316/");
            Thread.sleep(2000);

            // Click on the add restaurant button (update selector based on your navigation)
            WebElement addButton = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("a[href*='/restaurants/add']")));
            addButton.click();
            Thread.sleep(2000);

            // Verify navigation
            String currentUrl = driver.getCurrentUrl();
            System.out.println("Current URL: " + currentUrl);
            assertTrue(currentUrl.contains("/restaurants/add"));

            // Test 1: Nom trop court (moins de 3 caractères)
            WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("input[name='name']")));
            nameField.sendKeys("Ab");

            WebElement locationField = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("input[name='location']")));
            locationField.sendKeys("Av.");

            // File upload with new selector
            WebElement fileInput = driver.findElement(By.cssSelector("input[type='file']"));
            fileInput.sendKeys("C:\\Users\\saadk\\Downloads\\tt.jpg");

            // Scroll to the submit button
            WebElement submitButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".submit-btn")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
            Thread.sleep(1000);

            // Click submit
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

            // Verify error for name field
            WebElement nameError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".error-message")));
            assertTrue(nameError.getText().contains("Le nom doit contenir au moins 3 caractères"));

            // Test 2: Localisation invalide (moins de 5 caractères)
            nameField.clear();
            nameField.sendKeys("inv");

            locationField.clear();
            locationField.sendKeys("123");
            submitButton.click();

            // Verify error for location field
            WebElement locationError = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".error-message")));
            assertTrue(locationError.getText().contains("La localisation doit contenir au moins 5 caractères"));

        } finally {
            driver.quit();
        }
    }
}