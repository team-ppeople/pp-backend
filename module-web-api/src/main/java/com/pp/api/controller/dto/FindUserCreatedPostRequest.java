package com.pp.api.controller.dto;

import org.hibernate.validator.constraints.Range;

public record FindUserCreatedPostRequest(
        Long lastId,
        @Range(min = 10, max = 100, message = "조회 허용 갯수는 최소 {min}개 최대 {max}개 입니다.")
        Integer limit
) {
}
