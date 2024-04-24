package com.pp.api.facade;

import com.pp.api.controller.dto.FindUserProfileResponse;
import com.pp.api.controller.dto.FindUserProfileResponse.UserCreatedPostResponse;
import com.pp.api.service.PostService;
import com.pp.api.service.UserService;
import com.pp.api.service.command.FindPostOfListByNoOffsetQuery;
import com.pp.api.service.domain.UserWithProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindUserProfileFacade {

    private final UserService userService;

    private final PostService postService;

    public FindUserProfileResponse findUserProfile(Long userId) {
        UserWithProfile userWithProfile = userService.findWithProfileByUserId(userId);

        long postCount = postService.countByCreateId(userId);

        FindPostOfListByNoOffsetQuery query = FindPostOfListByNoOffsetQuery.firstPage(20);

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

        int thumbsUpCount = 0; // TODO 좋아요 구현이후 적용

        return new FindUserProfileResponse(
                userWithProfile.id(),
                userWithProfile.nickname(),
                userWithProfile.profileImageUrl(),
                postCount,
                thumbsUpCount,
                userCreatedPostResponses
        );
    }

}
