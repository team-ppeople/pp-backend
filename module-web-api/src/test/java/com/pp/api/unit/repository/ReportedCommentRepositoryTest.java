package com.pp.api.unit.repository;

import com.pp.api.entity.Comment;
import com.pp.api.entity.Post;
import com.pp.api.entity.ReportedComment;
import com.pp.api.entity.User;
import com.pp.api.fixture.CommentFixture;
import com.pp.api.fixture.PostFixture;
import com.pp.api.fixture.ReportedCommentFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.CommentRepository;
import com.pp.api.repository.PostRepository;
import com.pp.api.repository.ReportedCommentRepository;
import com.pp.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ReportedCommentRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private ReportedCommentRepository reportedCommentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 신고된_댓글_엔티티를_영속화한다() {
        User user = userRepository.save(UserFixture.of());

        Post post = postRepository.save(PostFixture.ofCreator(user));

        User commenter = userRepository.save(
                UserFixture.from(
                        "피피처돌이",
                        "pplover@naver.com"
                )
        );

        Comment comment = commentRepository.save(
                CommentFixture.fromPostAndCreator(
                        post,
                        commenter
                )
        );

        User reporter = userRepository.save(
                UserFixture.from(
                        "바다거북맘",
                        "sea-turtles@gmail.com"
                )
        );

        ReportedComment reportedComment = ReportedCommentFixture.from(
                comment,
                reporter
        );

        ReportedComment savedReportedComment = reportedCommentRepository.save(reportedComment);

        assertThat(savedReportedComment.getId()).isNotNull();
        assertThat(savedReportedComment.getComment()).isEqualTo(reportedComment.getComment());
        assertThat(savedReportedComment.getComment().getReports()).contains(reportedComment);
        assertThat(savedReportedComment.getReporter()).isEqualTo(reportedComment.getReporter());
        assertThat(savedReportedComment.getCreatedDate()).isNotNull();
        assertThat(savedReportedComment.getUpdatedDate()).isNotNull();
    }

    @Test
    void 신고된_댓글_엔티티를_조회한다() {
        User user = userRepository.save(UserFixture.of());

        Post post = postRepository.save(PostFixture.ofCreator(user));

        User commenter = userRepository.save(
                UserFixture.from(
                        "피피처돌이",
                        "pplover@naver.com"
                )
        );

        Comment comment = commentRepository.save(
                CommentFixture.fromPostAndCreator(
                        post,
                        commenter
                )
        );

        User reporter = userRepository.save(
                UserFixture.from(
                        "바다거북맘",
                        "sea-turtles@gmail.com"
                )
        );

        ReportedComment reportedComment = ReportedCommentFixture.from(
                comment,
                reporter
        );

        ReportedComment savedReportedComment = reportedCommentRepository.save(reportedComment);

        entityManager.clear();

        ReportedComment foundReportedComment = reportedCommentRepository.findById(reportedComment.getId())
                .orElseThrow();

        assertThat(foundReportedComment).isNotSameAs(savedReportedComment);
        assertThat(foundReportedComment.getId()).isEqualTo(savedReportedComment.getId());
        assertThat(foundReportedComment.getComment()).isEqualTo(savedReportedComment.getComment());
        assertThat(foundReportedComment.getReporter()).isEqualTo(savedReportedComment.getReporter());
        assertThat(foundReportedComment.getCreatedDate()).isEqualToIgnoringNanos(savedReportedComment.getCreatedDate());
        assertThat(foundReportedComment.getUpdatedDate()).isEqualToIgnoringNanos(savedReportedComment.getUpdatedDate());
    }

}
