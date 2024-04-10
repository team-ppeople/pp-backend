package com.pp.api.unit.repository;

import com.pp.api.entity.Comments;
import com.pp.api.entity.Posts;
import com.pp.api.entity.Users;
import com.pp.api.repository.CommentsRepository;
import com.pp.api.repository.PostsRepository;
import com.pp.api.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class CommentsRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void 댓글_엔티티를_영속화한다() {
        // given
        String content = "WOW! 29번째 생일 넘우 넘우 축하드려요~ 👏";

        // when
        Comments comments = Comments.builder()
                .content(content)
                .post(createAndSavePost())
                .build();

        Comments savedComments = commentsRepository.save(comments);

        // then
        assertThat(savedComments.getId()).isNotNull();
        assertThat(savedComments.getContent()).isEqualTo(content);
        assertThat(savedComments.getPost()).isEqualTo(comments.getPost());
        assertThat(savedComments.getPost().getComments()).contains(comments);
        assertThat(savedComments.getReports()).isNotNull();
        assertThat(savedComments.getReports()).isEmpty();
        assertThat(savedComments.getCreatedDate()).isNotNull();
        assertThat(savedComments.getUpdatedDate()).isNotNull();
    }

    @Test
    void 댓글_엔티티를_조회한다() {
        // given
        String content = "WOW! 29번째 생일 넘우 넘우 축하드려요~ 👏";

        // when
        Comments comments = Comments.builder()
                .content(content)
                .post(createAndSavePost())
                .build();

        Comments savedComments = commentsRepository.save(comments);

        entityManager.clear();

        Comments foundComments = commentsRepository.findById(comments.getId())
                .orElseThrow();

        // then
        assertThat(foundComments).isNotSameAs(savedComments);
        assertThat(foundComments.getId()).isEqualTo(savedComments.getId());
        assertThat(foundComments.getContent()).isEqualTo(savedComments.getContent());
        assertThat(foundComments.getPost()).isEqualTo(savedComments.getPost());
        assertThat(foundComments.getReports()).hasSameElementsAs(savedComments.getReports());
        assertThat(foundComments.getCreatedDate()).isEqualTo(savedComments.getCreatedDate());
        assertThat(foundComments.getUpdatedDate()).isEqualTo(savedComments.getUpdatedDate());
    }

    Users createAndSaveUser() {
        Users user = Users.builder()
                .nickname("sinbom")
                .email("dev.sinbom@gmail.com")
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
