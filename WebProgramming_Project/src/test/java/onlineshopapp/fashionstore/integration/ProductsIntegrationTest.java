package onlineshopapp.fashionstore.integration;

import onlineshopapp.fashionstore.model.Clothes;
import onlineshopapp.fashionstore.model.ClothesComment;
import onlineshopapp.fashionstore.model.ClothesGrade;
import onlineshopapp.fashionstore.model.User;
import onlineshopapp.fashionstore.model.enumerations.Role;
import onlineshopapp.fashionstore.service.ClothesCommentService;
import onlineshopapp.fashionstore.service.ClothesGradeService;
import onlineshopapp.fashionstore.service.ClothesService;
import onlineshopapp.fashionstore.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ProductsIntegrationTest {

    MockMvc mockMvc;

    @Autowired
    ClothesService clothesService;
    @Autowired
    UserService userService;
    @Autowired
    ClothesCommentService clothesCommentService;
    @Autowired
    ClothesGradeService clothesGradeService;

    private static boolean dataInitialized = false;
    int currentPage = 1;
    public static Page<Clothes> page;
    public static List<Clothes> clothes;
    public static List<Clothes> clothes2;
    public static Clothes product1;
    public static Clothes product2;
    private static User admin;
    private static User user;
    public static List<Clothes> listProducts;
    public static List<ClothesComment> comments;
    public static ClothesGrade grade;

    @BeforeEach
    public void setup(WebApplicationContext wac) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        initData();
    }

    public void initData() {

        if(!dataInitialized) {

            user = userService.register("user", "user", "user", "user", Role.ROLE_USER, "useruser@gmail.com");
            admin = userService.register("admin", "admin", "admin", "admin", Role.ROLE_ADMIN, "adminadmin@gmail.com");

            product1 = clothesService.create("test", "test", "test","test","test","test",0.0,4.0,10,10,10,10);
            product1.setId((long) 1);
            product2 = clothesService.create("test", "test", "test","test","test","test",0.0,0.0,10,10,10,10);
            clothes = new ArrayList<>();
            clothes.add(product2);
            clothes.add(product1);
            listProducts = clothesService.listProductsByName("test");
            page = clothesService.findAll(1,"name", "asc");

            clothes2 = new ArrayList<>();
            clothes2.add(product1);
            clothes2.add(product2);
            ClothesComment clothesComment = clothesCommentService.addNewComment(user, product1, "comment 1");
            clothesComment.setId((long) 10);
            clothesGradeService.addGrade(user, product1, 5.0);
            comments = clothesCommentService.findCommentsByProduct((long)1);
            grade = clothesGradeService.findByUserAndClothes(user, product1);

            dataInitialized = true;
        }
    }

    @Test
    public void testShowProducts() throws Exception {
        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.get("/products/");

        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("products"));
    }

    @Test
    public void testListByPage() throws Exception {
        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.get("/products/page/" + currentPage)
                .param("sortField", "name")
                .param("sortDir", "asc");

        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentPage"))
                .andExpect(MockMvcResultMatchers.model().attribute("currentPage", currentPage))
                .andExpect(MockMvcResultMatchers.model().attributeExists("totalItems"))
                .andExpect(MockMvcResultMatchers.model().attribute("totalItems", page.getTotalElements()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("totalPages"))
                .andExpect(MockMvcResultMatchers.model().attribute("totalPages", page.getTotalPages()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.model().attribute("products", clothes))
                .andExpect(MockMvcResultMatchers.model().attributeExists("sortField"))
                .andExpect(MockMvcResultMatchers.model().attribute("sortField", "name"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("sortDir"))
                .andExpect(MockMvcResultMatchers.model().attribute("sortDir", "asc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("products"));
    }

    @Test
    public void testSearchProduct() throws Exception{
        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.get("/products/searchProducts/")
        .param("nameSearch", "test");

        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("currentPage"))
                .andExpect(MockMvcResultMatchers.model().attribute("currentPage", 1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("totalItems"))
                .andExpect(MockMvcResultMatchers.model().attribute("totalItems", listProducts.size()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("totalPages"))
                .andExpect(MockMvcResultMatchers.model().attribute("totalPages", 1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("products"))
                .andExpect(MockMvcResultMatchers.model().attribute("products", listProducts))
                .andExpect(MockMvcResultMatchers.model().attributeExists("sortField"))
                .andExpect(MockMvcResultMatchers.model().attribute("sortField", "name"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("sortDir"))
                .andExpect(MockMvcResultMatchers.model().attribute("sortDir", "asc"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("products"));
    }

    @Test
    @WithMockUser(username = "user")
    public void testShowDetails() throws Exception {

        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.get("/products/" + product1.getId())
                .with(request -> {request.setRemoteUser("user");
                    return request;});

        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("comments"))
                .andExpect(MockMvcResultMatchers.model().attribute("comments", comments))
                .andExpect(MockMvcResultMatchers.model().attributeExists("grade"))
                .andExpect(MockMvcResultMatchers.model().attribute("grade", grade.getGrade()))
                .andExpect(MockMvcResultMatchers.model().attributeExists("product"))
                .andExpect(MockMvcResultMatchers.model().attribute("product", product1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("produkti"))
                .andExpect(MockMvcResultMatchers.model().attribute("produkti", clothes2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("product-details"));

    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    public void testShowAdd() throws Exception {

        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.get("/products/add/");

        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("form"));
    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    public void testShowEdit() throws Exception {

        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.get("/products/" + product1.getId() + "/edit/");

        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().attributeExists("product"))
                .andExpect(MockMvcResultMatchers.model().attribute("product", product1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("form"));

    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    public void testCreate() throws Exception {

        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.post("/products/")
                .param("name", "test").param("description", "test")
                .param("image", "test").param("image1", "test")
                .param("image2", "test").param("image3", "test")
                .param("price", "0.0").param("grade", "0.0")
                .param("quantitySizeS", "10").param("quantitySizeM", "10")
                .param("quantitySizeL", "10").param("quantitySizeXL", "10");

        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products"));

    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    public void testUpdate() throws Exception {

        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.post("/products/" + product1.getId())
                .param("name", "test").param("description", "test")
                .param("image", "test").param("image1", "test")
                .param("image2", "test").param("image3", "test")
                .param("price", "0.0").param("grade", "0.0")
                .param("quantitySizeS", "10").param("quantitySizeM", "10")
                .param("quantitySizeL", "10").param("quantitySizeXL", "10");

        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products"));

    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    public void testDelete() throws Exception {

        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.post("/products/delete/" + product1.getId());

        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products"));

    }

    @Test
    @WithMockUser(username = "user")
    public void testAddComment() throws Exception {

        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.post("/products/comment/" + product1.getId())
                .param("comment", "comment")
                .with(request -> {request.setRemoteUser("user");
                    return request;});

        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products/" + product1.getId()));

    }

    @Test
    @WithMockUser(username = "user")
    public void testGradeProduct() throws Exception {

        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.post("/products/" + product1.getId() + "/grade")
                .param("grade", "5.0")
                .with(request -> {request.setRemoteUser("user");
                    return request;});

        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products/" + product1.getId()));

    }

    @Test
    @WithMockUser(username = "user")
    public void testLikeComment() throws Exception {

        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.post("/products/" + product1.getId() + "/commentLike")
                .with(request -> {request.setRemoteUser("user");
                    return request;});

        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products/" + product1.getId()));

    }

    @Test
    @WithMockUser(username = "user")
    public void testDislikeComment() throws Exception {

        MockHttpServletRequestBuilder productRequest = MockMvcRequestBuilders.post("/products/" + product1.getId() + "/commentDislike")
                .with(request -> {request.setRemoteUser("user");
                    return request;});


        this.mockMvc.perform(productRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/products/" + product1.getId()));

    }
}
