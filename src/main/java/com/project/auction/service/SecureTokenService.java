package com.project.auction.service;

import com.project.auction.dto.PersonDto;
import com.project.auction.exception.UnkownIdentifierException;
import com.project.auction.model.Rol;
import com.project.auction.model.SecureToken;

import java.util.List;

public interface SecureTokenService {
    SecureToken createSecureToken();
    void saveSecureToken(final SecureToken token);
    SecureToken findByToken(final String token);
    SecureToken findByPersonId(long id);
    void removeToken(final SecureToken token);
    void removeTokenByToken(final String token);
}
