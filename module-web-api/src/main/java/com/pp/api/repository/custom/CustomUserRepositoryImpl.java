package com.pp.api.repository.custom;

import com.pp.api.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.pp.api.entity.QComment.comment;
import static com.pp.api.entity.QOauthFrameworkAuthorization.oauthFrameworkAuthorization;
import static com.pp.api.entity.QOauthUser.oauthUser;
import static com.pp.api.entity.QOauthUserToken.oauthUserToken;
import static com.pp.api.entity.QPost.post;
import static com.pp.api.entity.QPostImage.postImage;
import static com.pp.api.entity.QProfileImage.profileImage;
import static com.pp.api.entity.QReportedComment.reportedComment;
import static com.pp.api.entity.QReportedPost.reportedPost;
import static com.pp.api.entity.QUploadFile.uploadFile;
import static com.pp.api.entity.QUser.user;
import static com.querydsl.jpa.JPAExpressions.selectOne;

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

    @Transactional(readOnly = true)
    @Override
    public List<User> findWithProfileImagesByIds(List<Long> userIds) {
        return from(user)
                .distinct()
                .leftJoin(user.profileImages, profileImage)
                .fetchJoin()
                .leftJoin(profileImage.uploadFile, uploadFile)
                .fetchJoin()
                .where(user.id.in(userIds))
                .fetch();
    }

    @Transactional
    @Override
    public void deleteCascadeById(Long userId) {
        delete(reportedPost)
                .where(reportedPost.reporter.id.eq(userId))
                .execute();

        delete(reportedComment)
                .where(reportedComment.reporter.id.eq(userId))
                .execute();

        delete(comment)
                .where(comment.creator.id.eq(userId))
                .execute();

        List<Long> uploadFileIds = jpaQueryFactory.select(uploadFile.id)
                .from(uploadFile)
                .where(uploadFile.uploader.id.eq(userId))
                .fetch();

        if (!uploadFileIds.isEmpty()) {
            delete(postImage)
                    .where(postImage.uploadFile.id.in(uploadFileIds))
                    .execute();

            delete(profileImage)
                    .where(profileImage.uploadFile.id.in(uploadFileIds))
                    .execute();

            delete(uploadFile)
                    .where(
                            uploadFile.id.in(uploadFileIds)
                                    .and(
                                            selectOne().from(profileImage)
                                                    .where(profileImage.uploadFile.id.eq(uploadFile.id))
                                                    .notExists()

                                    )
                    )
                    .execute();
        }

        delete(comment)
                .where(
                        selectOne().from(post)
                                .where(
                                        post.creator.id.eq(userId)
                                                .and(comment.post.id.eq(post.id))
                                )
                                .exists()
                )
                .execute();

        delete(post)
                .where(post.creator.id.eq(userId))
                .execute();

        Long oauthUserId = jpaQueryFactory.select(oauthUser.id)
                .from(oauthUser)
                .where(oauthUser.user.id.eq(userId))
                .fetchOne();

        if (oauthUserId != null) {
            delete(oauthUserToken)
                    .where(oauthUserToken.oauthUser.id.eq(oauthUserId))
                    .execute();

            delete(oauthUser)
                    .where(oauthUser.id.eq(oauthUserId))
                    .execute();
        }

        delete(oauthFrameworkAuthorization)
                .where(oauthFrameworkAuthorization.user.id.eq(userId))
                .execute();

        delete(user)
                .where(user.id.eq(userId))
                .execute();
    }

}
