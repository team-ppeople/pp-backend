package com.pp.api.event.handler;

import com.pp.api.client.apple.AppleClient;
import com.pp.api.event.WithdrawOauthUserEvent;
import com.pp.api.event.WithdrawUserEvent;
import com.pp.api.service.PostUserActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.security.oauth2.server.authorization.OAuth2TokenType.ACCESS_TOKEN;
import static org.springframework.security.oauth2.server.authorization.OAuth2TokenType.REFRESH_TOKEN;
import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Slf4j
@Component
@RequiredArgsConstructor
public class WithdrawUserEventHandler {

    private final PostUserActionService postUserActionService;

    private final AppleClient appleClient;

    @Async(value = "withdrawUserEventHandleExecutor")
    @TransactionalEventListener(
            phase = AFTER_COMMIT,
            fallbackExecution = true
    )
    void handle(WithdrawUserEvent event) {
        log.info(
                "handle WithdrawUserEvent (userId : {})",
                event.userId()
        );

        postUserActionService.deleteUserPostThumbsUpByUserId(event.userId());

        log.info(
                "success deleteUserPostThumbsUpByUserId (userId : {})",
                event.userId()
        );
    }

    @Async(value = "withdrawUserEventHandleExecutor")
    @TransactionalEventListener(
            phase = AFTER_COMMIT,
            fallbackExecution = true
    )
    void handle(WithdrawOauthUserEvent event) {
        log.info(
                "handle WithdrawOauthUserEvent (client: {})",
                event.client()
        );

        // 애플 oauth 로그인 유저는 회원 탈퇴시에 엑세스 토큰 및 리프레시 토큰의 revoke 요청을 하지 않으면 심사가 통과되지 않는다고 한다.
        if (!event.isAppleOauthUser()) {
            return;
        }

        appleClient.revoke(
                event.oauthUserAccessToken(),
                ACCESS_TOKEN
        );

        appleClient.revoke(
                event.oauthUserRefreshToken(),
                REFRESH_TOKEN
        );

        log.info(
                "success revoke token of oauth user (client: {})",
                event.client()
        );
    }

}
