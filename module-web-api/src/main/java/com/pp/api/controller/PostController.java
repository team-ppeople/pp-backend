package com.pp.api.controller;

import com.pp.api.controller.dto.*;
import com.pp.api.facade.FindPostDetailFacade;
import com.pp.api.facade.FindPostsFacade;
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

import static com.pp.api.controller.dto.RestResponseWrapper.empty;
import static com.pp.api.controller.dto.RestResponseWrapper.from;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final FindUserCreatedPostsFacade findUserCreatedPostsFacade;

    private final FindPostsFacade findPostsFacade;

    private final FindPostDetailFacade findPostDetailFacade;

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

        return ok(from(response));
    }

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_post.write')")
    @PostMapping(
            path = "/api/v1/posts",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> create(@RequestBody @Valid CreatePostRequest request) {
        CreatePostCommand command = new CreatePostCommand(
                request.title(),
                request.content(),
                request.postImageFileUploadIds()
        );

        CreatedPost createdPost = postService.create(command);

        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{postId}")
                .buildAndExpand(createdPost.id())
                .toUri();

        return created(location)
                .body(empty());
    }

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_post.read') && hasAuthority('SCOPE_post.write')")
    @DeleteMapping(
            path = "/api/v1/posts/{postId}",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> delete(@PathVariable(name = "postId") Long postId) {
        postService.deleteById(postId);

        return ok(empty());
    }

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_post.read')")
    @GetMapping(
            path = "/api/v1/posts",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> findPostDetail(@Valid FindPostsRequest request) {
        FindPostsResponse response = findPostsFacade.findPosts(request);

        return ok(from(response));
    }

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_post.read')")
    @GetMapping(
            path = "/api/v1/posts/{postId}",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> findPostDetail(@PathVariable(name = "postId") Long postId) {
        FindPostDetailResponse response = findPostDetailFacade.findPostDetail(postId);

        return ok(from(response));
    }

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_post.write')")
    @PostMapping(
            path = "/api/v1/posts/{postId}/report",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> report(@PathVariable(name = "postId") Long postId) {
        postService.report(postId);

        return ok(empty());
    }

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_post.write')")
    @PostMapping(
            path = "/api/v1/posts/{postId}/thumbs-up",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> thumbsUp(@PathVariable(name = "postId") Long postId) {
        postService.thumbsUp(postId);

        return ok(empty());
    }

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_post.write')")
    @PostMapping(
            path = "/api/v1/posts/{postId}/thumbs-sideways",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> thumbsSideways(@PathVariable(name = "postId") Long postId) {
        postService.thumbsSideways(postId);

        return ok(empty());
    }

}
