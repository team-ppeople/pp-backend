package com.pp.api.unit.repository;

import com.pp.api.entity.OauthUser;
import com.pp.api.entity.User;
import com.pp.api.entity.enums.OauthUserClient;
import com.pp.api.fixture.OauthUserFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.OauthUserRepository;
import com.pp.api.repository.UserRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class OauthUserRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private OauthUserRepository oauthUserRepository;

    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @EnumSource(value = OauthUserClient.class)
    void Oauth_유저_엔티티를_영속화한다(OauthUserClient client) {
        User user = userRepository.save(UserFixture.of());

        OauthUser oauthUser = OauthUserFixture.ofClient(
                client,
                user
        );

        OauthUser savedOauthUser = oauthUserRepository.save(oauthUser);

        assertThat(savedOauthUser.getId()).isNotNull();
        assertThat(savedOauthUser.getClient()).isSameAs(client);
        assertThat(savedOauthUser.getClientSubject()).isEqualTo(oauthUser.getClientSubject());
        assertThat(savedOauthUser.getUser()).isEqualTo(oauthUser.getUser());
        assertThat(savedOauthUser.getCreatedDate()).isNotNull();
        assertThat(savedOauthUser.getUpdatedDate()).isNotNull();
    }

    @ParameterizedTest
    @EnumSource(value = OauthUserClient.class)
    void Oauth_유저_엔티티를_조회한다(OauthUserClient client) {
        User user = userRepository.save(UserFixture.of());

        OauthUser oauthUser = OauthUserFixture.ofClient(
                client,
                user
        );

        OauthUser savedOauthUser = oauthUserRepository.save(oauthUser);

        entityManager.clear();

        OauthUser foundOauthUser = oauthUserRepository.findById(savedOauthUser.getId())
                .orElseThrow();

        assertThat(foundOauthUser).isNotSameAs(savedOauthUser);
        assertThat(foundOauthUser.getId()).isEqualTo(savedOauthUser.getId());
        assertThat(foundOauthUser.getClient()).isSameAs(savedOauthUser.getClient());
        assertThat(foundOauthUser.getClientSubject()).isEqualTo(savedOauthUser.getClientSubject());
        assertThat(foundOauthUser.getUser()).isEqualTo(savedOauthUser.getUser());
        assertThat(foundOauthUser.getCreatedDate()).isEqualToIgnoringNanos(savedOauthUser.getCreatedDate());
        assertThat(foundOauthUser.getUpdatedDate()).isEqualToIgnoringNanos(savedOauthUser.getUpdatedDate());
    }

    @ParameterizedTest
    @EnumSource(value = OauthUserClient.class)
    void Oauth_유저_엔티티를_클라이언트의_유저정보로_조회한다(OauthUserClient client) {
        User user = userRepository.save(UserFixture.of());

        OauthUser oauthUser = OauthUserFixture.ofClient(
                client,
                user
        );

        OauthUser savedOauthUser = oauthUserRepository.save(oauthUser);

        entityManager.clear();

        boolean exists = oauthUserRepository.existsByClientSubject(savedOauthUser.getClientSubject());
        boolean notExists = oauthUserRepository.existsByClientSubject("not-exists-client-subject");

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

}
