package com.pp.api.service;

import com.pp.api.entity.Comment;
import com.pp.api.entity.Post;
import com.pp.api.entity.ReportedComment;
import com.pp.api.entity.User;
import com.pp.api.exception.*;
import com.pp.api.repository.*;
import com.pp.api.service.command.CreateCommentCommand;
import com.pp.api.service.command.FindCommentsByNoOffsetQuery;
import com.pp.api.service.command.FindCommentsNotInBlockedByNoOffsetQuery;
import com.pp.api.service.domain.CommentOfList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import org.springframework.validation.annotation.Validated;

import static com.pp.api.util.JwtAuthenticationUtil.getAuthenticatedUserId;

@Validated
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final ReportedCommentRepository reportedCommentRepository;

    private final BlockUserRepository blockUserRepository;

    @Transactional
    public void create(@Valid CreateCommentCommand command) {
        User user = userRepository.findById(getAuthenticatedUserId())
                .orElseThrow(UserNotExistsException::new);

        Post post = postRepository.findById(command.postId())
                .orElseThrow(PostNotExistsException::new);

        Comment comment = Comment.builder()
                .content(command.content())
                .post(post)
                .creator(user)
                .build();

        commentRepository.save(comment);
    }

    public List<CommentOfList> findComments(@Valid FindCommentsByNoOffsetQuery query) {
        return commentRepository.findByPostId(
                        query.postId(),
                        query.lastId(),
                        query.limit()
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

    public List<CommentOfList> findCommentsNotInBlockedUsers(@Valid FindCommentsNotInBlockedByNoOffsetQuery query) {
        return commentRepository.findNotInBlockedUsersByPostId(
                        query.postId(),
                        query.lastId(),
                        query.limit(),
                        query.blockedIds()
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
        List<Long> blockedIds = blockUserRepository.findBlockedIds(getAuthenticatedUserId())
                .stream()
                .map(Long::valueOf)
                .toList();

        if (blockedIds.isEmpty()) {
            return commentRepository.countByPostId(postId);
        }

        return commentRepository.countNotInBlockedUserByPostId(
                postId,
                blockedIds
        );
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
