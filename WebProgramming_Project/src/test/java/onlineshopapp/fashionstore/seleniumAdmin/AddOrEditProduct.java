//package onlineshopapp.fashionstore.seleniumAdmin;
//
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.PageFactory;
//
//public class AddOrEditProduct extends AbstractPage{
//
//    private WebElement name;
//    private WebElement description;
//    private WebElement image;
//    private WebElement image1;
//    private WebElement image2;
//    private WebElement image3;
//    private WebElement price;
//    private WebElement grade;
//    private WebElement quantitySizeS;
//    private WebElement quantitySizeM;
//    private WebElement quantitySizeL;
//    private WebElement quantitySizeXL;
//    private WebElement submit;
//
//    public AddOrEditProduct(WebDriver driver) {
//        super(driver);
//    }
//
//    public static ProductsPage addProduct(WebDriver driver, String name, String description, String image, String image1, String image2, String image3, String price, String grade, String quantitySizeS, String quantitySizeM, String quantitySizeL, String quantitySizeXL){
//        get(driver, "/products/add");
//        AddOrEditProduct addOrEditProduct = PageFactory.initElements(driver, AddOrEditProduct.class);
//        addOrEditProduct.name.sendKeys(name);
//        addOrEditProduct.description.sendKeys(description);
//        addOrEditProduct.image.sendKeys(image);
//        addOrEditProduct.image1.sendKeys(image1);
//        addOrEditProduct.image2.sendKeys(image2);
//        addOrEditProduct.image3.sendKeys(image3);
//        addOrEditProduct.price.sendKeys(price);
//        addOrEditProduct.grade.sendKeys(grade);
//        addOrEditProduct.quantitySizeS.sendKeys(quantitySizeS);
//        addOrEditProduct.quantitySizeM.sendKeys(quantitySizeM);
//        addOrEditProduct.quantitySizeL.sendKeys(quantitySizeL);
//        addOrEditProduct.quantitySizeXL.sendKeys(quantitySizeXL);
//
//        addOrEditProduct.submit.click();
//        return PageFactory.initElements(driver, ProductsPage.class);
//    }
//
//    public static ProductsPage editProduct(WebDriver driver, WebElement editButton, String name, String description, String image, String image1, String image2, String image3, String price, String grade, String quantitySizeS, String quantitySizeM, String quantitySizeL, String quantitySizeXL){
//        editButton.click();
//        System.out.println(driver.getCurrentUrl());
//        AddOrEditProduct addOrEditProduct = PageFactory.initElements(driver, AddOrEditProduct.class);
//        addOrEditProduct.name.sendKeys(name);
//        addOrEditProduct.description.sendKeys(description);
//        addOrEditProduct.image.sendKeys(image);
//        addOrEditProduct.image1.sendKeys(image1);
//        addOrEditProduct.image2.sendKeys(image2);
//        addOrEditProduct.image3.sendKeys(image3);
//        addOrEditProduct.price.sendKeys(price);
//        addOrEditProduct.grade.sendKeys(grade);
//        addOrEditProduct.quantitySizeS.sendKeys(quantitySizeS);
//        addOrEditProduct.quantitySizeM.sendKeys(quantitySizeM);
//        addOrEditProduct.quantitySizeL.sendKeys(quantitySizeL);
//        addOrEditProduct.quantitySizeXL.sendKeys(quantitySizeXL);
//
//        addOrEditProduct.submit.click();
//        return PageFactory.initElements(driver, ProductsPage.class);
//    }
//
//
//
//}
