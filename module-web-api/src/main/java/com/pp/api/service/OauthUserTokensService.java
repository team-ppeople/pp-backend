package com.pp.api.service;

import com.pp.api.entity.OauthUser;
import com.pp.api.entity.OauthUserToken;
import com.pp.api.exception.OauthUserServiceException;
import com.pp.api.repository.OauthUserRepository;
import com.pp.api.repository.OauthUserTokenRepository;
import com.pp.api.service.command.SaveOauthUserTokenCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
@RequiredArgsConstructor
public class OauthUserTokensService {

    private final OauthUserRepository oauthUserRepository;

    private final OauthUserTokenRepository oauthUserTokenRepository;

    @Transactional
    public void save(@Valid SaveOauthUserTokenCommand command) {
        String clientSubject = command.client()
                .parseClientSubject(command.subject());

        OauthUser oauthUser = oauthUserRepository.findByClientSubject(clientSubject)
                .orElseThrow(() -> new OauthUserServiceException("등록되지 않은 회원이에요"));

        oauthUserTokenRepository.findByOauthUserIdAndClient(
                        oauthUser.getId(),
                        command.client()
                )
                .ifPresentOrElse(
                        oauthUserToken -> {
                            oauthUserToken.updateAccessToken(command.accessToken());
                            oauthUserToken.updateRefreshToken(command.refreshToken());
                            oauthUserToken.updateExpiresIn(command.expiresIn());
                        },
                        () -> {
                            OauthUserToken oauthUserToken = OauthUserToken.builder()
                                    .client(command.client())
                                    .accessToken(command.accessToken())
                                    .expiresIn(command.expiresIn())
                                    .refreshToken(command.refreshToken())
                                    .oauthUser(oauthUser)
                                    .build();

                            oauthUserTokenRepository.save(oauthUserToken);
                        }
                );
    }

}
