package com.pp.api.event.handler;

import com.pp.api.client.apple.AppleClient;
import com.pp.api.event.WithdrawUserEvent;
import com.pp.api.service.PostUserActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.security.oauth2.server.authorization.OAuth2TokenType.ACCESS_TOKEN;
import static org.springframework.security.oauth2.server.authorization.OAuth2TokenType.REFRESH_TOKEN;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
@RequiredArgsConstructor
public class WithdrawUserEventHandler {

    private final PostUserActionService postUserActionService;

    private final AppleClient appleClient;

    @Async
    @TransactionalEventListener(
            phase = AFTER_COMMIT,
            fallbackExecution = true
    )
    void handle(WithdrawUserEvent event) {
        postUserActionService.deleteUserPostThumbsUpByUserId(event.userId());

        if (!event.isAppleOauthUser()) {
            return;
        }

        // 애플 oauth 로그인 유저는 회원 탈퇴시에 엑세스 토큰 및 리프레시 토큰의 revoke 요청을 하지 않으면 심사가 통과되지 않는다고 한다.
        appleClient.revoke(
                event.accessToken(),
                ACCESS_TOKEN
        );

        appleClient.revoke(
                event.refreshToken(),
                REFRESH_TOKEN
        );
    }

}
