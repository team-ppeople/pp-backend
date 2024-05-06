package com.pp.api.repository.custom;

import com.pp.api.entity.Post;

import java.util.List;
import java.util.Optional;

public interface CustomPostRepository {

    long countByCreatorId(Long creatorId);

    List<Post> findByCreatorId(
            Long creatorId,
            Long lastId,
            int limit
    );

    List<Post> find(
            Long lastId,
            int limit
    );

    Optional<Post> findWithImagesById(Long id);

}
