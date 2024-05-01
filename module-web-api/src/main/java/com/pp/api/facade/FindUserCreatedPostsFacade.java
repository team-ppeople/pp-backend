package com.pp.api.facade;

import com.pp.api.controller.dto.FindUserCreatedPostsRequest;
import com.pp.api.controller.dto.FindUserCreatedPostsResponse;
import com.pp.api.controller.dto.UserCreatedPostResponse;
import com.pp.api.service.PostService;
import com.pp.api.service.command.FindPostsByNoOffsetQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindUserCreatedPostsFacade {

    private final PostService postService;

    public FindUserCreatedPostsResponse findUserCreatedPosts(
            Long userId,
            FindUserCreatedPostsRequest request
    ) {
        FindPostsByNoOffsetQuery query = FindPostsByNoOffsetQuery.of(
                request.lastId(),
                request.limit() != null ? request.limit() : 20
        );

        List<UserCreatedPostResponse> userCreatedPostsResponses = postService.findPostOfListByCreatorId(
                        userId,
                        query
                )
                .stream()
                .map(post -> new UserCreatedPostResponse(
                        post.id(),
                        post.thumbnailUrl(),
                        post.title(),
                        post.createdDate()
                ))
                .toList();

        return new FindUserCreatedPostsResponse(userCreatedPostsResponses);
    }

}
