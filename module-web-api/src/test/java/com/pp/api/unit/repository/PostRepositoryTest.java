package com.pp.api.unit.repository;

import com.pp.api.entity.Post;
import com.pp.api.entity.User;
import com.pp.api.fixture.PostFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.PostRepository;
import com.pp.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class PostRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 게시글_엔티티를_영속화한다() {
        User user = userRepository.save(UserFixture.of());

        Post post = postRepository.save(PostFixture.ofCreator(user));

        Post savedPost = postRepository.save(post);

        assertThat(savedPost.getId()).isNotNull();
        assertThat(savedPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(savedPost.getContent()).isEqualTo(post.getContent());
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
        User user = userRepository.save(UserFixture.of());

        Post post = postRepository.save(PostFixture.ofCreator(user));

        Post savedPost = postRepository.save(post);

        entityManager.clear();

        Post foundPost = postRepository.findById(savedPost.getId())
                .orElseThrow();

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

}
