package io.catalyte.training.sportsproducts.domains.review;

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
   * Test that ReviewController returns a 404 error when given an invalid product ID parameter
   *
   * @throws Exception
   */
  @Test
  public void getReviewsWithInvalidProductIdReturns404() throws Exception {
    mockMvc.perform(get(REVIEWS_PATH + "/product?id=0")).andExpect(status().isNotFound());
  }

  /**
   * Test that ReviewController returns a 404 error when given an invalid user ID parameter
   *
   * @throws Exception
   */
  @Test
  public void getReviewsWithInvalidUserIdReturns404() throws Exception {
    mockMvc.perform(get(REVIEWS_PATH + "/user?id=0")).andExpect(status().isNotFound());
  }

  /**
   * Test that ReviewController returns a status:200 when given a product ID
   *
   * @throws Exception
   */
  @Test
  public void getReviewByProductIdReturns200() throws Exception {
    mockMvc.perform(get(REVIEWS_PATH + "/product?id=1")).andExpect(status().isOk());
  }

  /**
   * Test that ReviewController returns a status:200 when given a product ID
   *
   * @throws Exception
   */
  @Test
  public void getReviewByUserIdReturns200() throws Exception {
    mockMvc.perform(get(REVIEWS_PATH + "/user?id=1")).andExpect(status().isOk());
  }
}




