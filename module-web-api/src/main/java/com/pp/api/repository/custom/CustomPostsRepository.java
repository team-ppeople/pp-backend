package com.pp.api.repository.custom;

import com.pp.api.entity.Posts;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CustomPostsRepository {

    @Transactional(readOnly = true)
    long countByCreatorId(Long creatorId);

    @Transactional(readOnly = true)
    List<Posts> findByCreatorId(
            Long creatorId,
            Long lastId,
            int limit
    );

}
