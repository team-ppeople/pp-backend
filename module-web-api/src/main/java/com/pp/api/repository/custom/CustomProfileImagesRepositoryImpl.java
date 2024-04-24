package com.pp.api.repository.custom;

import com.pp.api.entity.ProfileImages;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import static com.pp.api.entity.QProfileImages.profileImages;

public class CustomProfileImagesRepositoryImpl extends QuerydslRepositorySupport implements CustomProfileImagesRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CustomProfileImagesRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(ProfileImages.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional
    @Override
    public void deleteByUserId(Long userId) {
        delete(profileImages)
                .where(profileImages.user.id.eq(userId))
                .execute();
    }

}
