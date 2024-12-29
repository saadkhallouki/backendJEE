package tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationTests {

    @Test
    public void testReservation() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        // Ajouter des options pour le navigateur
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);

        try {
            // Configuration du délai implicite
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            // Naviguer vers la page de la liste des restaurants
            driver.get("http://localhost:4200/restaurants");
            Thread.sleep(2000);

            // Cliquer sur le bouton "Réserver" du premier restaurant
            WebElement reserveButton = driver.findElement(By.cssSelector("a.reserve-button"));
            reserveButton.click();
            Thread.sleep(2000);// Delay

            // Vérifier que la page de réservation s'affiche (en fonction de la route)
            String currentUrl = driver.getCurrentUrl();
            System.out.println("Current URL: " + currentUrl);
            assertTrue(currentUrl.contains("/reserve/"));
            Thread.sleep(2000);

            // Remplir le formulaire de réservation
            WebElement customerNameField = driver.findElement(By.id("customerName"));
            customerNameField.sendKeys("saad");
            Thread.sleep(1000);

            WebElement emailField = driver.findElement(By.id("email"));
            emailField.sendKeys("saadkhallouki17@gmail.com");
            Thread.sleep(1000);

            WebElement phoneNumberField = driver.findElement(By.id("phoneNumber"));
            phoneNumberField.sendKeys("0631745467");
            Thread.sleep(1000);

            WebElement dateField = driver.findElement(By.id("date"));
            dateField.sendKeys("12/30/2024");
            Thread.sleep(1000);

            WebElement timeField = driver.findElement(By.id("time"));
            timeField.sendKeys("11:00AM");
            Thread.sleep(1000);

            WebElement numberOfGuestsField = driver.findElement(By.id("numberOfGuests"));
            numberOfGuestsField.sendKeys("");
            Thread.sleep(1000);

            // Updated submit button selector
            WebElement submitButton = driver.findElement(By.cssSelector("button.btn-reserve"));
            submitButton.click();
            Thread.sleep(2000);

            // Updated success message wait
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
            boolean isSuccessMessageDisplayed = wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    By.cssSelector(".swal2-title"), "Succès!"));
            assertTrue(isSuccessMessageDisplayed);
            Thread.sleep(2000);

        } finally {
            // Fermer le navigateur
            driver.quit();
        }
    }
}