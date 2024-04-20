package com.pp.api.unit.repository;

import com.pp.api.entity.PostImages;
import com.pp.api.entity.Posts;
import com.pp.api.entity.UploadFiles;
import com.pp.api.entity.Users;
import com.pp.api.repository.PostImagesRepository;
import com.pp.api.repository.PostsRepository;
import com.pp.api.repository.UploadFilesRepository;
import com.pp.api.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.pp.api.entity.enums.UploadFileContentTypes.IMAGE_JPEG;
import static com.pp.api.entity.enums.UploadFileTypes.PROFILE_IMAGE;
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
        // given
        Posts post = createAndSavePost();

        // when
        PostImages postImage = PostImages.builder()
                .post(post)
                .uploadFile(createAndSaveUploadFile(post.getCreator()))
                .build();

        PostImages savedPostImage = postImagesRepository.save(postImage);

        // then
        assertThat(savedPostImage.getId()).isNotNull();
        assertThat(savedPostImage.getPost()).isEqualTo(postImage.getPost());
        assertThat(savedPostImage.getPost().getImages()).contains(postImage);
        assertThat(savedPostImage.getUploadFile()).isEqualTo(postImage.getUploadFile());
        assertThat(savedPostImage.getCreatedDate()).isNotNull();
        assertThat(savedPostImage.getUpdatedDate()).isNotNull();
    }


    @Test
    void 게시글_이미지_엔티티를_조회한다() {
        // given
        Posts post = createAndSavePost();

        // when
        PostImages postImage = PostImages.builder()
                .post(post)
                .uploadFile(createAndSaveUploadFile(post.getCreator()))
                .build();

        PostImages savedPostImage = postImagesRepository.save(postImage);

        entityManager.clear();

        PostImages foundPostImage = postImagesRepository.findById(savedPostImage.getId())
                .orElseThrow();

        // then
        assertThat(foundPostImage).isNotSameAs(savedPostImage);
        assertThat(foundPostImage.getId()).isEqualTo(savedPostImage.getId());
        assertThat(foundPostImage.getPost()).isEqualTo(savedPostImage.getPost());
        assertThat(foundPostImage.getUploadFile()).isEqualTo(savedPostImage.getUploadFile());
        assertThat(foundPostImage.getCreatedDate()).isEqualTo(savedPostImage.getCreatedDate());
        assertThat(foundPostImage.getUpdatedDate()).isEqualTo(savedPostImage.getUpdatedDate());
    }

    Users createAndSaveUser() {
        Users user = Users.builder()
                .nickname("sinbom")
                .email("dev.sinbom@gmail.com")
                .build();

        return usersRepository.save(user);
    }

    UploadFiles createAndSaveUploadFile(Users user) {
        UploadFiles uploadFile = UploadFiles.builder()
                .fileType(PROFILE_IMAGE)
                .url("https://avatars.githubusercontent.com/u/52724515")
                .contentType(IMAGE_JPEG)
                .contentLength(1048576L)
                .uploader(user)
                .build();

        return uploadFilesRepository.save(uploadFile);
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
