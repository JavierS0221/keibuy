package com.project.auction.service;

import com.project.auction.model.Rol;
import com.project.auction.model.SecureToken;

import java.util.List;

public interface SecureTokenService {
    SecureToken createSecureToken();
    void saveSecureToken(final SecureToken token);
    SecureToken findByToken(final String token);
    void removeToken(final SecureToken token);
    void removeTokenByToken(final String token);
}
