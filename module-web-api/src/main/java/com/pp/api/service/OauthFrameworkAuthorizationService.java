package com.pp.api.service;

import com.pp.api.repository.OauthFrameworkAuthorizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthFrameworkAuthorizationService {

    private final OauthFrameworkAuthorizationRepository oauthFrameworkAuthorizationRepository;

    public void deleteByUserId(Long userId) {
        oauthFrameworkAuthorizationRepository.deleteByUserId(userId);
    }

}
