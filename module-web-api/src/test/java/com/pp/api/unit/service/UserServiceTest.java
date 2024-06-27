package com.pp.api.unit.service;

import com.pp.api.configuration.aws.property.AwsS3Property;
import com.pp.api.entity.UploadFile;
import com.pp.api.entity.User;
import com.pp.api.exception.UploadFileNotExistsException;
import com.pp.api.exception.UserNotExistsException;
import com.pp.api.repository.ProfileImageRepository;
import com.pp.api.repository.UploadFileRepository;
import com.pp.api.repository.UserRepository;
import com.pp.api.service.UserService;
import com.pp.api.service.command.UpdateUserCommand;
import com.pp.api.util.JwtAuthenticationUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

import static com.pp.api.util.JwtAuthenticationUtil.checkUserPermission;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
class UserServiceTest {

    private MockedStatic<JwtAuthenticationUtil> jwtAuthenticationUtil;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileImageRepository profileImageRepository;

    @Mock
    private UploadFileRepository uploadFileRepository;

    @Mock
    private AwsS3Property awsS3Property;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        jwtAuthenticationUtil = mockStatic(JwtAuthenticationUtil.class);
    }

    @AfterEach
    void tearDown() {
        jwtAuthenticationUtil.close();
    }

    @Test
    void 유저_정보를_수정한다() {
        Long userId = 1L;

        String nickname = "바다거북맘";

        Long profileImageFileUploadId = 1L;

        UpdateUserCommand command = new UpdateUserCommand(
                userId,
                nickname,
                profileImageFileUploadId
        );

        User user = User.builder()
                .build();

        UploadFile uploadFile = UploadFile.builder()
                .uploader(user)
                .build();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(uploadFileRepository.findById(profileImageFileUploadId))
                .thenReturn(Optional.of(uploadFile));

        userService.update(command);

        assertThat(user.getNickname()).isEqualTo(nickname);
        verify(profileImageRepository, times(1)).deleteByUserId(userId);
        verify(profileImageRepository, times(1)).save(any());
    }

    @Test
    void 수정되는_내용이_없으면_유저_정보는_수정되지_않는다() {
        Long userId = 1L;

        UpdateUserCommand command = new UpdateUserCommand(
                userId,
                null,
                null
        );

        User user = mock(User.class);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        userService.update(command);

        verify(user, never()).updateNickname(any());
        verify(uploadFileRepository, never()).findById(any());
        verify(profileImageRepository, never()).deleteByUserId(any());
        verify(profileImageRepository, never()).save(any());
    }

    @Test
    void 권한이_없는_유저는_유저_정보를_수정할수없다() {
        Long userId = 1L;

        String nickname = "바다거북맘";

        Long profileImageFileUploadId = 1L;

        UpdateUserCommand command = new UpdateUserCommand(
                userId,
                nickname,
                profileImageFileUploadId
        );

        jwtAuthenticationUtil.when(() -> checkUserPermission(userId))
                .thenThrow(AccessDeniedException.class);

        assertThatThrownBy(() -> userService.update(command))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void 존재하지않는_유저의_정보를_수정할수없다() {
        Long userId = 1L;

        String nickname = "바다거북맘";

        Long profileImageFileUploadId = 1L;

        UpdateUserCommand command = new UpdateUserCommand(
                userId,
                nickname,
                profileImageFileUploadId
        );

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.update(command))
                .isInstanceOf(UserNotExistsException.class);
    }

    @Test
    void 존재하지않는_파일을_프로필_이미지로_수정할수없다() {
        Long userId = 1L;

        String nickname = "바다거북맘";

        Long profileImageFileUploadId = 1L;

        UpdateUserCommand command = new UpdateUserCommand(
                userId,
                nickname,
                profileImageFileUploadId
        );

        User user = User.builder()
                .build();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(uploadFileRepository.findById(profileImageFileUploadId))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.update(command))
                .isInstanceOf(UploadFileNotExistsException.class);
    }

    @Test
    void 권한이_없는_파일을_프로필_이미지로_수정할수없다() {
        Long userId = 1L;

        String nickname = "바다거북맘";

        Long profileImageFileUploadId = 1L;

        UpdateUserCommand command = new UpdateUserCommand(
                userId,
                nickname,
                profileImageFileUploadId
        );

        User user = User.builder()
                .build();

        UploadFile uploadFile = UploadFile.builder()
                .uploader(user)
                .build();

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(uploadFileRepository.findById(profileImageFileUploadId))
                .thenReturn(Optional.of(uploadFile));

        jwtAuthenticationUtil.when(() -> checkUserPermission(any()))
                .thenAnswer(invocationOnMock -> null)
                .thenThrow(IllegalArgumentException.class);

        assertThatThrownBy(() -> userService.update(command))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
