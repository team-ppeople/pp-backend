package com.pp.api.facade;

import com.pp.api.controller.dto.FindCommentsRequest;
import com.pp.api.controller.dto.FindCommentsResponse;
import com.pp.api.controller.dto.FindCommentsResponse.CommentResponse;
import com.pp.api.controller.dto.FindCommentsResponse.CreatorResponse;
import com.pp.api.service.CommentService;
import com.pp.api.service.UserService;
import com.pp.api.service.command.FindCommentsByNoOffsetQuery;
import com.pp.api.service.domain.CommentOfList;
import com.pp.api.service.domain.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toUnmodifiableMap;

@Component
@RequiredArgsConstructor
public class FindCommentsFacade {

    private final CommentService commentService;

    private final UserService userService;

    public FindCommentsResponse findComments(
            Long postId,
            FindCommentsRequest request
    ) {
        FindCommentsByNoOffsetQuery query = FindCommentsByNoOffsetQuery.of(
                postId,
                request.lastId(),
                request.limit() != null ? request.limit() : 20
        );

        List<CommentOfList> comments = commentService.findComments(query);

        List<Long> creatorIds = comments.stream()
                .map(CommentOfList::creatorId)
                .toList();

        Map<Long, CreatorResponse> creatorResponses = userService.findUserProfilesByIds(creatorIds)
                .stream()
                .collect(
                        toUnmodifiableMap(
                                UserProfile::id,
                                userProfile -> new CreatorResponse(
                                        userProfile.id(),
                                        userProfile.nickname(),
                                        userProfile.profileImageUrl()
                                )
                        )
                );

        List<CommentResponse> commentResponses = comments.stream()
                .map(comment ->
                        new CommentResponse(
                                comment.id(),
                                comment.content(),
                                comment.createdDate(),
                                creatorResponses.get(comment.creatorId())
                        )
                )
                .toList();

        return new FindCommentsResponse(commentResponses);
    }

}
