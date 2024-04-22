package com.pp.api.unit.repository;

import com.pp.api.entity.OauthUsers;
import com.pp.api.entity.Users;
import com.pp.api.entity.enums.OauthUserClient;
import com.pp.api.fixture.OauthUserFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.OauthUsersRepository;
import com.pp.api.repository.UsersRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class OauthUsersRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private OauthUsersRepository oauthUsersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @ParameterizedTest
    @EnumSource(value = OauthUserClient.class)
    void Oauth_유저_엔티티를_영속화한다(OauthUserClient client) {
        Users user = usersRepository.save(UserFixture.of());

        OauthUsers oauthUser = OauthUserFixture.ofClient(
                client,
                user
        );

        OauthUsers savedOauthUser = oauthUsersRepository.save(oauthUser);

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
        Users user = usersRepository.save(UserFixture.of());

        OauthUsers oauthUser = OauthUserFixture.ofClient(
                client,
                user
        );

        OauthUsers savedOauthUser = oauthUsersRepository.save(oauthUser);

        entityManager.clear();

        OauthUsers foundOauthUser = oauthUsersRepository.findById(savedOauthUser.getId())
                .orElseThrow();

        assertThat(foundOauthUser).isNotSameAs(savedOauthUser);
        assertThat(foundOauthUser.getId()).isEqualTo(savedOauthUser.getId());
        assertThat(foundOauthUser.getClient()).isSameAs(savedOauthUser.getClient());
        assertThat(foundOauthUser.getClientSubject()).isEqualTo(savedOauthUser.getClientSubject());
        assertThat(foundOauthUser.getUser()).isEqualTo(savedOauthUser.getUser());
        assertThat(foundOauthUser.getCreatedDate()).isEqualTo(savedOauthUser.getCreatedDate());
        assertThat(foundOauthUser.getUpdatedDate()).isEqualTo(savedOauthUser.getUpdatedDate());
    }

    @ParameterizedTest
    @EnumSource(value = OauthUserClient.class)
    void Oauth_유저_엔티티를_클라이언트의_유저정보로_조회한다(OauthUserClient client) {
        Users user = usersRepository.save(UserFixture.of());

        OauthUsers oauthUser = OauthUserFixture.ofClient(
                client,
                user
        );

        OauthUsers savedOauthUser = oauthUsersRepository.save(oauthUser);

        entityManager.clear();

        boolean exists = oauthUsersRepository.existsByClientSubject(savedOauthUser.getClientSubject());
        boolean notExists = oauthUsersRepository.existsByClientSubject("not-exists-client-subject");

        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

}
