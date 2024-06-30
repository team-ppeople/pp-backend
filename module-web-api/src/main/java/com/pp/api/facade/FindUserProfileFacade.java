package com.pp.api.facade;

import com.pp.api.controller.dto.FindUserProfileResponse;
import com.pp.api.controller.dto.UserCreatedPostResponse;
import com.pp.api.service.BlockUserService;
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

    private final BlockUserService blockUserService;

    public FindUserProfileResponse findUserProfile(Long userId) {
        UserProfile userProfile = userService.findUserProfileById(userId);

        long postCount = postService.countByCreateId(userId);

        FindUserCreatedPostsByNoOffsetQuery query = FindUserCreatedPostsByNoOffsetQuery.firstPage(
                userId,
                20
        );

        long thumbsUpCount = postService.countUserPostThumbsUpByPostId(userId);

        if (blockUserService.isBlockedUser(userId)) {
            return FindUserProfileResponse.blockedUser(
                    userProfile.id(),
                    userProfile.nickname(),
                    userProfile.profileImageUrl(),
                    postCount,
                    thumbsUpCount
            );
        }

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

        return FindUserProfileResponse.of(
                userProfile.id(),
                userProfile.nickname(),
                userProfile.profileImageUrl(),
                postCount,
                thumbsUpCount,
                userCreatedPostResponses
        );
    }

}
