package com.pp.api.repository.custom;

import com.pp.api.entity.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pp.api.entity.QPost.post;

public class CustomPostRepositoryImpl extends QuerydslRepositorySupport implements CustomPostRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CustomPostRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Post.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public long countByCreatorId(Long creatorId) {
        return from(post)
                .where(post.creator.id.eq(creatorId))
                .fetchCount();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> findByCreatorId(
            Long creatorId,
            Long lastId,
            int limit
    ) {
        return from(post)
                .where(
                        post.creator.id.eq(creatorId),
                        lowerThanLastId(lastId)
                )
                .orderBy(post.id.desc())
                .limit(limit)
                .fetch();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Post> find(
            Long lastId,
            int limit
    ) {
        return from(post)
                .where(lowerThanLastId(lastId))
                .orderBy(post.id.desc())
                .limit(limit)
                .fetch();
    }

    private BooleanExpression lowerThanLastId(Long lastId) {
        if (lastId == null) {
            return null;
        }

        return post.id.lt(lastId);
    }

}
