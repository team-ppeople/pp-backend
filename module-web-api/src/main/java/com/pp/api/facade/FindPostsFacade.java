package com.pp.api.facade;

import com.pp.api.controller.dto.FindPostsRequest;
import com.pp.api.controller.dto.FindPostsResponse;
import com.pp.api.controller.dto.FindPostsResponse.PostResponse;
import com.pp.api.service.PostService;
import com.pp.api.service.command.FindPostsByNoOffsetQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindPostsFacade {

    private final PostService postService;

    public FindPostsResponse findPosts(FindPostsRequest request) {
        FindPostsByNoOffsetQuery query = FindPostsByNoOffsetQuery.of(
                request.lastId(),
                request.limit() != null ? request.limit() : 20
        );

        List<PostResponse> postResponses = postService.findPosts(query)
                .stream()
                .map(post ->
                        new PostResponse(
                                post.id(),
                                post.thumbnailUrl(),
                                post.title(),
                                post.createdDate()
                        )
                )
                .toList();

        return new FindPostsResponse(postResponses);
    }

}
