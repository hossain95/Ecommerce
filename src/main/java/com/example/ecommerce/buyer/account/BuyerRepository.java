package com.example.ecommerce.buyer.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    @Query("select b from Buyer b where b.email=?1")
    public Optional<Buyer> findByEmail(String email);
}
