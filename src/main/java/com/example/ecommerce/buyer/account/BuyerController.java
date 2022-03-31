package com.example.ecommerce.buyer.account;

import com.example.ecommerce.buyer.verification.BuyerVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buyer")
public class BuyerController {
    @Autowired
    private BuyerService buyerService;

    @Autowired
    private BuyerVerification buyerVerification;

    @PostMapping("/registration")
    public ResponseEntity<Object> createBuyer(@RequestBody Buyer buyer){
        return buyerService.createBuyer(buyer);
    }

    @RequestMapping("/activate-account/{emailId}/{verificationCode}")
    public String activateAccount(@RequestBody @PathVariable("emailId") String emailId, @PathVariable("verificationCode") String verificationCode){
        return buyerVerification.activateAccount(emailId, verificationCode);
    }


    @GetMapping("/{emailId}")
    public ResponseEntity<Object> findByEmail(@PathVariable("emailId") String emailId){
        return buyerService.findByEmail(emailId);
    }


}
