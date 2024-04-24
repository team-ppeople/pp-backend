package com.pp.api.repository.custom;

import com.pp.api.entity.ProfileImage;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import static com.pp.api.entity.QProfileImage.profileImage;

public class CustomProfileImageRepositoryImpl extends QuerydslRepositorySupport implements CustomProfileImageRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public CustomProfileImageRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(ProfileImage.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional
    @Override
    public void deleteByUserId(Long userId) {
        delete(profileImage)
                .where(profileImage.user.id.eq(userId))
                .execute();
    }

}
