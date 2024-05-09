package com.pp.api.service;

import com.pp.api.entity.*;
import com.pp.api.repository.*;
import com.pp.api.service.command.CreatePostCommand;
import com.pp.api.service.command.FindPostsByNoOffsetQuery;
import com.pp.api.service.command.FindUserCreatedPostsByNoOffsetQuery;
import com.pp.api.service.domain.CreatedPost;
import com.pp.api.service.domain.PostDetail;
import com.pp.api.service.domain.PostOfList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.pp.api.entity.enums.UploadFileType.POST_IMAGE;
import static com.pp.api.util.JwtAuthenticationUtil.checkUserPermission;
import static com.pp.api.util.JwtAuthenticationUtil.getAuthenticatedUserId;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final UploadFileRepository uploadFileRepository;

    private final PostImageRepository postImageRepository;

    private final ReportedPostRepository reportedPostRepository;

    private final PostUserActionRepository postUserActionRepository;

    public long countByCreateId(Long creatorId) {
        return postRepository.countByCreatorId(creatorId);
    }

    @Transactional(readOnly = true)
    public List<PostOfList> findUserCreatedPosts(FindUserCreatedPostsByNoOffsetQuery query) {
        return postRepository.findByCreatorId(
                        query.getCreatorId(),
                        query.getLastId(),
                        query.getLimit()
                )
                .stream()
                .map(post ->
                        new PostOfList(
                                post.getId(),
                                post.getImages()
                                        .get(0)
                                        .getUploadFile()
                                        .getUrl(),
                                post.getTitle(),
                                post.getCreatedDate(),
                                post.getUpdatedDate()
                        )
                )
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PostOfList> findPosts(FindPostsByNoOffsetQuery query) {
        return postRepository.find(
                        query.getLastId(),
                        query.getLimit()
                )
                .stream()
                .map(post ->
                        new PostOfList(
                                post.getId(),
                                post.getImages()
                                        .get(0)
                                        .getUploadFile()
                                        .getUrl(),
                                post.getTitle(),
                                post.getCreatedDate(),
                                post.getUpdatedDate()
                        )
                )
                .toList();
    }

    public PostDetail findPostDetailById(Long postId) {
        Post post = postRepository.findWithImagesById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        return new PostDetail(
                post.getId(),
                post.getImages()
                        .stream()
                        .map(postImage ->
                                postImage.getUploadFile()
                                        .getUrl()
                        )
                        .toList(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedDate(),
                post.getUpdatedDate(),
                post.getCreator()
                        .getId()
        );
    }

    @Transactional
    public CreatedPost create(CreatePostCommand command) {
        User user = userRepository.findById(getAuthenticatedUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Post post = Post.builder()
                .title(command.getTitle())
                .content(command.getContent())
                .creator(user)
                .build();

        Map<Long, UploadFile> uploadFiles = uploadFileRepository.findAllById(command.getPostImageFileUploadIds())
                .stream()
                .collect(
                        toUnmodifiableMap(
                                UploadFile::getId,
                                identity()
                        )
                );

        List<PostImage> postImages = command.getPostImageFileUploadIds()
                .stream()
                .map(uploadId -> {
                    UploadFile uploadFile = uploadFiles.get(uploadId);

                    if (uploadFile == null) {
                        throw new IllegalArgumentException("업로드하지 않은 게시글 이미지가 존재합니다.");
                    }

                    if (uploadFile.getFileType() != POST_IMAGE) {
                        throw new IllegalArgumentException("게시글 이미지에 사용할 수 없는 이미지가 존재합니다.");
                    }

                    checkUserPermission(uploadFile.getUploader().getId());

                    return PostImage.builder()
                            .post(post)
                            .uploadFile(uploadFile)
                            .build();
                })
                .toList();

        postRepository.save(post);

        postImageRepository.saveAll(postImages);

        return new CreatedPost(
                post.getId(),
                postImages.get(0)
                        .getUploadFile()
                        .getUrl(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedDate()
        );
    }

    @Transactional
    public void report(Long postId) {
        User user = userRepository.findById(getAuthenticatedUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (post.getCreator().getId().equals(user.getId())) {
            throw new IllegalArgumentException("본인이 작성한 게시글은 신고할 수 없습니다.");
        }

        boolean isAlreadyReported = reportedPostRepository.existsByPostIdAndReporterId(
                post.getId(),
                user.getId()
        );

        if (isAlreadyReported) {
            throw new IllegalArgumentException("이미 신고한 게시글입니다.");
        }

        ReportedPost reportedPost = ReportedPost.builder()
                .post(post)
                .reporter(user)
                .build();

        reportedPostRepository.save(reportedPost);
    }

    public boolean isReportedById(Long postId) {
        return reportedPostRepository.existsByPostId(postId);
    }

    public void thumbsUp(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        postUserActionRepository.thumbsUp(
                post.getId(),
                post.getCreator()
                        .getId(),
                getAuthenticatedUserId()
        );
    }

    public void thumbsSideways(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        postUserActionRepository.thumbsSideways(
                post.getId(),
                post.getCreator()
                        .getId(),
                getAuthenticatedUserId()
        );
    }

    public long countThumbsUpByPostId(Long postId) {
        return postUserActionRepository.countThumbsUpByPostId(postId);
    }

    public long countUserPostThumbsUpByPostId(Long userId) {
        return postUserActionRepository.countUserPostThumbsUpByUserId(userId);
    }

    public boolean isThumbsUppedByUserId(
            Long postId,
            Long userId
    ) {
        return postUserActionRepository.isThumbsUppedByUserId(
                postId,
                userId
        );
    }

}
