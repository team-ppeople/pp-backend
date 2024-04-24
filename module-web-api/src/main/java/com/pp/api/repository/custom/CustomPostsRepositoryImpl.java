package com.pp.api.repository.custom;

import com.pp.api.entity.Posts;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pp.api.entity.QPosts.posts;

public class CustomPostsRepositoryImpl extends QuerydslRepositorySupport implements CustomPostsRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CustomPostsRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Posts.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public long countByCreatorId(Long creatorId) {
        return from(posts)
                .where(posts.creator.id.eq(creatorId))
                .fetchCount();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Posts> findByCreatorId(
            Long creatorId,
            Long lastId,
            int limit
    ) {
        return from(posts)
                .where(
                        posts.creator.id.eq(creatorId),
                        lowerThanLastId(lastId)
                )
                .orderBy(posts.id.desc())
                .limit(limit)
                .fetch();
    }

    private BooleanExpression lowerThanLastId(Long lastId) {
        if (lastId == null) {
            return null;
        }

        return posts.id.lt(lastId);
    }

}
