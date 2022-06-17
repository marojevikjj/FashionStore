//package onlineshopapp.fashionstore.integration;
//
//import onlineshopapp.fashionstore.model.Clothes;
//import onlineshopapp.fashionstore.service.ClothesService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@ActiveProfiles("test")
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@AutoConfigureMockMvc
//public class HomeIntegrationTest {
//    //Home - showHomePage
//    MockMvc mockMvc;
//    @Autowired
//    ClothesService clothesService;
//
//
//    @BeforeEach
//    public void setup(WebApplicationContext wac) {
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//
//    }
//    @Test
//    public void testGetHomePage() throws Exception {
//        Clothes product = this.clothesService.create("test", "test", "test","test","test","test",0.0,0.0,10,10,10,10);
//        List<Clothes> clothes = new ArrayList<>();
//        clothes.add(product);
//        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.get("/home");
//        this.mockMvc.perform(productRequest)
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.model().attributeExists("featuredProducts"))
//                .andExpect(MockMvcResultMatchers.model().attribute("featuredProducts", clothes))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("latestProducts"))
//                .andExpect(MockMvcResultMatchers.model().attribute("latestProducts", clothes))
//                .andExpect(MockMvcResultMatchers.model().attributeExists("exclusive"))
//                .andExpect(MockMvcResultMatchers.model().attribute("exclusive", product))
//                .andExpect(MockMvcResultMatchers.view().name("home"));
//
//    }
//    @Test
//    public void testGetHomePageLatestProducts() throws Exception {
//        Clothes product = this.clothesService.create("test", "test", "test","test","test","test",0.0,0.0,10,10,10,10);
//        Clothes product2 = this.clothesService.create("test", "test", "test","test","test","test",0.0,0.0,10,10,10,10);
//        List<Clothes> clothes = new ArrayList<>();
//        clothes.add(product2);
//        clothes.add(product);
//        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.get("/home");
//        this.mockMvc.perform(productRequest)
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.model().attributeExists("latestProducts"))
//                .andExpect(MockMvcResultMatchers.model().attribute("latestProducts", clothes))
//                .andExpect(MockMvcResultMatchers.view().name("home"));
//
//    }
//
//
//}
