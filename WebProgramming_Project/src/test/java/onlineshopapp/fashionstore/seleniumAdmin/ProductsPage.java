package onlineshopapp.fashionstore.seleniumAdmin;

import lombok.Getter;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

@Getter
public class ProductsPage extends AbstractPage {

    @FindBy(id = "prr")
    private List<WebElement> productsNumber;

    @FindBy(id = "delete-product")
    private List<WebElement> deleteButtons;

    @FindBy(className = "edit-item")
    private List<WebElement> editButtons;

    @FindBy(linkText = "Add new product")
    private List<WebElement> addProductButtons;

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public static ProductsPage to(WebDriver driver) {
        get(driver, "/products");
        return PageFactory.initElements(driver, ProductsPage.class);
    }

    public void assertElements(int productsNumber, int deleteButtons, int editButtons, int addProductButtons){
        Assert.assertEquals("products do not match", productsNumber, this.getProductsNumber().size());
        Assert.assertEquals("delete do not match", deleteButtons, this.getDeleteButtons().size());
        Assert.assertEquals("edit do not match", editButtons, this.getEditButtons().size());
        Assert.assertEquals("add is visible", addProductButtons, this.getAddProductButtons().size());

    }
}
