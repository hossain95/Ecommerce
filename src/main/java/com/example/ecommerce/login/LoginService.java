package com.example.ecommerce.login;

import com.example.ecommerce.buyer.account.Buyer;
import com.example.ecommerce.buyer.account.BuyerRepository;
import com.example.ecommerce.seller.account.Seller;
import com.example.ecommerce.seller.account.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginService {
    @Autowired
    private BuyerRepository buyerRepository;
    @Autowired
    private SellerRepository sellerRepository;

    public ResponseEntity<Object> loginUser(String email, String password){

        Optional<Buyer> buyer = buyerRepository.findByEmail(email);
        Optional<Seller> seller = Optional.ofNullable(sellerRepository.findByEmail(email));

        if (buyer.isPresent()){
            Buyer profile = buyer.get();
            if (profile.isEnabled() == false){
                return new ResponseEntity<>("Your account does not active yet!", HttpStatus.NOT_ACCEPTABLE);
            }

            BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
            boolean isPasswordMatch = bCrypt.matches(password, profile.getPassword());

            if (isPasswordMatch == false){
                return new ResponseEntity<>("Wrong password!", HttpStatus.NOT_ACCEPTABLE);
            }

            return new ResponseEntity<>("Login successful to buyer account!", HttpStatus.OK);
        }
        else if (seller.isPresent()){
            Seller profile = seller.get();
            if (profile.isEnabled() == false){
                return new ResponseEntity<>("Your account does not active yet!", HttpStatus.NOT_ACCEPTABLE);
            }

            BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
            boolean isPasswordMatch = bCrypt.matches(password, profile.getPassword());

            if (isPasswordMatch == false){
                return new ResponseEntity<>("Wrong password!", HttpStatus.NOT_ACCEPTABLE);
            }


            return new ResponseEntity<>("Login successful to seller account!", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
        }
    }
}
