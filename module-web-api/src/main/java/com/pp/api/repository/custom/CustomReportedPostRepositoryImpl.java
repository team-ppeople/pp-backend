package com.pp.api.repository.custom;

import com.pp.api.entity.ReportedPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import static com.pp.api.entity.QReportedPost.reportedPost;

public class CustomReportedPostRepositoryImpl extends QuerydslRepositorySupport
        implements CustomReportedPostRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CustomReportedPostRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(ReportedPost.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByPostIdAndReporterId(
            Long postId,
            Long reporterId
    ) {
        Integer selectOne = jpaQueryFactory.selectOne()
                .from(reportedPost)
                .where(
                        reportedPost.post.id.eq(postId),
                        reportedPost.reporter.id.eq(reporterId)
                )
                .fetchFirst();

        return selectOne != null;
    }

}
