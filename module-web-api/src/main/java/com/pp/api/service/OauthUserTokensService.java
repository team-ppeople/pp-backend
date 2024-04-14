package com.pp.api.service;

import com.pp.api.entity.OauthUserTokens;
import com.pp.api.entity.OauthUsers;
import com.pp.api.repository.OauthUserTokensRepository;
import com.pp.api.repository.OauthUsersRepository;
import com.pp.api.service.command.SaveOauthUserTokenCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OauthUserTokensService {

    private final OauthUsersRepository oauthUsersRepository;

    private final OauthUserTokensRepository oauthUserTokensRepository;

    @Transactional
    public void save(SaveOauthUserTokenCommand command) {
        String clientSubject = command.getClient()
                .parseClientSubject(command.getSubject());

        OauthUsers oauthUser = oauthUsersRepository.findByClientSubject(clientSubject)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 Oauth 인증 로그인 회원입니다."));

        oauthUserTokensRepository.findByOauthUserIdAndClient(
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
                            OauthUserTokens oauthUserToken = OauthUserTokens.builder()
                                    .client(command.getClient())
                                    .accessToken(command.getAccessToken())
                                    .expiresIn(command.getExpiresIn())
                                    .refreshToken(command.getRefreshToken())
                                    .oauthUser(oauthUser)
                                    .build();

                            oauthUserTokensRepository.save(oauthUserToken);
                        }
                );
    }

}
