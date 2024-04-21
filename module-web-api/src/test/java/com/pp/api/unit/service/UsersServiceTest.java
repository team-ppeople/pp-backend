package com.pp.api.unit.service;

import com.pp.api.entity.UploadFiles;
import com.pp.api.entity.Users;
import com.pp.api.repository.ProfileImagesRepository;
import com.pp.api.repository.UploadFilesRepository;
import com.pp.api.repository.UsersRepository;
import com.pp.api.service.UsersService;
import com.pp.api.service.command.UpdateUserCommand;
import com.pp.api.util.JwtAuthenticationUtils;
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

import static com.pp.api.util.JwtAuthenticationUtils.checkUserPermission;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(value = MockitoExtension.class)
class UsersServiceTest {

    private MockedStatic<JwtAuthenticationUtils> jwtAuthenticationUtils;

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private ProfileImagesRepository profileImagesRepository;

    @Mock
    private UploadFilesRepository uploadFilesRepository;


    @InjectMocks
    private UsersService usersService;

    @BeforeEach
    void setUp() {
        jwtAuthenticationUtils = mockStatic(JwtAuthenticationUtils.class);
    }

    @AfterEach
    void tearDown() {
        jwtAuthenticationUtils.close();
    }

    @Test
    void 유저_정보를_수정한다() {
        // given
        Long userId = 1L;
        String nickname = "바다거북맘";
        Long profileImageFileUploadId = 1L;

        UpdateUserCommand command = UpdateUserCommand.of(
                nickname,
                profileImageFileUploadId
        );

        // when
        Users user = Users.builder()
                .build();

        UploadFiles uploadFile = UploadFiles.builder()
                .uploader(user)
                .build();

        when(usersRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(uploadFilesRepository.findById(profileImageFileUploadId))
                .thenReturn(Optional.of(uploadFile));

        usersService.update(
                userId,
                command
        );

        // then
        assertThat(user.getNickname()).isEqualTo(nickname);
        verify(profileImagesRepository, times(1)).deleteByUserId(userId);
        verify(profileImagesRepository, times(1)).save(any());
    }

    @Test
    void 수정되는_내용이_없으면_유저_정보는_수정되지_않는다() {
        // given
        Long userId = 1L;

        UpdateUserCommand command = UpdateUserCommand.of(
                null,
                null
        );

        // when
        Users user = mock(Users.class);

        when(usersRepository.findById(userId))
                .thenReturn(Optional.of(user));

        usersService.update(
                userId,
                command
        );

        // then
        verify(user, never()).updateNickname(any());
        verify(uploadFilesRepository, never()).findById(any());
        verify(profileImagesRepository, never()).deleteByUserId(any());
        verify(profileImagesRepository, never()).save(any());
    }

    @Test
    void 권한이_없는_유저는_유저_정보를_수정할수없다() {
        // given
        Long userId = 1L;
        String nickname = "바다거북맘";
        Long profileImageFileUploadId = 1L;

        UpdateUserCommand command = UpdateUserCommand.of(
                nickname,
                profileImageFileUploadId
        );

        // when
        jwtAuthenticationUtils.when(() -> checkUserPermission(userId))
                .thenThrow(AccessDeniedException.class);

        // then
        assertThatThrownBy(
                () -> usersService.update(
                        userId,
                        command
                )
        )
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    void 존재하지않는_유저의_정보를_수정할수없다() {
        // given
        Long userId = 1L;
        String nickname = "바다거북맘";
        Long profileImageFileUploadId = 1L;

        UpdateUserCommand command = UpdateUserCommand.of(
                nickname,
                profileImageFileUploadId
        );

        // when
        when(usersRepository.findById(userId))
                .thenReturn(Optional.empty());

        // then
        assertThatThrownBy(
                () -> usersService.update(
                        userId,
                        command
                )
        )
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 존재하지않는_파일을_프로필_이미지로_수정할수없다() {
        // given
        Long userId = 1L;
        String nickname = "바다거북맘";
        Long profileImageFileUploadId = 1L;

        UpdateUserCommand command = UpdateUserCommand.of(
                nickname,
                profileImageFileUploadId
        );

        // when
        Users user = Users.builder()
                .build();

        when(usersRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(uploadFilesRepository.findById(profileImageFileUploadId))
                .thenReturn(Optional.empty());

        // then
        assertThatThrownBy(
                () -> usersService.update(
                        userId,
                        command
                )
        )
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 권한이_없는_파일을_프로필_이미지로_수정할수없다() {
        // given
        Long userId = 1L;
        String nickname = "바다거북맘";
        Long profileImageFileUploadId = 1L;

        UpdateUserCommand command = UpdateUserCommand.of(
                nickname,
                profileImageFileUploadId
        );

        // when
        Users user = Users.builder()
                .build();

        UploadFiles uploadFile = UploadFiles.builder()
                .uploader(user)
                .build();

        when(usersRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(uploadFilesRepository.findById(profileImageFileUploadId))
                .thenReturn(Optional.of(uploadFile));

        jwtAuthenticationUtils.when(() -> checkUserPermission(any()))
                .thenAnswer(invocationOnMock -> null)
                .thenThrow(IllegalArgumentException.class);

        // then
        assertThatThrownBy(
                () -> usersService.update(
                        userId,
                        command
                )
        )
                .isInstanceOf(IllegalArgumentException.class);
    }

}
