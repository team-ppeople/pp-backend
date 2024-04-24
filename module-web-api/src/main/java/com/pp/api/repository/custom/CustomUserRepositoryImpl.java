package com.pp.api.repository.custom;

import com.pp.api.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.pp.api.entity.QProfileImage.profileImage;
import static com.pp.api.entity.QUploadFile.uploadFile;
import static com.pp.api.entity.QUser.user;

public class CustomUserRepositoryImpl extends QuerydslRepositorySupport implements CustomUserRepository {

    private final JPAQueryFactory jpaQueryFactory;


    public CustomUserRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(User.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findWithProfileImagesById(Long userId) {
        User entity = from(user)
                .distinct()
                .leftJoin(user.profileImages, profileImage)
                .fetchJoin()
                .leftJoin(profileImage.uploadFile, uploadFile)
                .fetchJoin()
                .where(user.id.eq(userId))
                .fetchOne();

        return Optional.ofNullable(entity);
    }

}
