package io.catalyte.training.sportsproducts.domains.promotion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {

  boolean existsByCode(String code);


}
