package com.pp.api.repository;

import com.pp.api.entity.Posts;
import com.pp.api.repository.custom.CustomPostsRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long>, CustomPostsRepository {
}
