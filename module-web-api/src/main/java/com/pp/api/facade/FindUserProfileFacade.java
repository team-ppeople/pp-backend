package com.pp.api.facade;

import com.pp.api.controller.dto.FindUserProfileResponse;
import com.pp.api.controller.dto.UserCreatedPostResponse;
import com.pp.api.service.PostService;
import com.pp.api.service.UserService;
import com.pp.api.service.command.FindUserCreatedPostsByNoOffsetQuery;
import com.pp.api.service.domain.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FindUserProfileFacade {

    private final UserService userService;

    private final PostService postService;

    public FindUserProfileResponse findUserProfile(Long userId) {
        UserProfile userProfile = userService.findUserProfileById(userId);

        long postCount = postService.countByCreateId(userId);

        FindUserCreatedPostsByNoOffsetQuery query = FindUserCreatedPostsByNoOffsetQuery.firstPage(
                userId,
                20
        );

        List<UserCreatedPostResponse> userCreatedPostResponses = postService.findUserCreatedPosts(query)
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

        long thumbsUpCount = postService.countUserPostThumbsUpByPostId(userId);

        return new FindUserProfileResponse(
                userProfile.id(),
                userProfile.nickname(),
                userProfile.profileImageUrl(),
                postCount,
                thumbsUpCount,
                userCreatedPostResponses
        );
    }

}
