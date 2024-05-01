package com.pp.api.service;

import com.pp.api.entity.Comment;
import com.pp.api.entity.Post;
import com.pp.api.entity.User;
import com.pp.api.repository.CommentRepository;
import com.pp.api.repository.PostRepository;
import com.pp.api.repository.UserRepository;
import com.pp.api.service.command.CreateCommentCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.pp.api.util.JwtAuthenticationUtil.getAuthenticatedUserId;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    @Transactional
    public void create(CreateCommentCommand command) {
        User user = userRepository.findById(getAuthenticatedUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        Post post = postRepository.findById(command.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        Comment comment = Comment.builder()
                .content(command.getContent())
                .post(post)
                .creator(user)
                .build();

        commentRepository.save(comment);
    }

}
