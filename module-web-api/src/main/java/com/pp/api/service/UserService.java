package com.pp.api.service;

import com.pp.api.entity.ProfileImage;
import com.pp.api.entity.UploadFile;
import com.pp.api.entity.User;
import com.pp.api.repository.ProfileImageRepository;
import com.pp.api.repository.UploadFileRepository;
import com.pp.api.repository.UserRepository;
import com.pp.api.service.command.UpdateUserCommand;
import com.pp.api.service.domain.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.pp.api.util.JwtAuthenticationUtil.checkUserPermission;
import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UploadFileRepository uploadFileRepository;

    private final ProfileImageRepository profileImageRepository;

    @Transactional
    public void update(UpdateUserCommand command) {
        checkUserPermission(command.getUserId());

        User user = userRepository.findById(command.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        if (hasText(command.getNickname())) {
            user.updateNickname(command.getNickname());
        }

        if (command.getProfileImageFileUploadId() != null) {
            UploadFile uploadFile = uploadFileRepository.findById(command.getProfileImageFileUploadId())
                    .orElseThrow(() -> new IllegalArgumentException("업로드하지 않은 프로필 이미지입니다."));

            checkUserPermission(uploadFile.getUploader().getId());

            profileImageRepository.deleteByUserId(command.getUserId());

            ProfileImage profileImage = ProfileImage.builder()
                    .user(user)
                    .uploadFile(uploadFile)
                    .build();

            profileImageRepository.save(profileImage);
        }
    }

    public UserProfile findUserProfileById(Long userId) {
        User user = userRepository.findWithProfileImagesById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        String profileImageUrl = user.getProfileImages()
                .stream()
                .limit(1)
                .map(profileImage -> profileImage.getUploadFile().getUrl())
                .findFirst()
                .orElse("https://pp-public-bucket.s3.ap-northeast-2.amazonaws.com/default/default_profile.png");

        return new UserProfile(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                profileImageUrl,
                user.getCreatedDate(),
                user.getUpdatedDate()
        );
    }

    public void deleteCascadeById(Long userId) {
        checkUserPermission(userId);

        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다.");
        }

        userRepository.deleteCascadeById(userId);
    }

}
