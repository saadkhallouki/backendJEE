package tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReservationTestsInvalid {

    @Test
    public void testReservationInvalidEmail() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

        // Initialiser le navigateur avec des options
        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);

        try {
            // Naviguer vers la page de réservation
            driver.get("http://localhost:4200/reserve/1");
            Thread.sleep(2000);

            // Remplir le formulaire de réservation avec une adresse email invalide
            WebElement customerNameField = driver.findElement(By.id("customerName"));
            customerNameField.sendKeys("Saad Test");
            Thread.sleep(1000);

            WebElement emailField = driver.findElement(By.id("email"));
            emailField.sendKeys("invalid-email"); // Email incorrect
            Thread.sleep(1000);

            WebElement phoneNumberField = driver.findElement(By.id("phoneNumber"));
            phoneNumberField.sendKeys("0612345678");
            Thread.sleep(1000);

            WebElement dateField = driver.findElement(By.id("date"));
            dateField.sendKeys("12/30/2024");
            Thread.sleep(1000);

            WebElement timeField = driver.findElement(By.id("time"));
            timeField.sendKeys("19:00");
            Thread.sleep(1000);

            WebElement numberOfGuestsField = driver.findElement(By.id("numberOfGuests"));
            numberOfGuestsField.sendKeys("");
            Thread.sleep(1000);

            // Soumettre le formulaire
            WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));
            submitButton.click();

            // Vérifier que le message d'erreur apparaît pour l'email invalide
            WebElement errorMessage = driver.findElement(By.className("swal2-html-container"));
            String errorText = errorMessage.getText();
            System.out.println("Erreur affichée : " + errorText);

            assertTrue(errorText.contains("Format d'email invalide"));

        } finally {
            // Fermer le navigateur
            driver.quit();
        }
    }
}
