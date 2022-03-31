package com.example.ecommerce.buyer.account;

import com.example.ecommerce.buyer.businessLogic.BuyerBusinessLogicImplement;
import com.example.ecommerce.buyer.verification.BuyerVerification;
import com.example.ecommerce.email.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BuyerService {
    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    BuyerVerification buyerVerification;

    @Autowired
    private BuyerBusinessLogicImplement buyerBusinessLogicImplement;


    public ResponseEntity<Object> createBuyer(Buyer buyer){
        if(buyerBusinessLogicImplement.isEmailExist(buyer.getEmail()) == true){
            return new ResponseEntity<>("Email already exist!", HttpStatus.NOT_ACCEPTABLE);
        }
//        if (buyerBusinessLogicImplement.isEmailValid(buyer.getEmail()) == false){
//            return new ResponseEntity<>("Email is invalid!", HttpStatus.NOT_ACCEPTABLE);
//        }
        if (buyerBusinessLogicImplement.isPasswordValid(buyer.getPassword()) == false){
            return new ResponseEntity<>(buyerBusinessLogicImplement.passwordMustBeContain(), HttpStatus.NOT_ACCEPTABLE);
        }

        String verificationCode = buyerBusinessLogicImplement.verificationCode();
        String encodedPassword = buyerBusinessLogicImplement.encodePassword(buyer.getPassword());


        Email email = new Email();
        email.setTo(buyer.getEmail());
        email.setSubject("Account Verification");
        email.setBody(
                "Hi "+ buyer.getName()
                        +"\nYou want to create an account as a buyer."
                        +"\nPlease visit the link to active the account.\n"+
                        "http://localhost:8888/buyer/activate-account/"+buyer.getEmail()+"/"+verificationCode
        );


        buyerVerification.sendEmail(email);

        buyer.setPassword(encodedPassword);
        buyer.setVerificationCode(verificationCode);

        buyerRepository.save(buyer);

        return new ResponseEntity<>("successfully account created!", HttpStatus.CREATED);
    }

    public ResponseEntity<Object> findByEmail(String email){
        return new ResponseEntity<>(buyerRepository.findByEmail(email), HttpStatus.OK);
    }
}
