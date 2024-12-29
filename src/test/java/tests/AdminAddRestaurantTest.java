package tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminAddRestaurantTest {

    @Test
    public void testAddRestaurant() throws InterruptedException {
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

            // Fill the form with the new selectors
            WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("input[name='name']")));
            nameField.sendKeys("Le Palace");

            WebElement locationField = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("input[name='location']")));
            locationField.sendKeys("Av. Echouhada, Marrakech 40000");

            // File upload with new selector
            WebElement fileInput = driver.findElement(By.cssSelector("input[type='file']"));
            fileInput.sendKeys("C:\\Users\\saadk\\Downloads\\tt.jpg");

            // Wait for the submit button and scroll it into view
            WebElement submitButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".submit-btn")));

            // Scroll the button into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", submitButton);
            Thread.sleep(1000); // Wait for scroll to complete

            // Click using JavaScript executor
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);

            // Wait for SweetAlert success message
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".swal2-title")));
            assertTrue(successMessage.getText().contains("Succès!"));

        } finally {
            driver.quit();
        }
    }
}