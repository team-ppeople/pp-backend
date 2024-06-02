package com.pp.api.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;

import static java.lang.Boolean.TRUE;
import static java.util.Arrays.asList;

@Repository
@RequiredArgsConstructor
public class PostUserActionRepositoryImpl implements PostUserActionRepository {

    private final StringRedisTemplate stringRedisTemplate;

    @Qualifier(value = "thumbsUpPostScript")
    private final RedisScript<Void> thumbsUpPostScript;

    @Qualifier(value = "thumbsSidewaysPostScript")
    private final RedisScript<Void> thumbsSidewaysPostScript;

    @Qualifier(value = "deleteThumbsUpPostScript")
    private final RedisScript<Void> deleteThumbsUpPostScript;

    @Override
    public void thumbsUp(
            Long postId,
            Long creatorId,
            Long actorId
    ) {
        stringRedisTemplate.execute(
                thumbsUpPostScript,
                asList(
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
                thumbsSidewaysPostScript,
                asList(
                        parsePostThumbsUpKey(postId),
                        parseUserPostThumbsUpCountKey(creatorId)
                ),
                parsePostThumbsUpValue(actorId)
        );
    }

    @Override
    public void deleteThumbsUp(Long postId, Long creatorId) {
        stringRedisTemplate.execute(
                deleteThumbsUpPostScript,
                asList(
                        parsePostThumbsUpKey(postId),
                        parseUserPostThumbsUpCountKey(creatorId)
                )
        );
    }

    @Override
    public long countThumbsUpByPostId(Long postId) {
        Long count = stringRedisTemplate.opsForSet()
                .size(parsePostThumbsUpKey(postId));

        return count != null ? count : 0;
    }

    @Override
    public long countUserPostThumbsUpByUserId(Long userId) {
        String count = stringRedisTemplate.opsForValue()
                .get(parseUserPostThumbsUpCountKey(userId));

        return count != null ? Long.parseLong(count) : 0;
    }

    @Override
    public boolean isThumbsUppedByUserId(
            Long postId,
            Long userId
    ) {
        Boolean isThumbsUpped = stringRedisTemplate.opsForSet()
                .isMember(
                        parsePostThumbsUpKey(postId),
                        parsePostThumbsUpValue(userId)
                );

        return TRUE.equals(isThumbsUpped);
    }

    @Override
    public void deleteUserPostThumbsUpByUserId(Long userId) {
        stringRedisTemplate.delete(parseUserPostThumbsUpCountKey(userId));
    }

    private String parsePostThumbsUpKey(Long postId) {
        return "post:" + postId + ":thumbs-up";
    }

    private String parseUserPostThumbsUpCountKey(Long creatorId) {
        return "user:" + creatorId + ":post-thumbs-up-count";
    }

    private String parsePostThumbsUpValue(Long userId) {
        return "user_" + userId;
    }

}
