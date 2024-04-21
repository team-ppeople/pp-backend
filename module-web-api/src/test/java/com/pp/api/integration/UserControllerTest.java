package com.pp.api.integration;

import com.pp.api.config.WithMockUserJwt;
import com.pp.api.entity.UploadFiles;
import com.pp.api.entity.Users;
import com.pp.api.repository.ProfileImagesRepository;
import com.pp.api.repository.UploadFilesRepository;
import com.pp.api.repository.UsersRepository;
import com.pp.api.service.command.UpdateUserCommand;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.pp.api.entity.enums.UploadFileContentTypes.IMAGE_JPEG;
import static com.pp.api.entity.enums.UploadFileTypes.PROFILE_IMAGE;
import static java.lang.Long.MAX_VALUE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractIntegrationTestContext {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UploadFilesRepository uploadFilesRepository;

    @Autowired
    private ProfileImagesRepository profileImagesRepository;

    @AfterEach
    void tearDown() {
        profileImagesRepository.deleteAllInBatch();
        uploadFilesRepository.deleteAllInBatch();
        usersRepository.deleteAllInBatch();
    }

    @Test
    void 유저_정보를_수정한다() throws Exception {
        Users user = createAndSaveUser();
        UploadFiles uploadFile = createAndSaveUploadFile(user);
        String nickname = "신봄";

        UpdateUserCommand command = UpdateUserCommand.of(
                nickname,
                uploadFile.getId()
        );

        String bearerToken = jwtTestUtils.createBearerToken(user);
        mockMvc.perform(
                        patch(
                                "/api/v1/users/{userId}",
                                user.getId()
                        )
                                .header(AUTHORIZATION, bearerToken)
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(command))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 토큰이없으면_유저_정보를_수정할수없다() throws Exception {
        Users user = createAndSaveUser();
        UploadFiles uploadFile = createAndSaveUploadFile(user);
        String nickname = "신봄";

        UpdateUserCommand command = UpdateUserCommand.of(
                nickname,
                uploadFile.getId()
        );

        mockMvc.perform(
                        patch(
                                "/api/v1/users/{userId}",
                                user.getId()
                        )
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(command))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUserJwt
    @Test
    void 권한이없는_토큰으로_유저_정보를_수정할수없다() throws Exception {
        Users user = createAndSaveUser();
        UploadFiles uploadFile = createAndSaveUploadFile(user);
        String nickname = "신봄";

        UpdateUserCommand command = UpdateUserCommand.of(
                nickname,
                uploadFile.getId()
        );

        mockMvc.perform(
                        patch(
                                "/api/v1/users/{userId}",
                                user.getId()
                        )
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(command))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void 권한이없는_유저_정보를_수정할수없다() throws Exception {
        Users user = createAndSaveUser();
        UploadFiles uploadFile = createAndSaveUploadFile(user);
        String nickname = "신봄";

        UpdateUserCommand command = UpdateUserCommand.of(
                nickname,
                uploadFile.getId()
        );

        mockMvc.perform(
                        patch(
                                "/api/v1/users/{userId}",
                                MAX_VALUE
                        )
                                .header(AUTHORIZATION, jwtTestUtils.createBearerToken(user))
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(command))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUserJwt(
            userId = 1L,
            scopes = {"user.read", "user.write"}
    )
    @Test
    void 존재하지않는_유저의_정보를_수정할수없다() throws Exception {
        String nickname = "신봄";

        UpdateUserCommand command = UpdateUserCommand.of(
                nickname,
                MAX_VALUE
        );

        mockMvc.perform(
                        patch(
                                "/api/v1/users/{userId}",
                                1L
                        )
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(command))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void 존재하지않는_파일을_프로필_이미지로_수정할수없다() throws Exception {
        Users user = createAndSaveUser();
        String nickname = "신봄";

        UpdateUserCommand command = UpdateUserCommand.of(
                nickname,
                MAX_VALUE
        );

        mockMvc.perform(
                        patch(
                                "/api/v1/users/{userId}",
                                user.getId()
                        )
                                .header(AUTHORIZATION, jwtTestUtils.createBearerToken(user))
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(command))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void 권한이_없는_파일을_프로필_이미지로_수정할수없다() throws Exception {
        Users user = createAndSaveUser();
        UploadFiles uploadFile = createAndSaveUploadFile(createAndSaveOtherUser());
        String nickname = "신봄";

        UpdateUserCommand command = UpdateUserCommand.of(
                nickname,
                uploadFile.getId()
        );

        mockMvc.perform(
                        patch(
                                "/api/v1/users/{userId}",
                                user.getId()
                        )
                                .header(AUTHORIZATION, jwtTestUtils.createBearerToken(user))
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(command))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    Users createAndSaveUser() {
        Users user = Users.builder()
                .nickname("sinbom")
                .email("dev.sinbom@gmail.com")
                .build();

        return usersRepository.save(user);
    }

    Users createAndSaveOtherUser() {
        Users user = Users.builder()
                .nickname("바다거북맘")
                .email("sea-turtles@gmail.com")
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

}
