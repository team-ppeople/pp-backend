package com.pp.api.service;

import com.pp.api.configuration.aws.property.AwsS3Property;
import com.pp.api.entity.OauthUserToken;
import com.pp.api.entity.ProfileImage;
import com.pp.api.entity.UploadFile;
import com.pp.api.entity.User;
import com.pp.api.event.WithdrawOauthUserEvent;
import com.pp.api.event.WithdrawUserEvent;
import com.pp.api.exception.UploadFileNotExistsException;
import com.pp.api.exception.UserNotExistsException;
import com.pp.api.repository.OauthUserTokenRepository;
import com.pp.api.repository.ProfileImageRepository;
import com.pp.api.repository.UploadFileRepository;
import com.pp.api.repository.UserRepository;
import com.pp.api.service.command.UpdateUserCommand;
import com.pp.api.service.domain.UserProfile;
import jakarta.validation.Valid;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.validation.annotation.Validated;

import static com.pp.api.util.JwtAuthenticationUtil.checkUserPermission;
import static org.springframework.util.CollectionUtils.isEmpty;
import static org.springframework.util.StringUtils.hasText;

@Validated
@Service
public class UserService {

    private final UserRepository userRepository;

    private final UploadFileRepository uploadFileRepository;

    private final ProfileImageRepository profileImageRepository;

    private final OauthUserTokenRepository oauthUserTokenRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    private final String defaultUserProfileUrl;

    public UserService(
            UserRepository userRepository,
            UploadFileRepository uploadFileRepository,
            ProfileImageRepository profileImageRepository,
            OauthUserTokenRepository oauthUserTokenRepository,
            ApplicationEventPublisher applicationEventPublisher,
            AwsS3Property awsS3Property
    ) {
        this.userRepository = userRepository;
        this.uploadFileRepository = uploadFileRepository;
        this.profileImageRepository = profileImageRepository;
        this.oauthUserTokenRepository = oauthUserTokenRepository;
        this.applicationEventPublisher = applicationEventPublisher;
        this.defaultUserProfileUrl = hasText(awsS3Property.cloudfrontEndpoint()) ?
                awsS3Property.cloudfrontEndpoint() + "/default/default_profile.png" :
                "https://" + awsS3Property.bucket() + ".s3.ap-northeast-2.amazonaws.com/default/default_profile.png";
    }

    @Transactional
    public void update(@Valid UpdateUserCommand command) {
        checkUserPermission(command.userId());

        User user = userRepository.findById(command.userId())
                .orElseThrow(UserNotExistsException::new);

        if (hasText(command.nickname())) {
            user.updateNickname(command.nickname());
        }

        if (command.profileImageFileUploadId() != null) {
            UploadFile uploadFile = uploadFileRepository.findById(command.profileImageFileUploadId())
                    .orElseThrow(() -> new UploadFileNotExistsException("업로드하지 않은 프로필 이미지에요"));

            checkUserPermission(uploadFile.getUploader().getId());

            profileImageRepository.deleteByUserId(command.userId());

            ProfileImage profileImage = ProfileImage.builder()
                    .user(user)
                    .uploadFile(uploadFile)
                    .build();

            profileImageRepository.save(profileImage);
        }
    }

    public UserProfile findUserProfileById(Long userId) {
        User user = userRepository.findWithProfileImagesById(userId)
                .orElseThrow(UserNotExistsException::new);

        return mapToUserProfile(user);
    }

    public List<UserProfile> findUserProfilesByIds(List<Long> userIds) {
        if (isEmpty(userIds)) {
            return List.of();
        }

        return userRepository.findWithProfileImagesByIds(userIds)
                .stream()
                .map(this::mapToUserProfile)
                .toList();
    }

    @Transactional
    public void withdraw(Long userId) {
        checkUserPermission(userId);

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotExistsException::new);

        publishWithdrawUserEvent(user);

        oauthUserTokenRepository.findByUserId(userId)
                .ifPresent(this::publishWithdrawOauthUserEvent);

        userRepository.deleteCascadeById(userId);
    }

    private void publishWithdrawUserEvent(User user) {
        WithdrawUserEvent event = new WithdrawUserEvent(
                user.getId()
        );

        applicationEventPublisher.publishEvent(event);
    }

    private void publishWithdrawOauthUserEvent(OauthUserToken oauthUserToken) {
        WithdrawOauthUserEvent event = new WithdrawOauthUserEvent(
                oauthUserToken.getOauthUser()
                        .getUser()
                        .getId(),
                oauthUserToken.getAccessToken(),
                oauthUserToken.getRefreshToken(),
                oauthUserToken.getClient()
        );

        applicationEventPublisher.publishEvent(event);
    }

    private UserProfile mapToUserProfile(User user) {
        String profileImageUrl = user.getProfileImages()
                .stream()
                .limit(1)
                .map(profileImage -> profileImage.getUploadFile().getUrl())
                .findFirst()
                .orElse(defaultUserProfileUrl);

        return new UserProfile(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                profileImageUrl,
                user.getCreatedDate(),
                user.getUpdatedDate()
        );
    }

}
