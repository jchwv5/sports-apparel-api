package io.catalyte.training.sportsproducts.domains.purchase;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PurchaseService {

  Purchase savePurchase(Purchase purchaseToSave);

  List<Purchase> findPurchasesByEmail(String email);

  List<LineItem> findPurchasesByProductId(Long id);
}
