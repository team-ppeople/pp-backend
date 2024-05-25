package com.pp.api.repository;

public interface PostUserActionRepository {

    void thumbsUp(
            Long postId,
            Long creatorId,
            Long actorId
    );

    void thumbsSideways(
            Long postId,
            Long creatorId,
            Long actorId
    );

    long countThumbsUpByPostId(Long postId);

    long countUserPostThumbsUpByUserId(Long userId);

    boolean isThumbsUppedByUserId(
            Long postId,
            Long actorId
    );

    void deleteUserPostThumbsUpByUserId(Long userId);

}
