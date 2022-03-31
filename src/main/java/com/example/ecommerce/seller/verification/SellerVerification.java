package com.example.ecommerce.seller.verification;

import com.example.ecommerce.email.Email;
import com.example.ecommerce.seller.account.Seller;
import com.example.ecommerce.seller.account.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
@Service
public class SellerVerification {
    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private JavaMailSender mailsend;

    public String activateAccount(String email, String verificationCode){
        Optional<Seller> seller = Optional.ofNullable(sellerRepository.findByEmail(email));
        System.out.println(seller.get().getVerificationCode() + "\n" + verificationCode);
        if(seller.isPresent()){
            String databaseVerificationCode = seller.get().getVerificationCode();
            if(verificationCode.equals(databaseVerificationCode)){
                seller.get().setEnabled(true);
                sellerRepository.save(seller.get());

                Email createEmail = new Email();
                createEmail.setTo(email);
                createEmail.setSubject("Account activated");
                createEmail.setBody(
                        "Hi, Your Account is activated!\n\nThanks,\nMia Hossain"
                );

                SendEmail(createEmail);


                return "Your Account is activated";
            }
            else{
                return "Your verification code is incorrect";
            }
        }

        return "Does not exist any account";
    }

    public void SendEmail(Email email){
        SimpleMailMessage message = new  SimpleMailMessage();
        message.setTo(email.getTo());
        message.setSubject(email.getSubject());
        message.setText(email.getBody());
        message.setSentDate(new Date());

        mailsend.send(message);
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
