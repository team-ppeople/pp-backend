package com.pp.api.unit.repository;

import com.pp.api.entity.Post;
import com.pp.api.entity.User;
import com.pp.api.entity.UserActionOfPost;
import com.pp.api.entity.enums.UserAction;
import com.pp.api.fixture.PostFixture;
import com.pp.api.fixture.UserActionOfPostFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.PostRepository;
import com.pp.api.repository.UserActionOfPostRepository;
import com.pp.api.repository.UserRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class UserActionOfPostRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private UserActionOfPostRepository userActionOfPostRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @EnumSource(value = UserAction.class)
    void 게시글_좋아요_엔티티를_영속화한다(UserAction action) {
        User user = userRepository.save(UserFixture.of());

        Post post = postRepository.save(PostFixture.ofCreator(user));

        User actor = userRepository.save(
                UserFixture.from(
                        "피피처돌이",
                        "pplover@naver.com"
                )
        );

        UserActionOfPost userActionOfPost = UserActionOfPostFixture.from(
                post,
                actor,
                action
        );

        UserActionOfPost savedUserActionOfPost = userActionOfPostRepository.save(userActionOfPost);

        assertThat(savedUserActionOfPost.getId()).isNotNull();
        assertThat(savedUserActionOfPost.getPost()).isEqualTo(userActionOfPost.getPost());
        assertThat(savedUserActionOfPost.getUser()).isEqualTo(userActionOfPost.getUser());
        assertThat(savedUserActionOfPost.getUserAction()).isSameAs(action);
        assertThat(savedUserActionOfPost.getCreatedDate()).isNotNull();
        assertThat(savedUserActionOfPost.getUpdatedDate()).isNotNull();
    }

    @ParameterizedTest
    @EnumSource(value = UserAction.class)
    void 게시글_좋아요_엔티티를_조회한다(UserAction action) {
        User user = userRepository.save(UserFixture.of());

        Post post = postRepository.save(PostFixture.ofCreator(user));

        User actor = userRepository.save(
                UserFixture.from(
                        "피피처돌이",
                        "pplover@naver.com"
                )
        );

        UserActionOfPost userActionOfPost = UserActionOfPostFixture.from(
                post,
                actor,
                action
        );

        UserActionOfPost savedUserActionOfPost = userActionOfPostRepository.save(userActionOfPost);

        entityManager.clear();

        UserActionOfPost foundUserActionOfPost = userActionOfPostRepository.findById(savedUserActionOfPost.getId())
                .orElseThrow();

        assertThat(foundUserActionOfPost).isNotSameAs(savedUserActionOfPost);
        assertThat(foundUserActionOfPost.getId()).isEqualTo(savedUserActionOfPost.getId());
        assertThat(foundUserActionOfPost.getPost()).isEqualTo(savedUserActionOfPost.getPost());
        assertThat(foundUserActionOfPost.getUser()).isEqualTo(savedUserActionOfPost.getUser());
        assertThat(foundUserActionOfPost.getUserAction()).isSameAs(savedUserActionOfPost.getUserAction());
        assertThat(foundUserActionOfPost.getCreatedDate()).isEqualTo(savedUserActionOfPost.getCreatedDate());
        assertThat(foundUserActionOfPost.getUpdatedDate()).isEqualTo(savedUserActionOfPost.getUpdatedDate());
    }

}
