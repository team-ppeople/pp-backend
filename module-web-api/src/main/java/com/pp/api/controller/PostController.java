package com.pp.api.controller;

import com.pp.api.controller.dto.CreatePostRequest;
import com.pp.api.controller.dto.FindUserCreatedPostsRequest;
import com.pp.api.controller.dto.FindUserCreatedPostsResponse;
import com.pp.api.controller.dto.RestResponseWrapper;
import com.pp.api.facade.FindUserCreatedPostsFacade;
import com.pp.api.service.PostService;
import com.pp.api.service.command.CreatePostCommand;
import com.pp.api.service.domain.CreatedPost;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final FindUserCreatedPostsFacade findUserCreatedPostsFacade;

    private final PostService postService;

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_post.read')")
    @GetMapping(
            path = "/api/v1/users/{userId}/posts",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> findUserCreatedPosts(
            @PathVariable(name = "userId") Long userId,
            @Valid FindUserCreatedPostsRequest request
    ) {
        FindUserCreatedPostsResponse response = findUserCreatedPostsFacade.findUserCreatedPosts(
                userId,
                request
        );

        return ResponseEntity.ok(RestResponseWrapper.from(response));
    }

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_post.write')")
    @PostMapping(
            path = "/api/v1/posts",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createPost(@RequestBody @Valid CreatePostRequest request) {
        CreatePostCommand command = CreatePostCommand.of(
                request.title(),
                request.content(),
                request.postImageFileUploadIds()
        );

        CreatedPost createdPost = postService.create(command);

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{postId}")
                .buildAndExpand(createdPost.id())
                .toUri();

        return ResponseEntity.created(location)
                .build();
    }

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_post.write')")
    @PostMapping(
            path = "/api/v1/posts/{postId}/report",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> reportPost(@PathVariable(name = "postId") Long postId) {
        postService.report(postId);

        return ResponseEntity.ok()
                .build();
    }

}
