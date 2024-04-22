package com.pp.api.unit.repository;

import com.pp.api.entity.Posts;
import com.pp.api.entity.Users;
import com.pp.api.fixture.PostFixture;
import com.pp.api.fixture.UserFixture;
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
        Users user = usersRepository.save(UserFixture.of());

        Posts post = postsRepository.save(PostFixture.ofCreator(user));

        Posts savedPost = postsRepository.save(post);

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
        Users user = usersRepository.save(UserFixture.of());

        Posts post = postsRepository.save(PostFixture.ofCreator(user));

        Posts savedPost = postsRepository.save(post);

        entityManager.clear();

        Posts foundPost = postsRepository.findById(savedPost.getId())
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
