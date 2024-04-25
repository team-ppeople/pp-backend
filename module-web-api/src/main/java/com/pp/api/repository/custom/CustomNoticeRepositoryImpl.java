package com.pp.api.repository.custom;

import com.pp.api.entity.Notice;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pp.api.entity.QNotice.notice;

public class CustomNoticeRepositoryImpl extends QuerydslRepositorySupport implements CustomNoticeRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CustomNoticeRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Notice.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Notice> find(
            Long lastId,
            int limit
    ) {
        return from(notice)
                .where(
                        lowerThanLastId(lastId)
                )
                .orderBy(notice.id.desc())
                .limit(limit)
                .fetch();
    }

    private BooleanExpression lowerThanLastId(Long lastId) {
        if (lastId == null) {
            return null;
        }

        return notice.id.lt(lastId);
    }

}
