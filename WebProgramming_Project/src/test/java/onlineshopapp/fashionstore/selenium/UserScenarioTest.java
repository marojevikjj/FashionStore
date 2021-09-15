package onlineshopapp.fashionstore.selenium;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.enumerations.Role;
import onlineshopapp.fashionstore.service.ClothesService;
import onlineshopapp.fashionstore.service.UserService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)

public class UserScenarioTest {
    private HtmlUnitDriver driver;
    @Autowired
    UserService userService;
    @Autowired
    ClothesService clothesService;
    //Korisnikot ne e logiran, ja otvara stranata , klika na all products,
    // gi gleda site produkti, sortira po site kategorii i prebaruva fustan,
    // klika na pagerot i ja gleda slednata strana
    private static User regularUser;
    private static User adminUser;

    private static String user = "user";
    private static String admin = "admin";
    private static Clothes product;
    private static Clothes product2;


    private static boolean dataInitialized = false;

    @BeforeEach
    private void setup() {
        this.driver = new HtmlUnitDriver(true);
        initData();
    }

    @AfterEach
    public void destroy() {
        if (this.driver != null) {
            this.driver.close();
        }
    }
    private void initData() {
        if (!dataInitialized) {
           product = this.clothesService.create("test", "test", "test","test","test","test",0.0,0.0,10,10,10,10);
           product2 = this.clothesService.create("test", "test", "test","test","test","test",0.0,0.0,10,10,10,10);
           dataInitialized = true;
        }
    }
    @Test
    public void testScenario() throws Exception {
        ClothesPage clothesPage = ClothesPage.to(this.driver);
        clothesPage.assertElemts(2);
//        clothesPage.assertSortByDate();
//        clothesPage.assertSortByRating();
        clothesPage.assertSortName();
        clothesPage.assertSortPrice();
    }
}
