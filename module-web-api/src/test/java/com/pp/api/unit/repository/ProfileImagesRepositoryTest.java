package com.pp.api.unit.repository;

import com.pp.api.entity.ProfileImages;
import com.pp.api.entity.UploadFiles;
import com.pp.api.entity.Users;
import com.pp.api.repository.ProfileImagesRepository;
import com.pp.api.repository.UploadFilesRepository;
import com.pp.api.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.pp.api.entity.enums.UploadFileContentTypes.IMAGE_JPEG;
import static com.pp.api.entity.enums.UploadFileTypes.PROFILE_IMAGE;
import static org.assertj.core.api.Assertions.assertThat;

class ProfileImagesRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private ProfileImagesRepository profileImagesRepository;

    @Autowired
    private UploadFilesRepository uploadFilesRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void 유저_프로필이미지_엔티티를_영속화한다() {
        // when
        ProfileImages profileImage = ProfileImages.builder()
                .uploadFile(createAndSaveUploadFile())
                .user(createAndSaveUser())
                .build();

        ProfileImages savedProfileImage = profileImagesRepository.save(profileImage);

        // then
        assertThat(savedProfileImage.getId()).isNotNull();
        assertThat(savedProfileImage.getUploadFile()).isEqualTo(profileImage.getUploadFile());
        assertThat(savedProfileImage.getUser()).isSameAs(profileImage.getUser());
        assertThat(savedProfileImage.getUser().getProfileImages()).contains(profileImage);
        assertThat(savedProfileImage.getCreatedDate()).isNotNull();
        assertThat(savedProfileImage.getUpdatedDate()).isNotNull();
    }


    @Test
    void 유저_프로필이미지_엔티티를_조회한다() {
        // when
        ProfileImages profileImage = ProfileImages.builder()
                .uploadFile(createAndSaveUploadFile())
                .user(createAndSaveUser())
                .build();

        ProfileImages savedProfileImage = profileImagesRepository.save(profileImage);

        entityManager.clear();

        ProfileImages foundProfileImage = profileImagesRepository.findById(profileImage.getId())
                .orElseThrow();

        // then
        assertThat(foundProfileImage).isNotSameAs(savedProfileImage);
        assertThat(foundProfileImage.getId()).isEqualTo(savedProfileImage.getId());
        assertThat(foundProfileImage.getUploadFile()).isEqualTo(savedProfileImage.getUploadFile());
        assertThat(foundProfileImage.getUser()).isEqualTo(savedProfileImage.getUser());
        assertThat(foundProfileImage.getCreatedDate()).isEqualTo(savedProfileImage.getCreatedDate());
        assertThat(foundProfileImage.getUpdatedDate()).isEqualTo(savedProfileImage.getUpdatedDate());
    }

    Users createAndSaveUser() {
        Users user = Users.builder()
                .nickname("sinbom")
                .email("dev.sinbom@gmail.com")
                .build();

        return usersRepository.save(user);
    }

    UploadFiles createAndSaveUploadFile() {
        UploadFiles uploadFile = UploadFiles.builder()
                .fileType(PROFILE_IMAGE)
                .url("https://avatars.githubusercontent.com/u/52724515")
                .contentType(IMAGE_JPEG)
                .contentLength(1048576L)
                .build();

        return uploadFilesRepository.save(uploadFile);
    }

}
