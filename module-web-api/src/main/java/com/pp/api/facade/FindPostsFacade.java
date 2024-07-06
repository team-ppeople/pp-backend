package com.pp.api.facade;

import com.pp.api.controller.dto.FindPostsRequest;
import com.pp.api.controller.dto.FindPostsResponse;
import com.pp.api.controller.dto.FindPostsResponse.PostResponse;
import com.pp.api.service.BlockUserService;
import com.pp.api.service.PostService;
import com.pp.api.service.command.FindPostsByNoOffsetQuery;
import com.pp.api.service.command.FindPostsNotInBlockedByNoOffsetQuery;
import com.pp.api.service.domain.PostOfList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.pp.api.util.JwtAuthenticationUtil.getAuthenticatedUserId;

@Component
@RequiredArgsConstructor
public class FindPostsFacade {

    private final PostService postService;

    private final BlockUserService blockUserService;

    public FindPostsResponse findPosts(FindPostsRequest request) {
        List<PostResponse> postResponses = findPostsNotInBlockedIfExist(request)
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

    private List<PostOfList> findPostsNotInBlockedIfExist(FindPostsRequest request) {
        List<Long> blockedIds = blockUserService.findBlockedIds(getAuthenticatedUserId());

        if (blockedIds.isEmpty()) {
            FindPostsByNoOffsetQuery query = FindPostsByNoOffsetQuery.of(
                    request.lastId(),
                    request.limit() != null ? request.limit() : 20
            );

            return postService.findPosts(query);
        }

        FindPostsNotInBlockedByNoOffsetQuery query = FindPostsNotInBlockedByNoOffsetQuery.of(
                request.lastId(),
                request.limit() != null ? request.limit() : 20,
                blockedIds
        );

        return postService.findPostsNotInBlockedUsers(query);
    }

}
