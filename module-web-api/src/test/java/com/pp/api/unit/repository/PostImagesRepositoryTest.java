package com.pp.api.unit.repository;

import com.pp.api.entity.PostImages;
import com.pp.api.entity.Posts;
import com.pp.api.entity.UploadFiles;
import com.pp.api.entity.Users;
import com.pp.api.fixture.PostFixture;
import com.pp.api.fixture.PostImageFixture;
import com.pp.api.fixture.UploadFileFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.PostImagesRepository;
import com.pp.api.repository.PostsRepository;
import com.pp.api.repository.UploadFilesRepository;
import com.pp.api.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class PostImagesRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private PostImagesRepository postImagesRepository;

    @Autowired
    private UploadFilesRepository uploadFilesRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void 게시글_이미지_엔티티를_영속화한다() {
        Users user = usersRepository.save(UserFixture.of());

        Posts post = postsRepository.save(PostFixture.ofCreator(user));

        UploadFiles uploadFile = uploadFilesRepository.save(UploadFileFixture.postImageFileOfUploader(user));

        PostImages postImage = PostImageFixture.from(
                post,
                uploadFile
        );

        PostImages savedPostImage = postImagesRepository.save(postImage);

        assertThat(savedPostImage.getId()).isNotNull();
        assertThat(savedPostImage.getPost()).isEqualTo(postImage.getPost());
        assertThat(savedPostImage.getPost().getImages()).contains(postImage);
        assertThat(savedPostImage.getUploadFile()).isEqualTo(postImage.getUploadFile());
        assertThat(savedPostImage.getCreatedDate()).isNotNull();
        assertThat(savedPostImage.getUpdatedDate()).isNotNull();
    }

    @Test
    void 게시글_이미지_엔티티를_조회한다() {
        Users user = usersRepository.save(UserFixture.of());

        Posts post = postsRepository.save(PostFixture.ofCreator(user));

        UploadFiles uploadFile = uploadFilesRepository.save(UploadFileFixture.postImageFileOfUploader(user));

        PostImages postImage = PostImageFixture.from(
                post,
                uploadFile
        );

        PostImages savedPostImage = postImagesRepository.save(postImage);

        entityManager.clear();

        PostImages foundPostImage = postImagesRepository.findById(savedPostImage.getId())
                .orElseThrow();

        assertThat(foundPostImage).isNotSameAs(savedPostImage);
        assertThat(foundPostImage.getId()).isEqualTo(savedPostImage.getId());
        assertThat(foundPostImage.getPost()).isEqualTo(savedPostImage.getPost());
        assertThat(foundPostImage.getUploadFile()).isEqualTo(savedPostImage.getUploadFile());
        assertThat(foundPostImage.getCreatedDate()).isEqualTo(savedPostImage.getCreatedDate());
        assertThat(foundPostImage.getUpdatedDate()).isEqualTo(savedPostImage.getUpdatedDate());
    }

}
