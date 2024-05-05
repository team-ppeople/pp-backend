package com.pp.api.controller;

import com.pp.api.controller.dto.FindNoticesRequest;
import com.pp.api.controller.dto.FindNoticesResponse;
import com.pp.api.controller.dto.RestResponseWrapper;
import com.pp.api.facade.FindNoticesFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class NoticeController {

    private final FindNoticesFacade findNoticesFacade;

    @GetMapping(
            path = "/api/v1/notices",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> findNotices(@Valid FindNoticesRequest request) {
        FindNoticesResponse response = findNoticesFacade.findNotices(request);

        return ResponseEntity.ok(RestResponseWrapper.from(response));
    }

}
