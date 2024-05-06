package com.pp.api.facade;

import com.pp.api.controller.dto.FindPostDetailResponse;
import com.pp.api.controller.dto.FindPostDetailResponse.CreatorResponse;
import com.pp.api.controller.dto.FindPostDetailResponse.UserActionHistoryResponse;
import com.pp.api.service.CommentService;
import com.pp.api.service.PostService;
import com.pp.api.service.UserService;
import com.pp.api.service.domain.PostDetail;
import com.pp.api.service.domain.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class FindPostDetailFacade {

    private final PostService postService;

    private final CommentService commentService;

    private final UserService userService;

    @Transactional(readOnly = true)
    public FindPostDetailResponse findPostDetail(Long postId) {
        PostDetail postDetail = postService.findPostDetailById(postId);

        UserProfile userProfile = userService.findUserProfileById(postDetail.creatorId());

        boolean isReported = postService.isReportedById(postId);

        boolean isThumbsUpped = false; // TODO 좋아요 구현 이후 구현

        long thumbsUpCount = 0; // TODO 좋아요 구현 이후 구현

        long commentCount = commentService.countByPostId(postId);

        CreatorResponse creatorResponse = new CreatorResponse(
                userProfile.id(),
                userProfile.nickname(),
                userProfile.profileImageUrl()
        );

        UserActionHistoryResponse userActionHistoryResponse = new UserActionHistoryResponse(
                isThumbsUpped,
                isReported
        );

        return new FindPostDetailResponse(
                postDetail.id(),
                postDetail.postImageUrls(),
                postDetail.title(),
                postDetail.content(),
                postDetail.createdDate(),
                thumbsUpCount,
                commentCount,
                creatorResponse,
                userActionHistoryResponse
        );
    }

}
