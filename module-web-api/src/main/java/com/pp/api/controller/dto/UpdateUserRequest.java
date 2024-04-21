package com.pp.api.controller.dto;

import lombok.Getter;

@Getter
public class UpdateUserRequest {

    private String nickname;

    private Long profileImageFileUploadId;

}
