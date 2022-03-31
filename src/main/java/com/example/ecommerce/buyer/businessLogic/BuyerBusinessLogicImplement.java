package com.example.ecommerce.buyer.businessLogic;

import com.example.ecommerce.buyer.account.Buyer;
import com.example.ecommerce.buyer.account.BuyerRepository;
import com.example.ecommerce.seller.account.Seller;
import com.example.ecommerce.seller.account.SellerRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuyerBusinessLogicImplement {
    @Autowired
    private BuyerRepository buyerRepository;

    @Autowired
    private SellerRepository sellerRepository;


    public boolean isEmailExist(String email){
        Optional<Buyer> buyer = buyerRepository.findByEmail(email);
        Optional<Seller> seller = Optional.ofNullable(sellerRepository.findByEmail(email));

        if(buyer.isPresent() || seller.isPresent()){
            System.out.println("Present");
            return true;
        }
        else{
            System.out.println("Not present");
            return false;
        }
    }

//    public boolean isEmailValid(String email){
//        int len = email.length();
//        for(int i = 0; i < len; i++){
//            if(email.charAt(i) == '@' && i < len-5){
//                if(email.charAt(len-4) == '.' && email.charAt(len-3) == 'c' && email.charAt(len-2) == 'o' && email.charAt(len-1) == 'm'){
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
    public boolean isPasswordValid(String password){
        int passwordLength = password.length();
        int lowerCase = (int) password.chars().filter((s)->Character.isLowerCase(s)).count();
        int upperCase = (int) password.chars().filter((s)->Character.isUpperCase(s)).count();
        int nonAlphabetic = passwordLength - (lowerCase+upperCase);
        if(passwordLength < 6 || lowerCase == 0 || upperCase == 0 || nonAlphabetic == 0){
            return false;
        }
        return true;
    }

    public String passwordMustBeContain(){
        String password = "password must be contains:" +
                "\nminimum 6 characters" +
                "\nuppercase" +
                "\nlowercase" +
                "\nnon-alphabetic character";

        return password;
    }

    public String verificationCode(){
        String code = RandomString.make(6);
        return code;
    }

    public String encodePassword(String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        return encodedPassword;
    }
}
