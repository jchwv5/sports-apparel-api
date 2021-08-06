package io.catalyte.training.sportsproducts.domains.purchase;

import static io.catalyte.training.sportsproducts.constants.Paths.PURCHASES_PATH;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.ServerException;

/**
 * Exposes endpoints for the purchase domain
 */
@RestController
@RequestMapping(value = PURCHASES_PATH)
public class PurchaseController {

  Logger logger = LogManager.getLogger(PurchaseController.class);

  private  PurchaseService purchaseService;

  @Autowired
  public PurchaseController(PurchaseService purchaseService) {
    this.purchaseService = purchaseService;
  }

  @PostMapping
  public ResponseEntity<Purchase> savePurchase(@RequestBody Purchase purchase) {

    Purchase newPurchase =  purchaseService.savePurchase(purchase);

    return new ResponseEntity<>(newPurchase,HttpStatus.CREATED);

  }


  @GetMapping
  public ResponseEntity findPurchasesByEmail(
      @RequestParam (required = false) String email) {
    return new ResponseEntity<>(purchaseService.findPurchasesByEmail(email), HttpStatus.OK);
  }
}
