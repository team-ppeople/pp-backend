package com.pp.api.service;

import com.pp.api.entity.OauthUsers;
import com.pp.api.entity.Users;
import com.pp.api.event.handler.OauthUserRegisteredEvent;
import com.pp.api.repository.OauthUsersRepository;
import com.pp.api.repository.UsersRepository;
import com.pp.api.service.command.IsRegisteredOauthUserQuery;
import com.pp.api.service.command.RegisterOauthUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class OauthUsersService {

    private final JwtDecoderFactory<RegisteredClient> jwtDecoderFactory;

    private final RegisteredClientRepository registeredClientRepository;

    private final OauthUsersRepository oauthUsersRepository;

    private final UsersRepository usersRepository;

    private final ApplicationEventPublisher eventPublisher;

    public boolean isRegistered(IsRegisteredOauthUserQuery query) {
        RegisteredClient registeredClient = registeredClientRepository.findById(query.getClient());

        if (registeredClient == null) {
            throw new IllegalArgumentException("등록되지 않은 Oauth 3rd-party 입니다.");
        }

        JwtDecoder decoder;

        try {
            decoder = jwtDecoderFactory.createDecoder(registeredClient);
        } catch (Exception e) {
            throw new IllegalArgumentException("Oauth 3rd-party 정보가 잘못되거나 지원하지않아 등록 여부를 확인할 수 없습니다.");
        }

        String subject;

        try {
            subject = decoder.decode(query.getIdToken())
                    .getSubject();
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Oauth 로그인 인증 토큰이 유효하지 않습니다.",
                    e
            );
        }

        String clientSubject = query.getOauthUserClient()
                .parseClientSubject(subject);

        return oauthUsersRepository.existsByClientSubject(clientSubject);
    }

    public OauthUsers findByClientSubject(String clientSubject) {
        return oauthUsersRepository.findByClientSubject(clientSubject)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 Oauth 인증 로그인 회원입니다."));
    }

    public boolean existsByClientSubject(String clientSubject) {
        if (!hasText(clientSubject)) {
            return false;
        }

        return oauthUsersRepository.existsByClientSubject(clientSubject);
    }

    @Transactional
    public void registerIfNotRegistered(RegisterOauthUserCommand command) {
        String clientSubject = command.getClient()
                .parseClientSubject(command.getSubject());

        if (existsByClientSubject(clientSubject)) {
            return;
        }

        Users user = Users.builder()
                .nickname(command.getNickname())
                .email(command.getEmail())
                .build();

        OauthUsers oauthUser = OauthUsers.builder()
                .client(command.getClient())
                .subject(command.getSubject())
                .user(user)
                .build();

        usersRepository.save(user);

        oauthUsersRepository.save(oauthUser);

        OauthUserRegisteredEvent event = OauthUserRegisteredEvent.of(
                command.getClient(),
                command.getAuthorizationCode(),
                oauthUser.getId()
        );

        eventPublisher.publishEvent(event);
    }

}
