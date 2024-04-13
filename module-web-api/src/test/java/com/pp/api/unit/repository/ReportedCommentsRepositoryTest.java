package com.pp.api.unit.repository;

import com.pp.api.entity.Comments;
import com.pp.api.entity.Posts;
import com.pp.api.entity.ReportedComments;
import com.pp.api.entity.Users;
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
        // when
        ReportedComments reportedComment = ReportedComments.builder()
                .comment(createAndSaveComment())
                .reporter(createAndSaveReporter())
                .build();

        ReportedComments savedReportedComment = reportedCommentsRepository.save(reportedComment);

        // then
        assertThat(savedReportedComment.getId()).isNotNull();
        assertThat(savedReportedComment.getComment()).isEqualTo(reportedComment.getComment());
        assertThat(savedReportedComment.getComment().getReports()).contains(reportedComment);
        assertThat(savedReportedComment.getReporter()).isEqualTo(reportedComment.getReporter());
        assertThat(savedReportedComment.getCreatedDate()).isNotNull();
        assertThat(savedReportedComment.getUpdatedDate()).isNotNull();
    }

    @Test
    void 신고된_댓글_엔티티를_조회한다() {
        // when
        ReportedComments reportedComment = ReportedComments.builder()
                .comment(createAndSaveComment())
                .reporter(createAndSaveReporter())
                .build();

        ReportedComments savedReportedComment = reportedCommentsRepository.save(reportedComment);

        entityManager.clear();

        ReportedComments foundReportedComments = reportedCommentsRepository.findById(reportedComment.getId())
                .orElseThrow();

        // then
        assertThat(foundReportedComments).isNotSameAs(savedReportedComment);
        assertThat(foundReportedComments.getId()).isEqualTo(savedReportedComment.getId());
        assertThat(foundReportedComments.getComment()).isEqualTo(savedReportedComment.getComment());
        assertThat(foundReportedComments.getReporter()).isEqualTo(savedReportedComment.getReporter());
        assertThat(foundReportedComments.getCreatedDate()).isEqualTo(savedReportedComment.getCreatedDate());
        assertThat(foundReportedComments.getUpdatedDate()).isEqualTo(savedReportedComment.getUpdatedDate());
    }

    Users createAndSaveUser() {
        Users user = Users.builder()
                .nickname("sinbom")
                .email("dev.sinbom@gmail.com")
                .build();

        return usersRepository.save(user);
    }

    Users createAndSaveReporter() {
        Users reporter = Users.builder()
                .nickname("바다거북맘")
                .email("sea-turtles@gmail.com")
                .build();

        return usersRepository.save(reporter);
    }

    Posts createAndSavePost() {
        Posts post = Posts.builder()
                .title("[HBD] 🎂저의 29번째 생일을 축하합니다.🥳")
                .content("yo~ 모두들 10002 10002 축하해주세요 😄")
                .creator(createAndSaveUser())
                .build();

        return postsRepository.save(post);
    }

    Comments createAndSaveComment() {
        Comments comments = Comments.builder()
                .content("WOW! 29번째 생일 넘우 넘우 축하드려요~ 👏")
                .post(createAndSavePost())
                .build();

        return commentsRepository.save(comments);
    }

}
