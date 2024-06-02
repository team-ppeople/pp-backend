package com.pp.api.repository.custom;

import com.pp.api.entity.Post;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.pp.api.entity.QComment.comment;
import static com.pp.api.entity.QPost.post;
import static com.pp.api.entity.QPostImage.postImage;
import static com.pp.api.entity.QReportedComment.reportedComment;
import static com.pp.api.entity.QReportedPost.reportedPost;
import static com.pp.api.entity.QUploadFile.uploadFile;

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

    @Transactional(readOnly = true)
    @Override
    public Optional<Post> findWithImagesById(Long id) {
        Post entity = from(post)
                .leftJoin(post.images, postImage)
                .fetchJoin()
                .leftJoin(postImage.uploadFile, uploadFile)
                .fetchJoin()
                .where(post.id.eq(id))
                .fetchOne();

        return Optional.ofNullable(entity);
    }

    @Transactional
    @Override
    public void deleteCascadeById(Long id) {
        delete(reportedPost)
                .where(reportedPost.post.id.eq(id))
                .execute();

        List<Long> commentIds = jpaQueryFactory.select(comment.id)
                .from(comment)
                .where(comment.post.id.eq(id))
                .fetch();

        if (!commentIds.isEmpty()) {
            delete(reportedComment)
                    .where(reportedComment.comment.id.in(commentIds))
                    .execute();

            delete(comment)
                    .where(comment.id.in(commentIds))
                    .execute();
        }

        List<Long> uploadFileIds = jpaQueryFactory.select(postImage.uploadFile.id)
                .from(postImage)
                .where(postImage.post.id.eq(id))
                .fetch();

        if (!uploadFileIds.isEmpty()) {
            delete(postImage)
                    .where(postImage.post.id.eq(id))
                    .execute();

            delete(uploadFile)
                    .where(uploadFile.id.in(uploadFileIds))
                    .execute();
        }

        delete(post)
                .where(post.id.eq(id))
                .execute();
    }

    private BooleanExpression lowerThanLastId(Long lastId) {
        if (lastId == null) {
            return null;
        }

        return post.id.lt(lastId);
    }

}
