package com.pp.api.repository.custom;

import com.pp.api.entity.ReportedComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import static com.pp.api.entity.QReportedComment.reportedComment;

public class CustomReportedCommentRepositoryImpl extends QuerydslRepositorySupport
        implements CustomReportedCommentRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CustomReportedCommentRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(ReportedComment.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean existsByCommentIdAndReporterId(
            Long commentId,
            Long reporterId
    ) {
        Integer selectOne = jpaQueryFactory.selectOne()
                .from(reportedComment)
                .where(
                        reportedComment.comment.id.eq(commentId),
                        reportedComment.reporter.id.eq(reporterId)
                )
                .fetchFirst();

        return selectOne != null;
    }

}
