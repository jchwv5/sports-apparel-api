package io.catalyte.training.sportsproducts.domains.promotion;

import static io.catalyte.training.sportsproducts.constants.Paths.PROMOTIONS_PATH;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The PromotionController exposes endpoints for promotion related actions.
 */
@RestController
@RequestMapping(value = PROMOTIONS_PATH)
public class PromotionController {

  Logger logger = LogManager.getLogger(PromotionController.class);
  @Autowired
  private PromotionService promotionService;

  public PromotionController( PromotionService promotionService) {
    this.promotionService = promotionService;
  }

  /**
   * Controller method for getting a promotion
   *
   * @param id - promotion id
   * @return promotion with requested id
   */
  @GetMapping(value = "/{id}")
  @ResponseStatus(value = HttpStatus.OK)
  public ResponseEntity<Promotion> getPromotionById(@PathVariable Long id) {
    logger.info("Request received for getPromotionById: " + id);
    return new ResponseEntity<>(promotionService.getPromotionById(id), HttpStatus.OK);
  }

  /**
   * Controller method for creating a promotion
   *
   * @param promotion - new promotion
   * @return promotion
   */
  @PostMapping("")
  @ResponseStatus(value = HttpStatus.CREATED)
  public ResponseEntity<Promotion> createPromotionByCode(@RequestBody Promotion promotion) {
    logger.info("Request received for createPromotionByCode: " + promotion.getCode());
    return new ResponseEntity<>(promotionService.createPromotionByCode(promotion),
        HttpStatus.CREATED);
  }

}



