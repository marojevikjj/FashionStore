package onlineshopapp.fashionstore.selenium;

import lombok.Getter;
import onlineshopapp.fashionstore.model.Clothes;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ClothesPage extends AbstractPage {

    @FindBy(css = ".col-4")
    private List<WebElement> clothes;
    @FindBy(id = "#name-sort")
    private WebElement byName;
    @FindBy(id = "#price-sort")
    private WebElement byPrice;
    @FindBy(id = "#latest-sort")
    private WebElement byDate;
    @FindBy(id = "#rating-sort")
    private WebElement byRating;


    public ClothesPage(WebDriver driver) {
        super(driver);
    }

    public static ClothesPage to(WebDriver driver) {
        get(driver, "/products");
        return PageFactory.initElements(driver, ClothesPage.class);
    }

    public void assertElemts(int productsNumber) {
        Assert.assertEquals("rows do not match", productsNumber, this.clothes.size());
    }

    public void assertSortName() {
        byName.click();
        List<WebElement> clothesTmp = driver.findElements(By.className("col-4"));
        Assert.assertNotEquals(clothesTmp,clothes);
    }
    public void assertSortPrice() {
        byPrice.click();
        List<WebElement> clothesTmp = driver.findElements(By.className("col-4"));
        Assert.assertNotEquals(clothesTmp,clothes);
    }
//    public void assertSortByDate() {
//        byDate.click();
//        List<WebElement> clothesTmp = driver.findElements(By.className("col-4"));
//        Assert.assertNotEquals(clothesTmp,clothes);
//    }
//    public void assertSortByRating() {
//        byRating.click();
//        List<WebElement> clothesTmp = driver.findElements(By.className("col-4"));
//        Assert.assertNotEquals(clothesTmp,clothes);
//    }
}

