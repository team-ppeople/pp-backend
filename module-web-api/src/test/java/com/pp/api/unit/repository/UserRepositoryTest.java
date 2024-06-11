package com.pp.api.unit.repository;

import com.pp.api.entity.User;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class UserRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 유저_엔티티를_영속화한다() {
        User user = UserFixture.of();

        User savedUser = userRepository.save(user);

        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getNickname()).isEqualTo(user.getNickname());
        assertThat(savedUser.getEmail()).isEqualTo(user.getEmail());
        assertThat(savedUser.getProfileImages()).isNotNull();
        assertThat(savedUser.getProfileImages()).isEmpty();
        assertThat(savedUser.getPosts()).isNotNull();
        assertThat(savedUser.getPosts()).isEmpty();
        assertThat(savedUser.getCreatedDate()).isNotNull();
        assertThat(savedUser.getUpdatedDate()).isNotNull();
    }

    @Test
    void 유저_엔티티를_조회한다() {
        User user = UserFixture.of();

        User savedUser = userRepository.save(user);

        entityManager.clear();

        User foundUser = userRepository.findById(savedUser.getId())
                .orElseThrow();

        assertThat(foundUser).isNotSameAs(savedUser);
        assertThat(foundUser.getId()).isEqualTo(savedUser.getId());
        assertThat(foundUser.getNickname()).isEqualTo(savedUser.getNickname());
        assertThat(foundUser.getEmail()).isEqualTo(savedUser.getEmail());
        assertThat(foundUser.getProfileImages()).hasSameElementsAs(savedUser.getProfileImages());
        assertThat(foundUser.getPosts()).hasSameElementsAs(savedUser.getPosts());
        assertThat(foundUser.getCreatedDate()).isEqualToIgnoringNanos(savedUser.getCreatedDate());
        assertThat(foundUser.getUpdatedDate()).isEqualToIgnoringNanos(savedUser.getUpdatedDate());
    }

}
