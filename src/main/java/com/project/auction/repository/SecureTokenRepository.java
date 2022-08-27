package com.project.auction.repository;

import com.project.auction.model.SecureToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecureTokenRepository extends JpaRepository<SecureToken, Long> {
    SecureToken findByToken(final String token);
    Long removeByToken(String token);
}
