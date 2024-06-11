package com.pp.api.unit.repository;

import com.pp.api.entity.PostImage;
import com.pp.api.entity.Post;
import com.pp.api.entity.UploadFile;
import com.pp.api.entity.User;
import com.pp.api.fixture.PostFixture;
import com.pp.api.fixture.PostImageFixture;
import com.pp.api.fixture.UploadFileFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.PostImageRepository;
import com.pp.api.repository.PostRepository;
import com.pp.api.repository.UploadFileRepository;
import com.pp.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class PostImageRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private PostImageRepository postImageRepository;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 게시글_이미지_엔티티를_영속화한다() {
        User user = userRepository.save(UserFixture.of());

        Post post = postRepository.save(PostFixture.ofCreator(user));

        UploadFile uploadFile = uploadFileRepository.save(UploadFileFixture.postImageFileOfUploader(user));

        PostImage postImage = PostImageFixture.from(
                post,
                uploadFile
        );

        PostImage savedPostImage = postImageRepository.save(postImage);

        assertThat(savedPostImage.getId()).isNotNull();
        assertThat(savedPostImage.getPost()).isEqualTo(postImage.getPost());
        assertThat(savedPostImage.getPost().getImages()).contains(postImage);
        assertThat(savedPostImage.getUploadFile()).isEqualTo(postImage.getUploadFile());
        assertThat(savedPostImage.getCreatedDate()).isNotNull();
        assertThat(savedPostImage.getUpdatedDate()).isNotNull();
    }

    @Test
    void 게시글_이미지_엔티티를_조회한다() {
        User user = userRepository.save(UserFixture.of());

        Post post = postRepository.save(PostFixture.ofCreator(user));

        UploadFile uploadFile = uploadFileRepository.save(UploadFileFixture.postImageFileOfUploader(user));

        PostImage postImage = PostImageFixture.from(
                post,
                uploadFile
        );

        PostImage savedPostImage = postImageRepository.save(postImage);

        entityManager.clear();

        PostImage foundPostImage = postImageRepository.findById(savedPostImage.getId())
                .orElseThrow();

        assertThat(foundPostImage).isNotSameAs(savedPostImage);
        assertThat(foundPostImage.getId()).isEqualTo(savedPostImage.getId());
        assertThat(foundPostImage.getPost()).isEqualTo(savedPostImage.getPost());
        assertThat(foundPostImage.getUploadFile()).isEqualTo(savedPostImage.getUploadFile());
        assertThat(foundPostImage.getCreatedDate()).isEqualToIgnoringNanos(savedPostImage.getCreatedDate());
        assertThat(foundPostImage.getUpdatedDate()).isEqualToIgnoringNanos(savedPostImage.getUpdatedDate());
    }

}
