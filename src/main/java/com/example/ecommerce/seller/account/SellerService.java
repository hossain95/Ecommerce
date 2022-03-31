package com.example.ecommerce.seller.account;

import com.example.ecommerce.email.Email;
import com.example.ecommerce.seller.businessLogic.SellerBusinessLogicImplement;
import com.example.ecommerce.seller.verification.SellerVerification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private SellerVerification sellerVerification;

    @Autowired
    private SellerBusinessLogicImplement sellerBusinessLogicImplement;


    public ResponseEntity<Object> createSeller(Seller seller){
        try{
            Email email = new Email();

            if (sellerBusinessLogicImplement.isPasswordValid(seller.getPassword()) == false){
                return new ResponseEntity<>(sellerBusinessLogicImplement.passwordMustBeContain(),HttpStatus.NOT_ACCEPTABLE);
            }

            if(sellerBusinessLogicImplement.isEmailExist(seller.getEmail()) == true){
                return new ResponseEntity<>(
                        "email already exist",
                        HttpStatus.BAD_REQUEST
                );
            }
//            if (sellerBusinessLogicImplement.isEmailValid(seller.getEmail()) == false){
//                return new ResponseEntity<>(
//                        "email is not valid",
//                        HttpStatus.BAD_REQUEST
//                );
//            }

            String verificationCode = sellerBusinessLogicImplement.verificationCode();

            email.setTo(seller.getEmail());
            email.setSubject("Active Your Account");
            email.setBody(
                    "Hi "+ seller.getName()
                    +"\nYou want to create an account as a seller."
                    +"\nPlease visit the link to active the account.\n"
                    +"http://localhost:8888/seller/activate-account/"+seller.getEmail()+"/"+verificationCode
            );


            sellerVerification.SendEmail(email);

            String password = sellerBusinessLogicImplement.encodePassword(seller.getPassword());
            seller.setPassword(password);

            seller.setVerificationCode(verificationCode);
            sellerRepository.save(seller);

            return new ResponseEntity<>("Your account is created successfully!", HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }
}
