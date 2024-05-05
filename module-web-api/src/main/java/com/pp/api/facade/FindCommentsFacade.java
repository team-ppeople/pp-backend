package com.pp.api.facade;

import com.pp.api.controller.dto.FindCommentsRequest;
import com.pp.api.controller.dto.FindCommentsResponse;
import com.pp.api.controller.dto.FindCommentsResponse.CommentResponse;
import com.pp.api.service.CommentService;
import com.pp.api.service.command.FindCommentsByNoOffsetQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindCommentsFacade {

    private final CommentService commentService;

    public FindCommentsResponse findComments(
            Long postId,
            FindCommentsRequest request
    ) {
        FindCommentsByNoOffsetQuery query = FindCommentsByNoOffsetQuery.of(
                postId,
                request.lastId(),
                request.limit() != null ? request.limit() : 20
        );

        List<CommentResponse> commentResponses = commentService.findComments(query)
                .stream()
                .map(comment ->
                        new CommentResponse(
                                comment.id(),
                                comment.content(),
                                comment.createdDate()
                        )
                )
                .toList();

        return new FindCommentsResponse(commentResponses);
    }

}
