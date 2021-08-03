package io.catalyte.training.sportsproducts.domains.purchase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static io.catalyte.training.sportsproducts.constants.Paths.PURCHASES_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PurchaseApiTest {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void findPurchasesByEmailReturns404() throws Exception {
        mockMvc.perform(get(PURCHASES_PATH)).andExpect(status().isNotFound());
    }

    @Test
    public void findPurchasesByEmailReturns200() throws Exception {
        mockMvc.perform(get(PURCHASES_PATH + "/?email=joe@gmail.com")).andExpect(status().isOk());
    }

}
