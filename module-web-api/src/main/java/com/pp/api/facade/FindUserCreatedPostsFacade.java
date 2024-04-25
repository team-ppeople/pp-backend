package com.pp.api.facade;

import com.pp.api.controller.dto.FindUserCreatedPostRequest;
import com.pp.api.controller.dto.FindUserCreatedPostResponse;
import com.pp.api.controller.dto.UserCreatedPostResponse;
import com.pp.api.service.PostService;
import com.pp.api.service.command.FindPostOfListByNoOffsetQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindUserCreatedPostsFacade {

    private final PostService postService;

    public FindUserCreatedPostResponse findUserCreatedPosts(
            Long userId,
            FindUserCreatedPostRequest request
    ) {
        FindPostOfListByNoOffsetQuery query = FindPostOfListByNoOffsetQuery.of(
                request.lastId(),
                request.limit() != null ? request.limit() : 20
        );

        List<UserCreatedPostResponse> userCreatedPostResponses = postService.findPostOfListByCreateId(
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

        return new FindUserCreatedPostResponse(userCreatedPostResponses);
    }

}
