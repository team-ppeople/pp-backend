package com.pp.api.unit.repository;

import com.pp.api.entity.ProfileImages;
import com.pp.api.entity.UploadFiles;
import com.pp.api.entity.Users;
import com.pp.api.fixture.ProfileImageFixture;
import com.pp.api.fixture.UploadFileFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.ProfileImagesRepository;
import com.pp.api.repository.UploadFilesRepository;
import com.pp.api.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        Users user = usersRepository.save(UserFixture.of());

        UploadFiles uploadFile = uploadFilesRepository.save(UploadFileFixture.profileImageFileOfUploader(user));

        ProfileImages profileImage = ProfileImageFixture.from(
                user,
                uploadFile
        );

        ProfileImages savedProfileImage = profileImagesRepository.save(profileImage);

        assertThat(savedProfileImage.getId()).isNotNull();
        assertThat(savedProfileImage.getUploadFile()).isEqualTo(profileImage.getUploadFile());
        assertThat(savedProfileImage.getUser()).isSameAs(profileImage.getUser());
        assertThat(savedProfileImage.getUser().getProfileImages()).contains(profileImage);
        assertThat(savedProfileImage.getCreatedDate()).isNotNull();
        assertThat(savedProfileImage.getUpdatedDate()).isNotNull();
    }


    @Test
    void 유저_프로필이미지_엔티티를_조회한다() {
        Users user = usersRepository.save(UserFixture.of());

        UploadFiles uploadFile = uploadFilesRepository.save(UploadFileFixture.profileImageFileOfUploader(user));

        ProfileImages profileImage = ProfileImageFixture.from(
                user,
                uploadFile
        );

        ProfileImages savedProfileImage = profileImagesRepository.save(profileImage);

        entityManager.clear();

        ProfileImages foundProfileImage = profileImagesRepository.findById(profileImage.getId())
                .orElseThrow();

        assertThat(foundProfileImage).isNotSameAs(savedProfileImage);
        assertThat(foundProfileImage.getId()).isEqualTo(savedProfileImage.getId());
        assertThat(foundProfileImage.getUploadFile()).isEqualTo(savedProfileImage.getUploadFile());
        assertThat(foundProfileImage.getUser()).isEqualTo(savedProfileImage.getUser());
        assertThat(foundProfileImage.getCreatedDate()).isEqualTo(savedProfileImage.getCreatedDate());
        assertThat(foundProfileImage.getUpdatedDate()).isEqualTo(savedProfileImage.getUpdatedDate());
    }

    @Test
    void 유저_프로필이미지_엔티티를_유저의_이미지만_모두_삭제한다() {
        Users user = usersRepository.save(UserFixture.of());

        UploadFiles uploadFile = uploadFilesRepository.save(UploadFileFixture.profileImageFileOfUploader(user));

        ProfileImages profileImage = ProfileImageFixture.from(
                user,
                uploadFile
        );

        profileImagesRepository.save(profileImage);

        entityManager.clear();

        profileImagesRepository.deleteByUserId(user.getId());

        boolean exists = profileImagesRepository.existsById(user.getId());

        assertThat(exists).isFalse();
    }

}
