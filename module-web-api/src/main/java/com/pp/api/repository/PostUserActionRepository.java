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

}
