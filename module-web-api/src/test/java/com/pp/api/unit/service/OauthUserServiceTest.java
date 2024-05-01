package com.pp.api.unit.service;

import com.pp.api.entity.OauthUser;
import com.pp.api.entity.enums.OauthUserClient;
import com.pp.api.repository.OauthUserRepository;
import com.pp.api.repository.UserRepository;
import com.pp.api.service.OauthUserService;
import com.pp.api.service.command.IsRegisteredOauthUserQuery;
import com.pp.api.service.command.RegisterOauthUserCommand;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.Optional;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
class OauthUserServiceTest {

    @Mock
    private JwtDecoderFactory<RegisteredClient> jwtDecoderFactory;

    @Mock
    private RegisteredClientRepository registeredClientRepository;

    @Mock
    private OauthUserRepository oauthUserRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OauthUserService oauthUserService;

    @ParameterizedTest
    @EnumSource(value = OauthUserClient.class)
    void Oauth_로그인_idToken으로_회원등록여부를_확인한다(OauthUserClient client) {
        IsRegisteredOauthUserQuery query = new IsRegisteredOauthUserQuery(
                client.name().toLowerCase(),
                randomUUID().toString()
        );

        String subject = randomUUID().toString();

        RegisteredClient registeredClient = mock(RegisteredClient.class);

        JwtDecoder jwtDecoder = mock(JwtDecoder.class);

        Jwt jwt = mock(Jwt.class);

        when(registeredClientRepository.findById(query.getClient()))
                .thenReturn(registeredClient);

        when(jwtDecoderFactory.createDecoder(registeredClient))
                .thenReturn(jwtDecoder);

        when(jwtDecoder.decode(query.getIdToken()))
                .thenReturn(jwt);

        when(jwt.getSubject())
                .thenReturn(subject);

        when(oauthUserRepository.existsByClientSubject(query.getOauthUserClient().parseClientSubject(subject)))
                .thenReturn(true);

        boolean registered = oauthUserService.isRegistered(query);

        assertThat(registered).isTrue();
    }

    @ParameterizedTest
    @EnumSource(value = OauthUserClient.class)
    void Oauth_로그인_유저클라이언트가_존재하지않아_idToken으로_회원등록여부를_확인할수없다(OauthUserClient client) {
        IsRegisteredOauthUserQuery query = new IsRegisteredOauthUserQuery(
                client.name().toLowerCase(),
                randomUUID().toString()
        );

        when(registeredClientRepository.findById(query.getClient()))
                .thenReturn(null);

        assertThatThrownBy(() -> oauthUserService.isRegistered(query))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @EnumSource(value = OauthUserClient.class)
    void Oauth_로그인_유저클라이언트의_JWTDecoder생성에_실패하여_idToken으로_회원등록여부를_확인할수없다(OauthUserClient client) {
        IsRegisteredOauthUserQuery query = new IsRegisteredOauthUserQuery(
                client.name().toLowerCase(),
                randomUUID().toString()
        );

        RegisteredClient registeredClient = mock(RegisteredClient.class);

        when(registeredClientRepository.findById(query.getClient()))
                .thenReturn(registeredClient);

        when(jwtDecoderFactory.createDecoder(registeredClient))
                .thenThrow(OAuth2AuthenticationException.class);

        assertThatThrownBy(() -> oauthUserService.isRegistered(query))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @EnumSource(value = OauthUserClient.class)
    void Oauth_로그인_회원의_인증토큰_유효하지않아_idToken으로_회원등록여부를_확인할수없다(OauthUserClient client) {
        IsRegisteredOauthUserQuery query = new IsRegisteredOauthUserQuery(
                client.name().toLowerCase(),
                randomUUID().toString()
        );

        RegisteredClient registeredClient = mock(RegisteredClient.class);

        JwtDecoder jwtDecoder = mock(JwtDecoder.class);

        when(registeredClientRepository.findById(query.getClient()))
                .thenReturn(registeredClient);

        when(jwtDecoderFactory.createDecoder(registeredClient))
                .thenReturn(jwtDecoder);

        when(jwtDecoder.decode(query.getIdToken()))
                .thenThrow(JwtException.class);

        assertThatThrownBy(() -> oauthUserService.isRegistered(query))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @EnumSource(value = OauthUserClient.class)
    void Oauth_로그인_subject로_회원등록정보를_조회한다(OauthUserClient client) {
        String subject = randomUUID().toString();

        String clientSubject = client.parseClientSubject(subject);

        OauthUser oauthUser = mock(OauthUser.class);

        when(oauthUserRepository.findByClientSubject(clientSubject))
                .thenReturn(Optional.of(oauthUser));

        OauthUser foundOauthUser = oauthUserService.findByClientSubject(clientSubject);

        assertThat(foundOauthUser).isSameAs(oauthUser);
    }

    @ParameterizedTest
    @EnumSource(value = OauthUserClient.class)
    void Oauth_로그인으로_등록하지않은_회원은_subject로_회원등록정보를_조회할수없다(OauthUserClient client) {
        String subject = randomUUID().toString();

        String clientSubject = client.parseClientSubject(subject);

        when(oauthUserRepository.findByClientSubject(clientSubject))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> oauthUserService.findByClientSubject(clientSubject))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @EnumSource(value = OauthUserClient.class)
    void Oauth_로그인으로_회원을_등록한다(OauthUserClient client) {
        String subject = randomUUID().toString();

        String nickname = "sinbom";

        String email = "dev.sinbom@gmail.com";

        String clientSubject = client.parseClientSubject(subject);

        RegisterOauthUserCommand command = new RegisterOauthUserCommand(
                client,
                subject,
                nickname,
                email
        );

        when(oauthUserRepository.existsByClientSubject(clientSubject))
                .thenReturn(false);

        oauthUserService.registerIfNotRegistered(command);

        verify(userRepository, times(1)).save(any());
        verify(oauthUserRepository, times(1)).save(any());
    }

    @ParameterizedTest
    @EnumSource(value = OauthUserClient.class)
    void Oauth_로그인으로_이미_등록된_회원은_등록하지않는다(OauthUserClient client) {
        String subject = randomUUID().toString();

        String nickname = "sinbom";

        String email = "dev.sinbom@gmail.com";

        String clientSubject = client.parseClientSubject(subject);

        RegisterOauthUserCommand command = new RegisterOauthUserCommand(
                client,
                subject,
                nickname,
                email
        );

        when(oauthUserRepository.existsByClientSubject(clientSubject))
                .thenReturn(true);

        oauthUserService.registerIfNotRegistered(command);

        verify(userRepository, never()).save(any());
        verify(oauthUserRepository, never()).save(any());
    }

}
