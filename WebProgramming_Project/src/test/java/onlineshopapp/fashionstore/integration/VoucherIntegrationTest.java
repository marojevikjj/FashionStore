package onlineshopapp.fashionstore.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;



@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class VoucherIntegrationTest {

    MockMvc mockMvc;

    @BeforeEach
    public void setup(WebApplicationContext wac) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @WithMockUser(username = "admin", roles={"ADMIN"})
    public void testGetVoucher() throws Exception {

        MockHttpServletRequestBuilder voucherRequest = MockMvcRequestBuilders.get("/voucher");
        this.mockMvc.perform(voucherRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("voucher-form"));
    }

    @Test
    @WithMockUser(username = "username", roles={"ADMIN"})
    public void testCreateVoucher() throws Exception {

        MockHttpServletRequestBuilder voucherRequest = MockMvcRequestBuilders.post("/voucher")
                .param("voucherName", "voucherName")
                .param("discount", "10");
        this.mockMvc.perform(voucherRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("success", true))
                .andExpect(MockMvcResultMatchers.view().name("voucher-form"));
    }
}
