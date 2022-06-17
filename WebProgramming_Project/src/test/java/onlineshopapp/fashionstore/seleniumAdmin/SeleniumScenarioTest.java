//package onlineshopapp.fashionstore.seleniumAdmin;
//
//import onlineshopapp.fashionstore.model.Clothes;
//import onlineshopapp.fashionstore.model.User;
//import onlineshopapp.fashionstore.model.enumerations.Role;
//import onlineshopapp.fashionstore.service.ClothesService;
//import onlineshopapp.fashionstore.service.UserService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.openqa.selenium.htmlunit.HtmlUnitDriver;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.List;
//
//@ActiveProfiles("test")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//public class SeleniumScenarioTest {
//
//    @Autowired
//    ClothesService clothesService;
//    @Autowired
//    UserService userService;
//
//    private HtmlUnitDriver driver;
//
//    private static boolean dataInitialized = false;
//
//    public static List<Clothes> clothes;
//    public static Clothes product1;
//    public static Clothes product2;
//    private static User adminUser;
//
//    @BeforeEach
//    public void setup(WebApplicationContext wac) {
//        this.driver = new HtmlUnitDriver(true);
//        initData();
//    }
//
//    @AfterEach
//    public void destroy(){
//        if(this.driver != null){
//            this.driver.close();
//        }
//    }
//
//    public void initData() {
//
//        if(!dataInitialized) {
//
//            adminUser = userService.register("admin", "admin", "admin", "admin", Role.ROLE_ADMIN, "adminadmin@gmail.com");
//
//          //  product1 = this.clothesService.create("test", "test", "test","test","test","test",0.0,0.0,10,10,10,10);
//          //  product2 = this.clothesService.create("test", "test", "test","test","test","test",0.0,0.0,10,10,10,10);
//            dataInitialized = true;
//        }
//    }
//
//    @Test
//    @WithMockUser(username = "admin", roles = "ADMIN")
//    public void testScenario() throws Exception{
//       ProductsPage productsPage = ProductsPage.to(this.driver);
//       productsPage.assertElements(0,0,0,0);
//
//       LoginPage loginPage = LoginPage.openLogin(this.driver);
//       productsPage = LoginPage.doLogin(this.driver, loginPage, adminUser.getUsername(), adminUser.getPassword());
//       productsPage.assertElements(0,0,0,1);
//
//        productsPage = AddOrEditProduct.addProduct(this.driver, "test", "test", "test", "test","test","test","1","test","test","test","test","test");
//       productsPage.assertElements(1,1,1,1);
//
//        productsPage = AddOrEditProduct.addProduct(this.driver, "test1", "test1", "test", "test","test","test","1","test","test","test","test","test");
//        productsPage.assertElements(2,2,2,1);
//
//        productsPage.getDeleteButtons().get(1).click();
//        productsPage.assertElements(1,1,1,1);
//
//        productsPage = AddOrEditProduct.editProduct(this.driver, productsPage.getEditButtons().get(0),"test1", "test1", "test", "test","test","test","1","test","test","test","test","test");
//        productsPage.assertElements(1,1,1,1);
//
//
//    }
//}
