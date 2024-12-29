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

public class AdminDeleteRestaurantTest {

    @Test
    public void testDeleteRestaurant() throws InterruptedException {
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
            Thread.sleep(2000); // Delay to observe the restaurants page loading

            // Cliquer sur le bouton "Supprimer" du premier restaurant
            WebElement deleteButton = driver.findElement(By.cssSelector("button.btn.btn-danger"));
            deleteButton.click();
            Thread.sleep(2000); // Delay to observe the delete action trigger

            // Confirmer la suppression dans la boîte de dialogue
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement confirmButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.swal2-confirm")));
            confirmButton.click();
            Thread.sleep(2000); // Delay to observe the confirmation dialog interaction

            // Attendre que le message de succès soit visible
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'Le restaurant a été supprimé avec succès')]")));
            assertTrue(successMessage.isDisplayed());
            Thread.sleep(2000); // Delay to observe the success message

        } finally {
            // Fermer le navigateur
            driver.quit();
        }
    }
}
