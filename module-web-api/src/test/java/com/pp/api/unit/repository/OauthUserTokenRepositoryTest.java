package com.pp.api.unit.repository;

import com.pp.api.entity.OauthUserToken;
import com.pp.api.entity.OauthUser;
import com.pp.api.entity.User;
import com.pp.api.entity.enums.OauthUserClient;
import com.pp.api.fixture.OauthUserFixture;
import com.pp.api.fixture.OauthUserTokenFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.OauthUserTokenRepository;
import com.pp.api.repository.OauthUserRepository;
import com.pp.api.repository.UserRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class OauthUserTokenRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private OauthUserTokenRepository oauthUserTokenRepository;

    @Autowired
    private OauthUserRepository oauthUserRepository;

    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @EnumSource(value = OauthUserClient.class)
    void Oauth_유저_토큰_엔티티를_영속화한다(OauthUserClient client) {
        User user = userRepository.save(UserFixture.of());

        OauthUser oauthUser = oauthUserRepository.save(
                OauthUserFixture.ofClient(
                        client,
                        user
                )
        );

        OauthUserToken oauthUserToken = OauthUserTokenFixture.ofClient(
                client,
                oauthUser
        );

        OauthUserToken savedOauthUserToken = oauthUserTokenRepository.save(oauthUserToken);

        assertThat(savedOauthUserToken.getId()).isNotNull();
        assertThat(savedOauthUserToken.getClient()).isEqualTo(client);
        assertThat(savedOauthUserToken.getAccessToken()).isEqualTo(oauthUserToken.getAccessToken());
        assertThat(savedOauthUserToken.getRefreshToken()).isEqualTo(oauthUserToken.getRefreshToken());
        assertThat(savedOauthUserToken.getExpiresIn()).isEqualTo(oauthUserToken.getExpiresIn());
        assertThat(savedOauthUserToken.getOauthUser()).isEqualTo(oauthUser);
        assertThat(savedOauthUserToken.getCreatedDate()).isNotNull();
        assertThat(savedOauthUserToken.getUpdatedDate()).isNotNull();
    }

    @ParameterizedTest
    @EnumSource(value = OauthUserClient.class)
    void Oauth_유저_토큰_엔티티를_조회한다(OauthUserClient client) {
        User user = userRepository.save(UserFixture.of());

        OauthUser oauthUser = oauthUserRepository.save(
                OauthUserFixture.ofClient(
                        client,
                        user
                )
        );

        OauthUserToken oauthUserToken = OauthUserTokenFixture.ofClient(
                client,
                oauthUser
        );

        OauthUserToken savedOauthUserToken = oauthUserTokenRepository.save(oauthUserToken);

        entityManager.clear();

        OauthUserToken foundOauthUserToken = oauthUserTokenRepository.findById(savedOauthUserToken.getId())
                .orElseThrow();

        assertThat(foundOauthUserToken).isNotSameAs(savedOauthUserToken);
        assertThat(foundOauthUserToken.getId()).isEqualTo(savedOauthUserToken.getId());
        assertThat(foundOauthUserToken.getClient()).isEqualTo(savedOauthUserToken.getClient());
        assertThat(foundOauthUserToken.getAccessToken()).isEqualTo(savedOauthUserToken.getAccessToken());
        assertThat(foundOauthUserToken.getRefreshToken()).isEqualTo(savedOauthUserToken.getRefreshToken());
        assertThat(foundOauthUserToken.getExpiresIn()).isEqualTo(savedOauthUserToken.getExpiresIn());
        assertThat(foundOauthUserToken.getOauthUser()).isEqualTo(savedOauthUserToken.getOauthUser());
        assertThat(foundOauthUserToken.getCreatedDate()).isEqualTo(savedOauthUserToken.getCreatedDate());
        assertThat(foundOauthUserToken.getUpdatedDate()).isEqualTo(savedOauthUserToken.getUpdatedDate());
    }

}
