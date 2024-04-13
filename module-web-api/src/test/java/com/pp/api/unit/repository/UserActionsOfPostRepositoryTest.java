package com.pp.api.unit.repository;

import com.pp.api.entity.Posts;
import com.pp.api.entity.UserActionsOfPost;
import com.pp.api.entity.Users;
import com.pp.api.entity.enums.UserActions;
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
        // when
        UserActionsOfPost userActionsOfPost = UserActionsOfPost.builder()
                .post(createAndSavePost())
                .user(createAndSaveActionUser())
                .userAction(action)
                .build();

        UserActionsOfPost savedUserActionOfPost = userActionsOfPostRepository.save(userActionsOfPost);

        // then
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
        // when
        UserActionsOfPost userActionsOfPost = UserActionsOfPost.builder()
                .post(createAndSavePost())
                .user(createAndSaveActionUser())
                .userAction(action)
                .build();

        UserActionsOfPost savedUserActionsOfPost = userActionsOfPostRepository.save(userActionsOfPost);

        entityManager.clear();

        UserActionsOfPost foundUserActionsOfPost = userActionsOfPostRepository.findById(savedUserActionsOfPost.getId())
                .orElseThrow();

        // then
        assertThat(foundUserActionsOfPost).isNotSameAs(savedUserActionsOfPost);
        assertThat(foundUserActionsOfPost.getId()).isEqualTo(savedUserActionsOfPost.getId());
        assertThat(foundUserActionsOfPost.getPost()).isEqualTo(savedUserActionsOfPost.getPost());
        assertThat(foundUserActionsOfPost.getUser()).isEqualTo(savedUserActionsOfPost.getUser());
        assertThat(foundUserActionsOfPost.getUserAction()).isSameAs(savedUserActionsOfPost.getUserAction());
        assertThat(foundUserActionsOfPost.getCreatedDate()).isEqualTo(savedUserActionsOfPost.getCreatedDate());
        assertThat(foundUserActionsOfPost.getUpdatedDate()).isEqualTo(savedUserActionsOfPost.getUpdatedDate());
    }

    Users createAndSaveUser() {
        Users user = Users.builder()
                .nickname("sinbom")
                .email("dev.sinbom@gmail.com")
                .build();

        return usersRepository.save(user);
    }

    Users createAndSaveActionUser() {
        Users user = Users.builder()
                .nickname("피피처돌이")
                .email("pplover@naver.com")
                .build();

        return usersRepository.save(user);
    }

    Posts createAndSavePost() {
        Posts post = Posts.builder()
                .title("[HBD] 🎂저의 29번째 생일을 축하합니다.🥳")
                .content("yo~ 모두들 10002 10002 축하해주세요 😄")
                .creator(createAndSaveUser())
                .build();

        return postsRepository.save(post);
    }

}
