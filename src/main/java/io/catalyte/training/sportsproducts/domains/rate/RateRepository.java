package io.catalyte.training.sportsproducts.domains.rate;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

  List<Rate> getRateByType(String type);

  List<Rate> getRateByCode(String code);

}
