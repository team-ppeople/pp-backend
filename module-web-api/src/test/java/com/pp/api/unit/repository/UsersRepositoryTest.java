package com.pp.api.unit.repository;

import com.pp.api.entity.Users;
import com.pp.api.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class UsersRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void 유저_엔티티를_영속화한다() {
        // given
        String nickname = "sinbom";
        String email = "dev.sinbom@gmail.com";

        // when
        Users user = Users.builder()
                .nickname(nickname)
                .email(email)
                .build();

        Users savedUser = usersRepository.save(user);

        // then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getNickname()).isEqualTo(nickname);
        assertThat(savedUser.getEmail()).isEqualTo(email);
        assertThat(savedUser.getProfileImages()).isNotNull();
        assertThat(savedUser.getProfileImages()).isEmpty();
        assertThat(savedUser.getPosts()).isNotNull();
        assertThat(savedUser.getPosts()).isEmpty();
        assertThat(savedUser.getCreatedDate()).isNotNull();
        assertThat(savedUser.getUpdatedDate()).isNotNull();
    }

    @Test
    void 유저_엔티티를_조회한다() {
        // given
        String nickname = "sinbom";
        String email = "dev.sinbom@gmail.com";

        // when
        Users user = Users.builder()
                .nickname(nickname)
                .email(email)
                .build();

        Users savedUser = usersRepository.save(user);

        entityManager.clear();

        Users foundUser = usersRepository.findById(savedUser.getId())
                .orElseThrow();

        // then
        assertThat(foundUser).isNotSameAs(savedUser);
        assertThat(foundUser.getId()).isEqualTo(savedUser.getId());
        assertThat(foundUser.getNickname()).isEqualTo(savedUser.getNickname());
        assertThat(foundUser.getEmail()).isEqualTo(savedUser.getEmail());
        assertThat(foundUser.getProfileImages()).hasSameElementsAs(savedUser.getProfileImages());
        assertThat(foundUser.getPosts()).hasSameElementsAs(savedUser.getPosts());
        assertThat(foundUser.getCreatedDate()).isEqualTo(savedUser.getCreatedDate());
        assertThat(foundUser.getUpdatedDate()).isEqualTo(savedUser.getUpdatedDate());
    }

}