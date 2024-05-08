package com.pp.api.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
@RequiredArgsConstructor
public class PostUserActionRepositoryImpl implements PostUserActionRepository {

    private final StringRedisTemplate stringRedisTemplate;

    @Qualifier(value = "postThumbsUpScript")
    private final RedisScript<Void> postThumbsUpScript;

    @Qualifier(value = "postThumbsSidewaysScript")
    private final RedisScript<Void> postThumbsSidewaysScript;

    @Override
    public void thumbsUp(
            Long postId,
            Long creatorId,
            Long actorId
    ) {
        stringRedisTemplate.execute(
                postThumbsUpScript,
                Arrays.asList(
                        parsePostThumbsUpKey(postId),
                        parseUserPostThumbsUpCountKey(creatorId)
                ),
                parsePostThumbsUpValue(actorId)
        );
    }

    @Override
    public void thumbsSideways(
            Long postId,
            Long creatorId,
            Long actorId
    ) {
        stringRedisTemplate.execute(
                postThumbsSidewaysScript,
                Arrays.asList(
                        parsePostThumbsUpKey(postId),
                        parseUserPostThumbsUpCountKey(creatorId)
                ),
                parsePostThumbsUpValue(actorId)
        );
    }

    private String parsePostThumbsUpKey(Long postId) {
        return "post:" + postId + ":thumbs-up";
    }

    private String parseUserPostThumbsUpCountKey(Long creatorId) {
        return "user:" + creatorId + ":post-thumbs-up-count";
    }

    private String parsePostThumbsUpValue(Long actorId) {
        return "user_" + actorId;
    }

}
