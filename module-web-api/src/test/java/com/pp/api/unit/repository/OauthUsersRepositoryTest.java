package com.pp.api.unit.repository;

import com.pp.api.entity.OauthUsers;
import com.pp.api.entity.Users;
import com.pp.api.entity.enums.OauthUserClient;
import com.pp.api.repository.OauthUsersRepository;
import com.pp.api.repository.UsersRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import static java.util.UUID.randomUUID;
import static org.assertj.core.api.Assertions.assertThat;

class OauthUsersRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private OauthUsersRepository oauthUsersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @ParameterizedTest
    @EnumSource(value = OauthUserClient.class)
    void Oauth_유저_엔티티를_영속화한다(OauthUserClient client) {
        // given
        String subject = randomUUID().toString();

        // when
        OauthUsers oauthUser = OauthUsers.builder()
                .client(client)
                .subject(subject)
                .user(createAndSaveUser())
                .build();

        OauthUsers savedOauthUser = oauthUsersRepository.save(oauthUser);

        // then
        assertThat(oauthUser.getId()).isNotNull();
        assertThat(oauthUser.getClient()).isSameAs(client);
        assertThat(oauthUser.getClientSubject()).isEqualTo(client.parseClientSubject(subject));
        assertThat(oauthUser.getUser()).isEqualTo(savedOauthUser.getUser());
        assertThat(oauthUser.getCreatedDate()).isNotNull();
        assertThat(oauthUser.getUpdatedDate()).isNotNull();
    }

    @ParameterizedTest
    @EnumSource(value = OauthUserClient.class)
    void Oauth_유저_엔티티를_조회한다(OauthUserClient client) {
        // given
        String subject = randomUUID().toString();

        // when
        OauthUsers oauthUser = OauthUsers.builder()
                .client(client)
                .subject(subject)
                .user(createAndSaveUser())
                .build();

        OauthUsers savedOauthUser = oauthUsersRepository.save(oauthUser);

        entityManager.clear();

        OauthUsers foundOauthUser = oauthUsersRepository.findById(savedOauthUser.getId())
                .orElseThrow();

        // then
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
        // given
        String subject = randomUUID().toString();

        // when
        OauthUsers oauthUser = OauthUsers.builder()
                .client(client)
                .subject(subject)
                .user(createAndSaveUser())
                .build();

        OauthUsers savedOauthUser = oauthUsersRepository.save(oauthUser);

        entityManager.clear();

        boolean exists = oauthUsersRepository.existsByClientSubject(savedOauthUser.getClientSubject());
        boolean notExists = oauthUsersRepository.existsByClientSubject("not-exists-client-subject");

        // then
        assertThat(exists).isTrue();
        assertThat(notExists).isFalse();
    }

    Users createAndSaveUser() {
        Users user = Users.builder()
                .nickname("sinbom")
                .email("dev.sinbom@gmail.com")
                .build();

        return usersRepository.save(user);
    }

}
