package com.pp.api.service;

import com.pp.api.entity.ProfileImages;
import com.pp.api.entity.UploadFiles;
import com.pp.api.entity.Users;
import com.pp.api.repository.ProfileImagesRepository;
import com.pp.api.repository.UploadFilesRepository;
import com.pp.api.repository.UsersRepository;
import com.pp.api.service.command.UpdateUserCommand;
import com.pp.api.service.domain.UserWithProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.pp.api.util.JwtAuthenticationUtils.checkUserPermission;
import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;

    private final UploadFilesRepository uploadFilesRepository;

    private final ProfileImagesRepository profileImagesRepository;

    @Transactional
    public void update(
            Long userId,
            UpdateUserCommand command
    ) {
        checkUserPermission(userId);

        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        if (hasText(command.getNickname())) {
            user.updateNickname(command.getNickname());
        }

        if (command.getProfileImageFileUploadId() != null) {
            UploadFiles uploadFile = uploadFilesRepository.findById(command.getProfileImageFileUploadId())
                    .orElseThrow(() -> new IllegalArgumentException("업로드하지 않은 프로필 이미지입니다."));

            checkUserPermission(uploadFile.getUploader().getId());

            ProfileImages profileImage = ProfileImages.builder()
                    .user(user)
                    .uploadFile(uploadFile)
                    .build();

            profileImagesRepository.deleteByUserId(userId);

            profileImagesRepository.save(profileImage);
        }
    }

    @Transactional(readOnly = true)
    public UserWithProfile findWithProfileByUserId(Long userId) {
        checkUserPermission(userId);

        Users user = usersRepository.findWithProfileImagesById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        String profileImageUrl = user.getProfileImages()
                .stream()
                .limit(1)
                .map(profileImage -> profileImage.getUploadFile().getUrl())
                .findFirst()
                .orElse("https://pp-public-bucket.s3.ap-northeast-2.amazonaws.com/default/default_profile.png");

        return new UserWithProfile(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                profileImageUrl,
                user.getCreatedDate(),
                user.getUpdatedDate()
        );
    }

}
