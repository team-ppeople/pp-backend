package com.pp.api.integration.controller;

import com.pp.api.controller.dto.UpdateUserRequest;
import com.pp.api.entity.UploadFile;
import com.pp.api.entity.User;
import com.pp.api.fixture.UploadFileFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.integration.AbstractIntegrationTestContext;
import com.pp.api.integration.WithMockUserJwt;
import com.pp.api.repository.UploadFileRepository;
import com.pp.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static java.lang.Long.MAX_VALUE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends AbstractIntegrationTestContext {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Test
    void 유저_정보를_수정한다() throws Exception {
        User user = userRepository.save(UserFixture.of());

        UploadFile uploadFile = uploadFileRepository.save(UploadFileFixture.profileImageFileOfUploader(user));

        String nickname = "신봄";

        UpdateUserRequest request = new UpdateUserRequest(
                nickname,
                uploadFile.getId()
        );

        mockMvc.perform(
                        patch(
                                "/api/v1/users/{userId}",
                                user.getId()
                        )
                                .header(AUTHORIZATION, jwtTestUtil.createBearerToken(user))
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(request))
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void 토큰이없으면_유저_정보를_수정할수없다() throws Exception {
        User user = userRepository.save(UserFixture.of());

        UploadFile uploadFile = uploadFileRepository.save(UploadFileFixture.profileImageFileOfUploader(user));

        String nickname = "신봄";

        UpdateUserRequest request = new UpdateUserRequest(
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
                                .content(objectMapper.writeValueAsBytes(request))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @WithMockUserJwt
    @Test
    void 권한이없는_토큰으로_유저_정보를_수정할수없다() throws Exception {
        User user = userRepository.save(UserFixture.of());

        UploadFile uploadFile = uploadFileRepository.save(UploadFileFixture.profileImageFileOfUploader(user));

        String nickname = "신봄";

        UpdateUserRequest request = new UpdateUserRequest(
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
                                .content(objectMapper.writeValueAsBytes(request))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void 권한이없는_유저_정보를_수정할수없다() throws Exception {
        User user = userRepository.save(UserFixture.of());

        UploadFile uploadFile = uploadFileRepository.save(UploadFileFixture.profileImageFileOfUploader(user));

        String nickname = "신봄";

        UpdateUserRequest request = new UpdateUserRequest(
                nickname,
                uploadFile.getId()
        );

        mockMvc.perform(
                        patch(
                                "/api/v1/users/{userId}",
                                MAX_VALUE
                        )
                                .header(AUTHORIZATION, jwtTestUtil.createBearerToken(user))
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(request))
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

        UpdateUserRequest request = new UpdateUserRequest(
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
                                .content(objectMapper.writeValueAsBytes(request))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void 존재하지않는_파일을_프로필_이미지로_수정할수없다() throws Exception {
        User user = userRepository.save(UserFixture.of());

        String nickname = "신봄";

        UpdateUserRequest request = new UpdateUserRequest(
                nickname,
                MAX_VALUE
        );

        mockMvc.perform(
                        patch(
                                "/api/v1/users/{userId}",
                                user.getId()
                        )
                                .header(AUTHORIZATION, jwtTestUtil.createBearerToken(user))
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(request))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void 권한이_없는_파일을_프로필_이미지로_수정할수없다() throws Exception {
        User user = userRepository.save(UserFixture.of());

        User otherUser = userRepository.save(
                UserFixture.from(
                        "바다거북맘",
                        "sea-turtles@gmail.com"
                )
        );

        UploadFile uploadFile = uploadFileRepository.save(UploadFileFixture.profileImageFileOfUploader(otherUser));

        String nickname = "신봄";

        UpdateUserRequest request = new UpdateUserRequest(
                nickname,
                uploadFile.getId()
        );

        mockMvc.perform(
                        patch(
                                "/api/v1/users/{userId}",
                                user.getId()
                        )
                                .header(AUTHORIZATION, jwtTestUtil.createBearerToken(user))
                                .accept(APPLICATION_JSON)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(request))
                )
                .andDo(print())
                .andExpect(status().isForbidden());
    }

}
