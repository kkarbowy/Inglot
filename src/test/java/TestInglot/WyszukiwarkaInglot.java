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
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        //wyszukuję pędzle
        findBrushesInSearchBarAndFilter();

        //pobieram ceny do tablicy
        ArrayList<Float> normalPrices = getPricesList(PriceTypeSelector.PRICE.getNotation());
        System.out.println("Ceny normalne " + normalPrices);

        ArrayList<Float> discountPrices = getPricesList(PriceTypeSelector.PRICE_HAS_DISCOUNT.getNotation());
        System.out.println("Ceny zniżkowe " + discountPrices);

        //wykonanie asercji dla cen bez obniżki
        float maxValue = normalPrices.get(0);
        float expectedValue = 119.0f;

        //wykonanie testu
        assertAll("Porównanie cen",
                () -> assertEquals(expectedValue, maxValue, 0.01f));

        //wyświetlenie najwyższej wartości, gdy test przejdzie poprawnie
        System.out.println("Najwyższa cena to " + maxValue + "zł");
    }

    //wyszukanie pędzli i posortowanie ich
    private void findBrushesInSearchBarAndFilter() {
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
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //metoda pozwalająca pobrać listę cen za pomocą selektora css (po w nazwie klasy są spacje)
    private ArrayList<Float> getPricesList(String nameOfCssSelector) {
        ArrayList<Float> prices = new ArrayList<>();
        List<WebElement> pricesLocalisation = driver.findElements(By.cssSelector(nameOfCssSelector));
        for (WebElement element : pricesLocalisation) {
            String elementText = element.getText();
            float elementValue = Float.parseFloat(elementText.replaceAll("[^0-9.,]", "").replace(",", "."));
            prices.add(elementValue);
        }
        //Sortowanie listy malejąco
        Collections.sort(prices, Collections.reverseOrder());
        return prices;
    }
}
//BUG - sortowanie na stronie inglot.pl nie działa poprawnie