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
    public ResponseEntity<?> create(
            @PathVariable(name = "postId") Long postId,
            @RequestBody @Valid CreateCommentRequest request
    ) {
        CreateCommentCommand command = new CreateCommentCommand(
                postId,
                request.content()
        );

        commentService.create(command);

        return ResponseEntity.ok()
                .build();
    }

    @PreAuthorize(value = "isAuthenticated() && hasAuthority('SCOPE_post.write')")
    @PostMapping(
            path = "/api/v1/comments/{commentId}/report",
            produces = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> report(@PathVariable(name = "commentId") Long commentId) {
        commentService.report(commentId);

        return ResponseEntity.ok()
                .build();
    }

}
