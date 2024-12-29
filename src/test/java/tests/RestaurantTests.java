package tests;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RestaurantTests {

    @Test
    public void testViewRestaurants() {
        // Configuration du WebDriver pour Chrome
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");

        // Ajouter des options pour le navigateur
        ChromeOptions options = new ChromeOptions();


        WebDriver driver = new ChromeDriver(options);

        try {
            // Configuration du délai implicite
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

            // Naviguer vers la page de la liste des restaurants
            driver.get("http://localhost:4200/restaurants"); //

            // Vérifier que la liste des restaurants est affichée
            assertTrue(driver.findElement(By.className("container")).isDisplayed());

            // Vérifier qu'au moins un restaurant est visible
            WebElement restaurantCard = driver.findElement(By.className("card"));
            assertTrue(restaurantCard.isDisplayed());

            // Vérifier que le nom et l'emplacement sont affichés dans la carte
            WebElement name = restaurantCard.findElement(By.className("card-title"));
            assertTrue(name.getText().length() > 0);  // Le nom du restaurant doit être visible

            WebElement location = restaurantCard.findElement(By.className("card-text"));
            assertTrue(location.getText().length() > 0);  // L'emplacement doit être visible

            // Vérifier la présence du bouton de réservation
            WebElement reserveButton = restaurantCard.findElement(By.cssSelector("a.btn.btn-primary"));
            assertTrue(reserveButton.isDisplayed());
            assertTrue(reserveButton.getText().equals("Réserver une table"));

            // Observer les résultats
            Thread.sleep(3000); // Réduction du temps d'observation

        } catch (Exception e) {
            System.out.println("Une erreur s'est produite : " + e.getMessage());
        } finally {
            // Fermer le navigateur
            driver.quit();
        }
    }
}
