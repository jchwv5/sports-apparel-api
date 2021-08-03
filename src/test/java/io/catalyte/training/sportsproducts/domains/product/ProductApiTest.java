package io.catalyte.training.sportsproducts.domains.product;

import static io.catalyte.training.sportsproducts.constants.Paths.PRODUCTS_PATH;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductApiTest {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void getProductsReturns200() throws Exception {
    mockMvc.perform(get(PRODUCTS_PATH))
        .andExpect(status().isOk());
  }

  @Test
  public void getProductByIdReturnsProductWith200() throws Exception {
    mockMvc.perform(get(PRODUCTS_PATH + "/1"))
        .andExpect(status().isOk());
  }

  @Test
  public void getCategoriesIsUp() throws Exception {
    mockMvc.perform(get(PRODUCTS_PATH + "/category/categories"))
        .andExpect(status().isOk());
  }

  @Test
  public void getCategoriesByName() throws Exception {
    MvcResult result = mockMvc.perform(get(PRODUCTS_PATH + "/category/categories"))
        .andReturn();
    String content = result.getResponse().getContentAsString();
    String expected = "[\"Baseball\",\"Basketball\",\"Boxing\",\"Football\",\"Golf\",\"Hockey\",\"Running\",\"Skateboarding\",\"Soccer\",\"Weightlifting\"]";
    assertEquals(expected, content);
  }

  @Test
  public void getTypesIsUp() throws Exception {
    mockMvc.perform(get(PRODUCTS_PATH + "/type/types"))
        .andExpect(status().isOk());
  }

  @Test
  public void getTypesByName() throws Exception {
    MvcResult result = mockMvc.perform(get(PRODUCTS_PATH + "/type/types"))
        .andReturn();
    String content = result.getResponse().getContentAsString();
    String expected = "[\"Belt\",\"Elbow Pad\",\"Flip Flop\",\"Glove\",\"Hat\",\"Headband\",\"Helmet\",\"Hoodie\",\"Jacket\",\"Pant\",\"Pool Noodle\",\"Shin Guard\",\"Shoe\",\"Short\",\"Sock\",\"Sunglasses\",\"Tank Top\",\"Visor\",\"Wristband\"]";
    assertEquals(expected, content);
  }
}
