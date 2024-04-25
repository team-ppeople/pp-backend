package com.pp.api.controller;

import com.pp.api.controller.dto.FindUserCreatedPostRequest;
import com.pp.api.controller.dto.FindUserCreatedPostResponse;
import com.pp.api.controller.dto.RestResponseWrapper;
import com.pp.api.facade.FindUserCreatedPostsFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final FindUserCreatedPostsFacade findUserCreatedPostsFacade;

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_user.read') && hasAuthority('SCOPE_post.read')")
    @GetMapping(
            path = "/api/v1/users/{userId}/posts",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> findUserProfile(
            @PathVariable(name = "userId") Long userId,
            FindUserCreatedPostRequest request
    ) {
        FindUserCreatedPostResponse response = findUserCreatedPostsFacade.findUserCreatedPosts(
                userId,
                request
        );

        return ResponseEntity.ok(RestResponseWrapper.from(response));
    }

}
