package io.catalyte.training.sportsproducts.data;

import io.catalyte.training.sportsproducts.domains.rate.RateRepository;
import io.catalyte.training.sportsproducts.domains.rate.Rate;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Because this class implements CommandLineRunner, the run method is executed as soon as the server
 * successfully starts, and before it begins accepting requests from the outside.
 * <p>
 * Here, we use this as a place to run some code that generates and saves a list of shipping rates
 * into the database.
 */
@Component
public class RateLoader implements CommandLineRunner {

  private final Logger logger = LogManager.getLogger(ShippingRateLoader.class);
  ShippingRateLoader shippingRateLoader = new ShippingRateLoader();

  @Autowired
  private RateRepository rateRepository;
  @Autowired
  private Environment env;

  @Override
  public void run(String... strings) {
    seedShippingRates();
  }

  private void seedShippingRates() {
    logger.info("Loading shipping cost data...");

    // Generate shipping rate data
    List<Rate> rateLoaderData = shippingRateLoader.buildShippingRates();

    // Persist the shipping data to the database
    rateRepository.saveAll(rateLoaderData);
    logger.info("Data load completed. You can make requests now.");
  }
}
