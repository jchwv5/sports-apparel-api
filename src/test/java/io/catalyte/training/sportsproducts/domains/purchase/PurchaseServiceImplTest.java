package io.catalyte.training.sportsproducts.domains.purchase;


import io.catalyte.training.sportsproducts.domains.product.ProductRepository;
import io.catalyte.training.sportsproducts.domains.product.ProductServiceImpl;
import io.catalyte.training.sportsproducts.exceptions.ResourceNotFound;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import org.hibernate.validator.constraints.Email;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseServiceImplTest {

    @InjectMocks
    private PurchaseServiceImpl mockPurchaseServiceImpl;

    @Mock
    private PurchaseRepository mockPurchaseRepository;

    List purchaseList = new ArrayList<>();
    Purchase p1 = new Purchase();
    Purchase p2 = new Purchase();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        DeliveryAddress d1 = new DeliveryAddress();
        d1.setFirstName("Jack");
        d1.setLastName("Smith");
        d1.setDeliveryStreet("123 South St");
        d1.setDeliveryStreet2("Apt 1");
        d1.setDeliveryCity("New York");
        d1.setDeliveryState("NY");
        d1.setDeliveryZip(12345);
        BillingAddress b1 = new BillingAddress();
        b1.setBillingStreet("123 South St");
        b1.setBillingStreet2("Apt 1");
        b1.setBillingCity("New York");
        b1.setBillingState("NY");
        b1.setBillingZip(12345);
        b1.setEmail("Jack@gmail.com");
        b1.setPhone("555-555-5555");
        CreditCard c1 = new CreditCard();
        c1.setCardNumber(1000000000000000L);
        c1.setCvv(510);
        c1.setExpiration("11/11/2011");
        c1.setCardholder("Jack Smith");
        p1.setId(1L);
        p1.setProducts(null);
        p1.setDeliveryAddress(d1);
        p1.setBillingAddress(b1);
        p1.setCreditCard(c1);
        purchaseList.add(p1);

        when(mockPurchaseRepository.findPurchasesByBillingAddressEmail(anyString())).thenReturn(purchaseList);

    }

    @Test
    public void findPurchasesByEmailReturnsJack() {
        ArrayList<Purchase> expected = new ArrayList<>();
        expected.add(p1);
        List<Purchase> result = mockPurchaseServiceImpl.findPurchasesByEmail(p1.getBillingAddress().getEmail());
        Assert.assertEquals(expected, result);
    }

    @Test (expected = ResourceNotFound.class)
    public void findPurchasesByEmailReturns404() {
        mockPurchaseServiceImpl.findPurchasesByEmail("");
    }

    @Test(expected = ServerError.class)
    public void findPurchasesByEmailReturnsDataAccessException() throws Exception {
        Mockito.when(mockPurchaseRepository.findPurchasesByBillingAddressEmail(anyString())).thenThrow(Mockito.mock(DataAccessException.class));
        mockPurchaseServiceImpl.findPurchasesByEmail("joe@gmail.com");
    }

}
