package com.pp.api.repository.custom;

import com.pp.api.entity.Post;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomPostRepository {

    @Transactional(readOnly = true)
    long countByCreatorId(Long creatorId);

    @Transactional(readOnly = true)
    List<Post> findByCreatorId(
            Long creatorId,
            Long lastId,
            int limit
    );

}
