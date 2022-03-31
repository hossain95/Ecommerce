package com.example.ecommerce.buyer.verification;

import com.example.ecommerce.buyer.account.Buyer;
import com.example.ecommerce.buyer.account.BuyerRepository;
import com.example.ecommerce.email.Email;
import com.example.ecommerce.seller.account.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class BuyerVerification {
    @Autowired
    private JavaMailSender mailsend;

    @Autowired
    private BuyerRepository buyerRepository;


    public void sendEmail(Email email){
        SimpleMailMessage message = new  SimpleMailMessage();
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        message.setSentDate(new Date());


        mailsend.send(message);
    }


    public String activateAccount(String email, String verificationCode){
        Optional<Buyer> buyer = buyerRepository.findByEmail(email);

        if(buyer.isPresent()){
            String databaseVerificationCode = buyer.get().getVerificationCode();
            if(verificationCode.equals(databaseVerificationCode)){
                buyer.get().setEnabled(true);
                buyerRepository.save(buyer.get());

                Email createEmail = new Email();
                createEmail.setTo(email);
                createEmail.setSubject("Account activated");
                createEmail.setBody(
                        "Hi, Your Account is activated!\n\nThanks,\nMia Hossain"
                );
                sendEmail(createEmail);

                return "Your account is activated";
            }
            else{
                return "Your verification code is incorrect";
            }
        }

        return "Does not have any account";
    }

    public void sendSuccessfulEmail(Email email){
        SimpleMailMessage message = new  SimpleMailMessage();
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        message.setSentDate(new Date());


        mailsend.send(message);
    }
}



