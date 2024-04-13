package com.pp.api.unit.repository;

import com.pp.api.entity.Posts;
import com.pp.api.entity.Users;
import com.pp.api.repository.PostsRepository;
import com.pp.api.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class PostsRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void 게시글_엔티티를_영속화한다() {
        // given
        String title = "[HBD] 🎂저의 29번째 생일을 축하합니다.🥳";
        String content = "yo~ 모두들 10002 10002 축하해주세요 😄";

        // when
        Posts post = Posts.builder()
                .title(title)
                .content(content)
                .creator(createAndSaveUser())
                .build();

        Posts savedPost = postsRepository.save(post);

        // then
        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedPost.getTitle()).isEqualTo(title);
        assertThat(savedPost.getContent()).isEqualTo(content);
        assertThat(savedPost.getCreator()).isEqualTo(post.getCreator());
        assertThat(savedPost.getImages()).isNotNull();
        assertThat(savedPost.getImages()).isEmpty();
        assertThat(savedPost.getComments()).isNotNull();
        assertThat(savedPost.getComments()).isEmpty();
        assertThat(savedPost.getReports()).isNotNull();
        assertThat(savedPost.getReports()).isEmpty();
        assertThat(savedPost.getCreatedDate()).isNotNull();
        assertThat(savedPost.getUpdatedDate()).isNotNull();
    }

    @Test
    void 게시글_엔티티를_조회한다() {
        // given
        String title = "[HBD] 🎂저의 29번째 생일을 축하합니다.🥳";
        String content = "yo~ 모두들 10002 10002 축하해주세요 😄";

        // when
        Posts post = Posts.builder()
                .title(title)
                .content(content)
                .creator(createAndSaveUser())
                .build();

        Posts savedPost = postsRepository.save(post);

        entityManager.clear();

        Posts foundPost = postsRepository.findById(savedPost.getId())
                .orElseThrow();

        // then
        assertThat(foundPost).isNotSameAs(savedPost);
        assertThat(foundPost.getId()).isEqualTo(savedPost.getId());
        assertThat(foundPost.getTitle()).isEqualTo(savedPost.getTitle());
        assertThat(foundPost.getContent()).isEqualTo(savedPost.getContent());
        assertThat(foundPost.getCreator()).isEqualTo(savedPost.getCreator());
        assertThat(foundPost.getImages()).hasSameElementsAs(savedPost.getImages());
        assertThat(foundPost.getComments()).hasSameElementsAs(savedPost.getComments());
        assertThat(foundPost.getReports()).hasSameElementsAs(savedPost.getReports());
        assertThat(foundPost.getCreatedDate()).isEqualTo(savedPost.getCreatedDate());
        assertThat(foundPost.getUpdatedDate()).isEqualTo(savedPost.getUpdatedDate());
    }

    Users createAndSaveUser() {
        Users user = Users.builder()
                .nickname("sinbom")
                .email("dev.sinbom@gmail.com")
                .build();

        return usersRepository.save(user);
    }

}
