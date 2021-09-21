package io.catalyte.training.sportsproducts.domains.rate;

import io.catalyte.training.sportsproducts.exceptions.ResourceNotFound;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 * This class provides the implementation for the ReviewService interface.
 */
@Service
public class RateServiceImpl implements RateService {

  private final Logger logger = LogManager.getLogger(RateServiceImpl.class);

  private final RateRepository rateRepository;

  @Autowired
  public RateServiceImpl(RateRepository rateRepository) {
    this.rateRepository = rateRepository;
  }

  /**
   * Retrieves rate associated with type.
   *
   * @param type rate type used to query the database
   * @return rate for a given type
   */
  public List<Rate> getRateByType(String type) {
    if (type == null || type.trim().isEmpty()) {
      throw new ResourceNotFound("No rate type specified for request.");
    }

    try {
      if (rateRepository.getRateByType(type) != null) {
        return rateRepository.getRateByType(type);
      } else {
        logger.info("Rate with type: " + type + ", does not exist.");
        throw new ResourceNotFound("Rate with type: " + type + ", does not exist.");
      }
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }

  /**
   * Retrieves rate associated with type.
   *
   * @param code rate type used to query the database
   * @return rate for a given type
   */
  public List<Rate> getRateByCode(String code) {
    if (code == null || code.trim().isEmpty()) {
      throw new ResourceNotFound("No rate type specified for request.");
    }

    try {
      if (rateRepository.getRateByCode(code) != null) {
        return rateRepository.getRateByCode(code);
      } else {
        logger.info("Rate with code: " + code + ", does not exist.");
        throw new ResourceNotFound("Rate with code: " + code + ", does not exist.");
      }
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
  }
}
