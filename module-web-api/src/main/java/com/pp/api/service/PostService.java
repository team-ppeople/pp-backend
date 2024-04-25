package com.pp.api.service;

import com.pp.api.repository.PostRepository;
import com.pp.api.service.command.FindPostsByNoOffsetQuery;
import com.pp.api.service.domain.PostOfList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postsRepository;

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

}
