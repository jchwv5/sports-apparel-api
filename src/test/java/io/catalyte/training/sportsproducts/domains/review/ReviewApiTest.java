package io.catalyte.training.sportsproducts.domains.review;

import static io.catalyte.training.sportsproducts.constants.Paths.PRODUCTS_PATH;
import static io.catalyte.training.sportsproducts.constants.Paths.PURCHASES_PATH;
import static io.catalyte.training.sportsproducts.constants.Paths.REVIEWS_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewApiTest {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  /**
   * Test that ReviewController returns a 404 error when given a null or empty string parameter
   *
   * @throws Exception
   */
  @Test
  public void getAllProductsReturns404() throws Exception {
    mockMvc.perform(get(REVIEWS_PATH)).andExpect(status().isNotFound());
  }

  /**
   * Test that ReviewController returns a status:200 requesting all reviews
   *
   * @throws Exception
   */
  @Test
  public void getAllReviewsReturns200() throws Exception {
    mockMvc.perform(get(REVIEWS_PATH)).andExpect(status().isOk());
  }

  /**
   * Test that ReviewController returns a status:200 when given a product ID
   *
   * @throws Exception
   */
  @Test
  public void getReviewByProductIdReturns200() throws Exception {
    mockMvc.perform(get(REVIEWS_PATH + "?productId=1")).andExpect(status().isOk());
  }
}




