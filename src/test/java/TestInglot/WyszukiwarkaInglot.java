package TestInglot;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class WyszukiwarkaInglot {

   private WebDriver driver;

    @BeforeEach
    public void driverSetup() {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }
    @AfterEach
    public void driverQuit() {
        driver.quit();
   }
    @Test
    public void checkTheHighestPrice() {
        driver.get("https://inglot.pl");
        //znalezienie wyszukiwarki
        WebElement searchInput = driver.findElement(By.id("topBarSearch"));
        //wpisanie "pędzel" w wyszukiwarce
        searchInput.sendKeys("pędzel");
        //wyszukanie - enter
        searchInput.submit();
        //znalezienie selektora do zmiany sortowania
        WebElement sortDropdown = driver.findElement(By.id("products-sort"));
        //stworzenie obiektu selektora
        Select sortSelect = new Select(sortDropdown);
        //ustawienie selektora na "cena, malejąco"
        sortSelect.selectByVisibleText("Cena, malejąco");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
//BUG - sortowanie na stronie inglot.pl nie działa poprawnie