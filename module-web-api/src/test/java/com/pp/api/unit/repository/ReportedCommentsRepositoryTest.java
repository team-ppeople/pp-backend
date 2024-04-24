package com.pp.api.unit.repository;

import com.pp.api.entity.Comments;
import com.pp.api.entity.Posts;
import com.pp.api.entity.ReportedComments;
import com.pp.api.entity.Users;
import com.pp.api.fixture.CommentFixture;
import com.pp.api.fixture.PostFixture;
import com.pp.api.fixture.ReportedCommentFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.CommentsRepository;
import com.pp.api.repository.PostsRepository;
import com.pp.api.repository.ReportedCommentsRepository;
import com.pp.api.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ReportedCommentsRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private ReportedCommentsRepository reportedCommentsRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void 신고된_댓글_엔티티를_영속화한다() {
        Users user = usersRepository.save(UserFixture.of());

        Posts post = postsRepository.save(PostFixture.ofCreator(user));

        Comments comment = commentsRepository.save(CommentFixture.ofPost(post));

        Users reporter = usersRepository.save(
                UserFixture.from(
                        "바다거북맘",
                        "sea-turtles@gmail.com"
                )
        );

        ReportedComments reportedComment = ReportedCommentFixture.from(
                comment,
                reporter
        );

        ReportedComments savedReportedComment = reportedCommentsRepository.save(reportedComment);

        assertThat(savedReportedComment.getId()).isNotNull();
        assertThat(savedReportedComment.getComment()).isEqualTo(reportedComment.getComment());
        assertThat(savedReportedComment.getComment().getReports()).contains(reportedComment);
        assertThat(savedReportedComment.getReporter()).isEqualTo(reportedComment.getReporter());
        assertThat(savedReportedComment.getCreatedDate()).isNotNull();
        assertThat(savedReportedComment.getUpdatedDate()).isNotNull();
    }

    @Test
    void 신고된_댓글_엔티티를_조회한다() {
        Users user = usersRepository.save(UserFixture.of());

        Posts post = postsRepository.save(PostFixture.ofCreator(user));

        Comments comment = commentsRepository.save(CommentFixture.ofPost(post));

        Users reporter = usersRepository.save(
                UserFixture.from(
                        "바다거북맘",
                        "sea-turtles@gmail.com"
                )
        );

        ReportedComments reportedComment = ReportedCommentFixture.from(
                comment,
                reporter
        );

        ReportedComments savedReportedComment = reportedCommentsRepository.save(reportedComment);

        entityManager.clear();

        ReportedComments foundReportedComments = reportedCommentsRepository.findById(reportedComment.getId())
                .orElseThrow();

        assertThat(foundReportedComments).isNotSameAs(savedReportedComment);
        assertThat(foundReportedComments.getId()).isEqualTo(savedReportedComment.getId());
        assertThat(foundReportedComments.getComment()).isEqualTo(savedReportedComment.getComment());
        assertThat(foundReportedComments.getReporter()).isEqualTo(savedReportedComment.getReporter());
        assertThat(foundReportedComments.getCreatedDate()).isEqualTo(savedReportedComment.getCreatedDate());
        assertThat(foundReportedComments.getUpdatedDate()).isEqualTo(savedReportedComment.getUpdatedDate());
    }

}
