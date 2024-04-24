package com.pp.api.unit.repository;

import com.pp.api.entity.Post;
import com.pp.api.entity.ReportedPost;
import com.pp.api.entity.User;
import com.pp.api.fixture.PostFixture;
import com.pp.api.fixture.ReportedPostFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.PostRepository;
import com.pp.api.repository.ReportedPostRepository;
import com.pp.api.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ReportedPostRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private ReportedPostRepository reportedPostRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 신고된_게시글_엔티티를_영속화한다() {
        User user = userRepository.save(UserFixture.of());

        Post post = postRepository.save(PostFixture.ofCreator(user));

        User reporter = userRepository.save(
                UserFixture.from(
                        "바다거북맘",
                        "sea-turtles@gmail.com"
                )
        );

        ReportedPost reportedPost = ReportedPostFixture.from(
                post,
                reporter
        );

        ReportedPost savedReportedPost = reportedPostRepository.save(reportedPost);

        assertThat(savedReportedPost.getId()).isNotNull();
        assertThat(savedReportedPost.getPost()).isEqualTo(reportedPost.getPost());
        assertThat(savedReportedPost.getPost().getReports()).contains(reportedPost);
        assertThat(savedReportedPost.getReporter()).isEqualTo(reportedPost.getReporter());
        assertThat(savedReportedPost.getCreatedDate()).isNotNull();
        assertThat(savedReportedPost.getUpdatedDate()).isNotNull();
    }

    @Test
    void 신고된_게시글_엔티티를_조회한다() {
        User user = userRepository.save(UserFixture.of());

        Post post = postRepository.save(PostFixture.ofCreator(user));

        User reporter = userRepository.save(
                UserFixture.from(
                        "바다거북맘",
                        "sea-turtles@gmail.com"
                )
        );

        ReportedPost reportedPost = ReportedPostFixture.from(
                post,
                reporter
        );

        ReportedPost savedReportedPost = reportedPostRepository.save(reportedPost);

        entityManager.clear();

        ReportedPost foundReportedPost = reportedPostRepository.findById(savedReportedPost.getId())
                .orElseThrow();

        assertThat(foundReportedPost).isNotSameAs(savedReportedPost);
        assertThat(foundReportedPost.getId()).isEqualTo(savedReportedPost.getId());
        assertThat(foundReportedPost.getPost()).isEqualTo(savedReportedPost.getPost());
        assertThat(foundReportedPost.getReporter()).isEqualTo(savedReportedPost.getReporter());
        assertThat(foundReportedPost.getCreatedDate()).isEqualTo(savedReportedPost.getCreatedDate());
        assertThat(foundReportedPost.getUpdatedDate()).isEqualTo(savedReportedPost.getUpdatedDate());
    }

}
