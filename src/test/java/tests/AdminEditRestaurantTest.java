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

public class AdminEditRestaurantTest {

    @Test
    public void testEditRestaurant() throws InterruptedException {
        // Configuration du WebDriver pour Chrome
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

        // Ajouter des options pour le navigateur
        ChromeOptions options = new ChromeOptions();
        // Désactiver le mode headless pour voir le navigateur
        // options.addArguments("--headless");  // Assurez-vous que cette ligne est commentée si tu veux voir la fenêtre

        WebDriver driver = new ChromeDriver(options);

        try {
            // Configuration du délai implicite
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            // Naviguer vers la page d'accueil de l'admin
            driver.get("http://localhost:55316/restaurants");
            Thread.sleep(2000); // Delay to observe the page load

            // Cliquer sur le bouton "Modifier" du premier restaurant
            WebElement editButton = driver.findElement(By.cssSelector("button.btn.btn-warning"));
            editButton.click();
            Thread.sleep(2000); // Delay to observe the click action

            // Attendre que le formulaire de modification soit visible
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("editForm")));

            // Modifier les champs du formulaire
            WebElement nameField = driver.findElement(By.id("name"));
            nameField.clear();
            Thread.sleep(1000); // Delay to observe clearing the name field
            nameField.sendKeys("Updated Restaurant");
            Thread.sleep(1000); // Delay to observe filling the name field

            WebElement locationField = driver.findElement(By.id("localisation"));
            locationField.clear();
            Thread.sleep(1000); // Delay to observe clearing the location field
            locationField.sendKeys("marrakech");
            Thread.sleep(1000); // Delay to observe filling the location field

            WebElement fileInput = driver.findElement(By.id("file"));
            fileInput.sendKeys("C:\\Users\\saadk\\Downloads\\Dar Cherifa.jpg"); // Ensure this path is correct
            Thread.sleep(2000); // Delay to observe file input action

            WebElement submitButton = driver.findElement(By.cssSelector("button.swal2-confirm"));
            submitButton.click();
            Thread.sleep(2000); // Delay to observe the form submission

            // Attendre que le message de succès soit visible
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Restaurant modifié avec succès')]")));
            assertTrue(successMessage.isDisplayed());
            Thread.sleep(2000); // Delay to observe the success message

        } finally {
            // Fermer le navigateur
            driver.quit();
        }
    }
}
