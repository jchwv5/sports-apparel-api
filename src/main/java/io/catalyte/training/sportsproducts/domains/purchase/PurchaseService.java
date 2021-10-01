package io.catalyte.training.sportsproducts.domains.purchase;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PurchaseService {

  Purchase savePurchase(Purchase purchaseToSave);

  List<Purchase> findPurchasesByEmail(String email);


  Purchase calculateTotalCharges(PurchaseForTaxCalculation purchase);


  List<LineItem> findPurchasesByProductId(Long id);

}
