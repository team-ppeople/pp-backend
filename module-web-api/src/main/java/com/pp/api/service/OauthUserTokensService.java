package com.pp.api.service;

import com.pp.api.entity.OauthUser;
import com.pp.api.entity.OauthUserToken;
import com.pp.api.exception.OauthUserServiceException;
import com.pp.api.repository.OauthUserRepository;
import com.pp.api.repository.OauthUserTokenRepository;
import com.pp.api.service.command.SaveOauthUserTokenCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OauthUserTokensService {

    private final OauthUserRepository oauthUserRepository;

    private final OauthUserTokenRepository oauthUserTokenRepository;

    @Transactional
    public void save(SaveOauthUserTokenCommand command) {
        String clientSubject = command.getClient()
                .parseClientSubject(command.getSubject());

        OauthUser oauthUser = oauthUserRepository.findByClientSubject(clientSubject)
                .orElseThrow(() -> new OauthUserServiceException("등록되지 않은 회원이에요"));

        oauthUserTokenRepository.findByOauthUserIdAndClient(
                        oauthUser.getId(),
                        command.getClient()
                )
                .ifPresentOrElse(
                        oauthUserToken -> {
                            oauthUserToken.updateAccessToken(command.getAccessToken());
                            oauthUserToken.updateRefreshToken(command.getRefreshToken());
                            oauthUserToken.updateExpiresIn(command.getExpiresIn());
                        },
                        () -> {
                            OauthUserToken oauthUserToken = OauthUserToken.builder()
                                    .client(command.getClient())
                                    .accessToken(command.getAccessToken())
                                    .expiresIn(command.getExpiresIn())
                                    .refreshToken(command.getRefreshToken())
                                    .oauthUser(oauthUser)
                                    .build();

                            oauthUserTokenRepository.save(oauthUserToken);
                        }
                );
    }

}
