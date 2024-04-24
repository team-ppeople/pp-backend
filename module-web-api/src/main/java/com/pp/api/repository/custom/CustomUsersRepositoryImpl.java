package com.pp.api.repository.custom;

import com.pp.api.entity.Users;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.pp.api.entity.QProfileImages.profileImages;
import static com.pp.api.entity.QUploadFiles.uploadFiles;
import static com.pp.api.entity.QUsers.users;

public class CustomUsersRepositoryImpl extends QuerydslRepositorySupport implements CustomUsersRepository {

    private final JPAQueryFactory jpaQueryFactory;


    public CustomUsersRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Users.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Users> findWithProfileImagesById(Long userId) {
        Users entity = from(users)
                .distinct()
                .leftJoin(users.profileImages, profileImages)
                .fetchJoin()
                .leftJoin(profileImages.uploadFile, uploadFiles)
                .fetchJoin()
                .where(users.id.eq(userId))
                .fetchOne();

        return Optional.ofNullable(entity);
    }

}
