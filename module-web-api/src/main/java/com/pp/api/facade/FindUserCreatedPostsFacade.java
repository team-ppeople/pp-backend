package com.pp.api.facade;

import com.pp.api.controller.dto.FindUserCreatedPostsRequest;
import com.pp.api.controller.dto.FindUserCreatedPostsResponse;
import com.pp.api.controller.dto.UserCreatedPostResponse;
import com.pp.api.exception.CannotAccessBlockUserException;
import com.pp.api.service.BlockUserService;
import com.pp.api.service.PostService;
import com.pp.api.service.command.FindUserCreatedPostsByNoOffsetQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindUserCreatedPostsFacade {

    private final PostService postService;

    private final BlockUserService blockUserService;

    public FindUserCreatedPostsResponse findUserCreatedPosts(
            Long userId,
            FindUserCreatedPostsRequest request
    ) {
        if (blockUserService.isBlockedUser(userId)) {
            throw CannotAccessBlockUserException.ofAccessPostMessage();
        }

        FindUserCreatedPostsByNoOffsetQuery query = FindUserCreatedPostsByNoOffsetQuery.of(
                userId,
                request.lastId(),
                request.limit() != null ? request.limit() : 20
        );

        List<UserCreatedPostResponse> userCreatedPostsResponses = postService.findUserCreatedPosts(query)
                .stream()
                .map(post ->
                        new UserCreatedPostResponse(
                                post.id(),
                                post.thumbnailUrl(),
                                post.title(),
                                post.createdDate()
                        )
                )
                .toList();

        return new FindUserCreatedPostsResponse(userCreatedPostsResponses);
    }

}
