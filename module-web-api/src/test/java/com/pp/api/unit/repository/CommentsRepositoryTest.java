package com.pp.api.unit.repository;

import com.pp.api.entity.Comments;
import com.pp.api.entity.Posts;
import com.pp.api.entity.Users;
import com.pp.api.fixture.CommentFixture;
import com.pp.api.fixture.PostFixture;
import com.pp.api.fixture.UserFixture;
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
        Users user = usersRepository.save(UserFixture.of());

        Posts post = postsRepository.save(PostFixture.ofCreator(user));

        Comments comments = CommentFixture.ofPost(post);

        Comments savedComments = commentsRepository.save(comments);

        assertThat(savedComments.getId()).isNotNull();
        assertThat(savedComments.getContent()).isEqualTo(comments.getContent());
        assertThat(savedComments.getPost()).isEqualTo(comments.getPost());
        assertThat(savedComments.getPost().getComments()).contains(comments);
        assertThat(savedComments.getReports()).isNotNull();
        assertThat(savedComments.getReports()).isEmpty();
        assertThat(savedComments.getCreatedDate()).isNotNull();
        assertThat(savedComments.getUpdatedDate()).isNotNull();
    }

    @Test
    void 댓글_엔티티를_조회한다() {
        Users user = usersRepository.save(UserFixture.of());

        Posts post = postsRepository.save(PostFixture.ofCreator(user));

        Comments comments = CommentFixture.ofPost(post);

        Comments savedComments = commentsRepository.save(comments);

        entityManager.clear();

        Comments foundComments = commentsRepository.findById(comments.getId())
                .orElseThrow();

        assertThat(foundComments).isNotSameAs(savedComments);
        assertThat(foundComments.getId()).isEqualTo(savedComments.getId());
        assertThat(foundComments.getContent()).isEqualTo(savedComments.getContent());
        assertThat(foundComments.getPost()).isEqualTo(savedComments.getPost());
        assertThat(foundComments.getReports()).hasSameElementsAs(savedComments.getReports());
        assertThat(foundComments.getCreatedDate()).isEqualTo(savedComments.getCreatedDate());
        assertThat(foundComments.getUpdatedDate()).isEqualTo(savedComments.getUpdatedDate());
    }

}
