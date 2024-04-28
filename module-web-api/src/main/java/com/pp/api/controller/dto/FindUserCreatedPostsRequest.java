package com.pp.api.controller.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record FindUserCreatedPostsRequest(
        Long lastId,
        @Min(value = 10, message = "조회 허용 갯수는 최소 {value}개 입니다.")
        @Max(value = 100, message = "조회 허용 갯수는 최대 {value}개 입니다.")
        Integer limit
) {
}
