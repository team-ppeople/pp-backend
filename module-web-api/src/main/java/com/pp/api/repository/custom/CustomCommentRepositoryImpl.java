package com.pp.api.repository.custom;

import com.pp.api.entity.Comment;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

import static com.pp.api.entity.QComment.comment;

public class CustomCommentRepositoryImpl extends QuerydslRepositorySupport implements CustomCommentRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CustomCommentRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Comment.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findByPostId(
            Long postId,
            Long lastId,
            int limit
    ) {
        return from(comment)
                .where(
                        comment.post.id.eq(postId),
                        lowerThanLastId(lastId)
                )
                .orderBy(comment.id.desc())
                .limit(limit)
                .fetch();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> findNotInBlockedUsersByPostId(
            Long postId,
            Long lastId,
            int limit,
            List<Long> blockedIds
    ) {
        return from(comment)
                .where(
                        comment.post.id.eq(postId),
                        lowerThanLastId(lastId),
                        notInBlockedUserIds(blockedIds)
                )
                .orderBy(comment.id.desc())
                .limit(limit)
                .fetch();
    }

    @Transactional(readOnly = true)
    @Override
    public long countByPostId(Long postId) {
        return from(comment)
                .where(comment.post.id.eq(postId))
                .fetchCount();
    }

    @Transactional(readOnly = true)
    @Override
    public long countNotInBlockedUserByPostId(
            Long postId,
            List<Long> blockedIds
    ) {
        return from(comment)
                .where(
                        comment.post.id.eq(postId),
                        notInBlockedUserIds(blockedIds)
                )
                .fetchCount();
    }

    private BooleanExpression lowerThanLastId(Long lastId) {
        if (lastId == null) {
            return null;
        }

        return comment.id.lt(lastId);
    }

    private BooleanExpression notInBlockedUserIds(List<Long> blockedUserIds) {
        if (CollectionUtils.isEmpty(blockedUserIds)) {
            return null;
        }
        return comment.creator.id.notIn(blockedUserIds);
    }

}
