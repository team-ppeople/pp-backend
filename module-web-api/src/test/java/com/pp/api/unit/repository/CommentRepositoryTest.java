package com.pp.api.unit.repository;

import com.pp.api.entity.Comment;
import com.pp.api.entity.Post;
import com.pp.api.entity.User;
import com.pp.api.fixture.CommentFixture;
import com.pp.api.fixture.PostFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.CommentRepository;
import com.pp.api.repository.PostRepository;
import com.pp.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class CommentRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 댓글_엔티티를_영속화한다() {
        User user = userRepository.save(UserFixture.of());

        Post post = postRepository.save(PostFixture.ofCreator(user));

        Comment comment = CommentFixture.fromPostAndCreator(
                post,
                user
        );

        Comment savedComment = commentRepository.save(comment);

        assertThat(savedComment.getId()).isNotNull();
        assertThat(savedComment.getContent()).isEqualTo(comment.getContent());
        assertThat(savedComment.getPost()).isEqualTo(comment.getPost());
        assertThat(savedComment.getCreator()).isEqualTo(comment.getCreator());
        assertThat(savedComment.getPost().getComments()).contains(comment);
        assertThat(savedComment.getReports()).isNotNull();
        assertThat(savedComment.getReports()).isEmpty();
        assertThat(savedComment.getCreatedDate()).isNotNull();
        assertThat(savedComment.getUpdatedDate()).isNotNull();
    }

    @Test
    void 댓글_엔티티를_조회한다() {
        User user = userRepository.save(UserFixture.of());

        Post post = postRepository.save(PostFixture.ofCreator(user));

        Comment comment = CommentFixture.fromPostAndCreator(
                post,
                user
        );

        Comment savedComment = commentRepository.save(comment);

        entityManager.clear();

        Comment foundComment = commentRepository.findById(comment.getId())
                .orElseThrow();

        assertThat(foundComment).isNotSameAs(savedComment);
        assertThat(foundComment.getId()).isEqualTo(savedComment.getId());
        assertThat(foundComment.getContent()).isEqualTo(savedComment.getContent());
        assertThat(foundComment.getPost()).isEqualTo(savedComment.getPost());
        assertThat(foundComment.getCreator()).isEqualTo(savedComment.getCreator());
        assertThat(foundComment.getReports()).hasSameElementsAs(savedComment.getReports());
        assertThat(foundComment.getCreatedDate()).isEqualToIgnoringNanos(savedComment.getCreatedDate());
        assertThat(foundComment.getUpdatedDate()).isEqualToIgnoringNanos(savedComment.getUpdatedDate());
    }

}
