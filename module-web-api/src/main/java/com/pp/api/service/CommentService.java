package com.pp.api.service;

import com.pp.api.entity.Comment;
import com.pp.api.entity.Post;
import com.pp.api.entity.ReportedComment;
import com.pp.api.entity.User;
import com.pp.api.exception.*;
import com.pp.api.repository.CommentRepository;
import com.pp.api.repository.PostRepository;
import com.pp.api.repository.ReportedCommentRepository;
import com.pp.api.repository.UserRepository;
import com.pp.api.service.command.CreateCommentCommand;
import com.pp.api.service.command.FindCommentsByNoOffsetQuery;
import com.pp.api.service.domain.CommentOfList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.pp.api.util.JwtAuthenticationUtil.getAuthenticatedUserId;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final ReportedCommentRepository reportedCommentRepository;

    @Transactional
    public void create(CreateCommentCommand command) {
        User user = userRepository.findById(getAuthenticatedUserId())
                .orElseThrow(UserNotExistsException::new);

        Post post = postRepository.findById(command.getPostId())
                .orElseThrow(PostNotExistsException::new);

        Comment comment = Comment.builder()
                .content(command.getContent())
                .post(post)
                .creator(user)
                .build();

        commentRepository.save(comment);
    }

    public List<CommentOfList> findComments(FindCommentsByNoOffsetQuery query) {
        return commentRepository.findByPostId(
                        query.getPostId(),
                        query.getLastId(),
                        query.getLimit()
                )
                .stream()
                .map(comment ->
                        new CommentOfList(
                                comment.getId(),
                                comment.getContent(),
                                comment.getCreatedDate(),
                                comment.getUpdatedDate(),
                                comment.getCreator()
                                        .getId()
                        )
                )
                .toList();
    }

    public long countByPostId(Long postId) {
        return commentRepository.countByPostId(postId);
    }

    @Transactional
    public void report(Long commentId) {
        User user = userRepository.findById(getAuthenticatedUserId())
                .orElseThrow(UserNotExistsException::new);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotExistsException::new);

        if (comment.getCreator().getId().equals(user.getId())) {
            throw new CommentCanNotReportMySelfException();
        }

        boolean isAlreadyReported = reportedCommentRepository.existsByCommentIdAndReporterId(
                comment.getId(),
                user.getId()
        );

        if (isAlreadyReported) {
            throw new CommentAlreadyReportedException();
        }

        ReportedComment reportedComment = ReportedComment.builder()
                .comment(comment)
                .reporter(user)
                .build();

        reportedCommentRepository.save(reportedComment);
    }

}
