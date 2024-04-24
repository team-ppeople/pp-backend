package com.pp.api.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@Getter
@RequiredArgsConstructor(access = PRIVATE)
public class RestResponseWrapper<T> {

    private final T data;

    public static <T> RestResponseWrapper<T> from(T data) {
        return new RestResponseWrapper<>(data);
    }

}
