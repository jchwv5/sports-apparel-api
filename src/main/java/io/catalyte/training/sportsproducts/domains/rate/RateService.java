package io.catalyte.training.sportsproducts.domains.rate;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 * This interface provides an abstraction layer for the Rate Services
 */
@Service
public interface RateService {

  List<Rate> getRateByType(String type);

  List<Rate> getRateByCode(String code);

}
