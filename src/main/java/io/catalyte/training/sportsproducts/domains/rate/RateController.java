package io.catalyte.training.sportsproducts.domains.rate;

import static io.catalyte.training.sportsproducts.constants.Paths.RATES_PATH;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Exposes endpoints for the billing domain
 */
@RestController
@RequestMapping(value = RATES_PATH)
public class RateController {

  Logger logger = LogManager.getLogger(
      RateController.class);

  @Autowired
  private RateService rateService;

  @GetMapping(value = "/type")
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity<List<Rate>> getRateByType(@RequestParam(required = false) String t) {
    logger.info("Request received to get shipping rate by type: " + t);

    return new ResponseEntity<>(rateService.getRateByType(t), HttpStatus.OK);
  }

  @GetMapping(value = "/code")
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity<List<Rate>> getRateByCode(@RequestParam(required = false) String c) {
    logger.info("Request received to get shipping rate by code: " + c);

    return new ResponseEntity<>(rateService.getRateByCode(c), HttpStatus.OK);
  }
}
