package com.example.ecommerce.seller.account;

import com.example.ecommerce.seller.account.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query("select s from Seller s where s.email =?1")
    public Seller findByEmail(String email);
}
