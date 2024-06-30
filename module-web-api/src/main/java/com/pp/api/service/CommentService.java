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

    private final BlockUserRepository blockUserRepository;

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

    public List<CommentOfList> findCommentsNotInBlockedUsers(FindCommentsNotInBlockedByNoOffsetQuery query) {
        return commentRepository.findNotInBlockedUsersByPostId(
                        query.getPostId(),
                        query.getLastId(),
                        query.getLimit(),
                        query.getBlockedIds()
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
