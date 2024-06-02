package com.pp.api.facade;

import com.pp.api.event.WithdrawUserEvent;
import com.pp.api.service.OauthUserTokensService;
import com.pp.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class WithdrawUserFacade {

    private final UserService userService;

    private final OauthUserTokensService oauthUserTokensService;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void withdraw(Long userId) {
        // TODO S3 버킷 이미지 삭제 필요?

        oauthUserTokensService.findByUserId(userId)
                .ifPresent(oauthUserAuthenticatedToken ->
                        applicationEventPublisher.publishEvent(
                                new WithdrawUserEvent(
                                        oauthUserAuthenticatedToken.userId(),
                                        oauthUserAuthenticatedToken.accessToken(),
                                        oauthUserAuthenticatedToken.refreshToken(),
                                        oauthUserAuthenticatedToken.client()
                                )
                        )
                );

        userService.deleteCascadeById(userId);
    }

}
