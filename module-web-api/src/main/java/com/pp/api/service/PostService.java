package com.pp.api.service;

import com.pp.api.entity.Post;
import com.pp.api.entity.PostImage;
import com.pp.api.entity.UploadFile;
import com.pp.api.entity.User;
import com.pp.api.repository.PostImageRepository;
import com.pp.api.repository.PostRepository;
import com.pp.api.repository.UploadFileRepository;
import com.pp.api.repository.UserRepository;
import com.pp.api.service.command.CreatePostCommand;
import com.pp.api.service.command.FindPostsByNoOffsetQuery;
import com.pp.api.service.domain.CreatedPost;
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

    private final PostRepository postsRepository;

    private final UserRepository userRepository;

    private final UploadFileRepository uploadFileRepository;

    private final PostImageRepository postImageRepository;

    public long countByCreateId(Long createId) {
        return postsRepository.countByCreatorId(createId);
    }

    @Transactional(readOnly = true)
    public List<PostOfList> findPostOfListByCreateId(
            Long createId,
            FindPostsByNoOffsetQuery query
    ) {
        return postsRepository.findByCreatorId(
                        createId,
                        query.getLastId(),
                        query.getLimit()
                )
                .stream()
                .map(post ->
                        new PostOfList(
                                post.getId(),
                                post.getImages().get(0).getUploadFile().getUrl(),
                                post.getTitle(),
                                post.getCreatedDate(),
                                post.getUpdatedDate()
                        )
                )
                .toList();
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

        postsRepository.save(post);

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

}
