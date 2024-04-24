package com.pp.api.repository;

import com.pp.api.entity.Post;
import com.pp.api.repository.custom.CustomPostRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, CustomPostRepository {
}
