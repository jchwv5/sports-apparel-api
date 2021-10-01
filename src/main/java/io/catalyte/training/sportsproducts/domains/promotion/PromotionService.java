package io.catalyte.training.sportsproducts.domains.promotion;

import io.catalyte.training.sportsproducts.exceptions.ResourceNotFound;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class PromotionService {

  private final Logger logger = LogManager.getLogger(PromotionService.class);

  @Autowired
  private final PromotionRepository promotionRepository;

  public PromotionService(
      PromotionRepository promotionRepository) {
    this.promotionRepository = promotionRepository;
  }


  /**
   * Adds a new promotion with unique promo code to the database.
   *
   * @param promotion - the promotion to add
   * @return - the persisted promotion
   */
  public Promotion createPromotionByCode(@RequestBody Promotion promotion) {
    boolean codeIsAlreadyInUse = false;
    try {
      if (promotion.getCode() != null && !promotion.getCode().equals("")) {
        codeIsAlreadyInUse = promotionRepository.existsByCode(promotion.getCode());
      }
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
    }
    if (codeIsAlreadyInUse) {
      logger.info(
          "Add new promotion failed, code already exists in the database: " + promotion.getCode());
      throw new ResponseStatusException(HttpStatus.CONFLICT,
          "Email already exists: " + promotion.getCode());
    }
    Promotion persistedPromotion = null;
    try {
      persistedPromotion = promotionRepository.save(promotion);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
    }
    return persistedPromotion;
  }

  /**
   * Retrieves the promotion with the provided id from the database.
   *
   * @param code - the code of the promotion to retrieve
   * @return - the promotion
   */
  public Promotion getPromotionByCode(String code) {
    Promotion promotion;
    try {
      promotion = promotionRepository.getPromotionByCode(code);
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
    if (promotion != null) {
      return promotion;
    } else {
      logger.info("Get by code failed, code does not exist in the database: " + code);
      throw new ResourceNotFound(
          "Get by id failed. code " + code + " does not exist in the database: ");
    }
  }

  public List<Promotion> getPromotions() {
    try {
     return promotionRepository.findAll();
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }
}



