package com.pp.api.service;

import com.pp.api.entity.OauthUser;
import com.pp.api.entity.User;
import com.pp.api.exception.OauthUserServiceException;
import com.pp.api.repository.OauthUserRepository;
import com.pp.api.repository.UserRepository;
import com.pp.api.service.command.IsRegisteredOauthUserQuery;
import com.pp.api.service.command.RegisterOauthUserCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OauthUserService {

    private final JwtDecoderFactory<RegisteredClient> jwtDecoderFactory;

    private final RegisteredClientRepository registeredClientRepository;

    private final OauthUserRepository oauthUserRepository;

    private final UserRepository userRepository;

    public boolean isRegistered(IsRegisteredOauthUserQuery query) {
        RegisteredClient registeredClient = registeredClientRepository.findById(query.getClient());

        if (registeredClient == null) {
            throw new OauthUserServiceException("등록 여부를 확인할 수 없는 로그인 방식이에요");
        }

        JwtDecoder decoder = jwtDecoderFactory.createDecoder(registeredClient);

        String subject;

        try {
            subject = decoder.decode(query.getIdToken())
                    .getSubject();
        } catch (Exception e) {
            throw new OauthUserServiceException(
                    "인증 토큰이 유효하지 않아요",
                    e
            );
        }

        String clientSubject = query.getOauthUserClient()
                .parseClientSubject(subject);

        return oauthUserRepository.existsByClientSubject(clientSubject);
    }

    public OauthUser findByClientSubject(String clientSubject) {
        return oauthUserRepository.findByClientSubject(clientSubject)
                .orElseThrow(() -> new OauthUserServiceException("등록되지 않은 회원이에요"));
    }

    @Transactional
    public void registerIfNotRegistered(RegisterOauthUserCommand command) {
        String clientSubject = command.getClient()
                .parseClientSubject(command.getSubject());

        if (oauthUserRepository.existsByClientSubject(clientSubject)) {
            return;
        }

        User user = User.builder()
                .nickname(command.getNickname())
                .email(command.getEmail())
                .build();

        OauthUser oauthUser = OauthUser.builder()
                .client(command.getClient())
                .subject(command.getSubject())
                .user(user)
                .build();

        userRepository.save(user);

        oauthUserRepository.save(oauthUser);
    }

}
