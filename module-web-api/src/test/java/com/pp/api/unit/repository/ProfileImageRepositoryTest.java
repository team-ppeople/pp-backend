package com.pp.api.unit.repository;

import com.pp.api.entity.ProfileImage;
import com.pp.api.entity.UploadFile;
import com.pp.api.entity.User;
import com.pp.api.fixture.ProfileImageFixture;
import com.pp.api.fixture.UploadFileFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.ProfileImageRepository;
import com.pp.api.repository.UploadFileRepository;
import com.pp.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ProfileImageRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private ProfileImageRepository profileImageRepository;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 유저_프로필이미지_엔티티를_영속화한다() {
        User user = userRepository.save(UserFixture.of());

        UploadFile uploadFile = uploadFileRepository.save(UploadFileFixture.profileImageFileOfUploader(user));

        ProfileImage profileImage = ProfileImageFixture.from(
                user,
                uploadFile
        );

        ProfileImage savedProfileImage = profileImageRepository.save(profileImage);

        assertThat(savedProfileImage.getId()).isNotNull();
        assertThat(savedProfileImage.getUploadFile()).isEqualTo(profileImage.getUploadFile());
        assertThat(savedProfileImage.getUser()).isSameAs(profileImage.getUser());
        assertThat(savedProfileImage.getUser().getProfileImages()).contains(profileImage);
        assertThat(savedProfileImage.getCreatedDate()).isNotNull();
        assertThat(savedProfileImage.getUpdatedDate()).isNotNull();
    }


    @Test
    void 유저_프로필이미지_엔티티를_조회한다() {
        User user = userRepository.save(UserFixture.of());

        UploadFile uploadFile = uploadFileRepository.save(UploadFileFixture.profileImageFileOfUploader(user));

        ProfileImage profileImage = ProfileImageFixture.from(
                user,
                uploadFile
        );

        ProfileImage savedProfileImage = profileImageRepository.save(profileImage);

        entityManager.clear();

        ProfileImage foundProfileImage = profileImageRepository.findById(profileImage.getId())
                .orElseThrow();

        assertThat(foundProfileImage).isNotSameAs(savedProfileImage);
        assertThat(foundProfileImage.getId()).isEqualTo(savedProfileImage.getId());
        assertThat(foundProfileImage.getUploadFile()).isEqualTo(savedProfileImage.getUploadFile());
        assertThat(foundProfileImage.getUser()).isEqualTo(savedProfileImage.getUser());
        assertThat(foundProfileImage.getCreatedDate()).isEqualTo(savedProfileImage.getCreatedDate());
        assertThat(foundProfileImage.getUpdatedDate()).isEqualTo(savedProfileImage.getUpdatedDate());
    }

    @Test
    void 유저_프로필이미지_엔티티_유저의_이미지만_모두_삭제한다() {
        User user = userRepository.save(UserFixture.of());

        UploadFile uploadFile = uploadFileRepository.save(UploadFileFixture.profileImageFileOfUploader(user));

        ProfileImage profileImage = ProfileImageFixture.from(
                user,
                uploadFile
        );

        profileImageRepository.save(profileImage);

        entityManager.clear();

        profileImageRepository.deleteByUserId(user.getId());

        boolean exists = profileImageRepository.existsById(profileImage.getId());

        assertThat(exists).isFalse();
    }

}
