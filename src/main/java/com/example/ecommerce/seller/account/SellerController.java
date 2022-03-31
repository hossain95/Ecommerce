package com.example.ecommerce.seller.account;

import com.example.ecommerce.seller.verification.SellerVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;
    @Autowired
    private SellerVerification sellerVerification;


    @PostMapping("/registration")
    public ResponseEntity<Object> createSellerAccount(@RequestBody Seller seller){
        return sellerService.createSeller(seller);
    }

    @RequestMapping("/activate-account/{emailId}/{verificationCode}")
    public String createSellerAccount(@RequestBody @PathVariable("emailId") String emailId, @PathVariable("verificationCode") String verificationCode){
        return sellerVerification.activateAccount(emailId, verificationCode);
    }

    @GetMapping("/users")
    public String users(){
        return "User information.";
    }
}
