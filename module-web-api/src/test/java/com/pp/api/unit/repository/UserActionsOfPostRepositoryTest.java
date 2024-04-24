package com.pp.api.unit.repository;

import com.pp.api.entity.Posts;
import com.pp.api.entity.UserActionsOfPost;
import com.pp.api.entity.Users;
import com.pp.api.entity.enums.UserActions;
import com.pp.api.fixture.PostFixture;
import com.pp.api.fixture.UserActionOfPostFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.PostsRepository;
import com.pp.api.repository.UserActionsOfPostRepository;
import com.pp.api.repository.UsersRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class UserActionsOfPostRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private UserActionsOfPostRepository userActionsOfPostRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @ParameterizedTest
    @EnumSource(value = UserActions.class)
    void 게시글_좋아요_엔티티를_영속화한다(UserActions action) {
        Users user = usersRepository.save(UserFixture.of());

        Posts post = postsRepository.save(PostFixture.ofCreator(user));

        Users actor = usersRepository.save(
                UserFixture.from(
                        "피피처돌이",
                        "pplover@naver.com"
                )
        );

        UserActionsOfPost userActionsOfPost = UserActionOfPostFixture.from(
                post,
                actor,
                action
        );

        UserActionsOfPost savedUserActionOfPost = userActionsOfPostRepository.save(userActionsOfPost);

        assertThat(savedUserActionOfPost.getId()).isNotNull();
        assertThat(savedUserActionOfPost.getPost()).isEqualTo(userActionsOfPost.getPost());
        assertThat(savedUserActionOfPost.getUser()).isEqualTo(userActionsOfPost.getUser());
        assertThat(savedUserActionOfPost.getUserAction()).isSameAs(action);
        assertThat(savedUserActionOfPost.getCreatedDate()).isNotNull();
        assertThat(savedUserActionOfPost.getUpdatedDate()).isNotNull();
    }

    @ParameterizedTest
    @EnumSource(value = UserActions.class)
    void 게시글_좋아요_엔티티를_조회한다(UserActions action) {
        Users user = usersRepository.save(UserFixture.of());

        Posts post = postsRepository.save(PostFixture.ofCreator(user));

        Users actor = usersRepository.save(
                UserFixture.from(
                        "피피처돌이",
                        "pplover@naver.com"
                )
        );

        UserActionsOfPost userActionsOfPost = UserActionOfPostFixture.from(
                post,
                actor,
                action
        );

        UserActionsOfPost savedUserActionsOfPost = userActionsOfPostRepository.save(userActionsOfPost);

        entityManager.clear();

        UserActionsOfPost foundUserActionsOfPost = userActionsOfPostRepository.findById(savedUserActionsOfPost.getId())
                .orElseThrow();

        assertThat(foundUserActionsOfPost).isNotSameAs(savedUserActionsOfPost);
        assertThat(foundUserActionsOfPost.getId()).isEqualTo(savedUserActionsOfPost.getId());
        assertThat(foundUserActionsOfPost.getPost()).isEqualTo(savedUserActionsOfPost.getPost());
        assertThat(foundUserActionsOfPost.getUser()).isEqualTo(savedUserActionsOfPost.getUser());
        assertThat(foundUserActionsOfPost.getUserAction()).isSameAs(savedUserActionsOfPost.getUserAction());
        assertThat(foundUserActionsOfPost.getCreatedDate()).isEqualTo(savedUserActionsOfPost.getCreatedDate());
        assertThat(foundUserActionsOfPost.getUpdatedDate()).isEqualTo(savedUserActionsOfPost.getUpdatedDate());
    }

}
