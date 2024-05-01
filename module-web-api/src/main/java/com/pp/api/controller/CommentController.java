package com.pp.api.controller;

import com.pp.api.controller.dto.CreateCommentRequest;
import com.pp.api.service.CommentService;
import com.pp.api.service.command.CreateCommentCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_post.write')")
    @PostMapping(
            path = "/api/v1/posts/{postId}/comments",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> createPost(
            @PathVariable(name = "postId") Long postId,
            @RequestBody @Valid CreateCommentRequest request
    ) {
        CreateCommentCommand command = CreateCommentCommand.of(request.content());

        commentService.create(
                postId,
                command
        );

        return ResponseEntity.ok()
                .build();
    }

}
