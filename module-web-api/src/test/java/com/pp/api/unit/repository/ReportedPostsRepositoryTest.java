package com.pp.api.unit.repository;

import com.pp.api.entity.Posts;
import com.pp.api.entity.ReportedPosts;
import com.pp.api.entity.Users;
import com.pp.api.fixture.PostFixture;
import com.pp.api.fixture.ReportedPostFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.PostsRepository;
import com.pp.api.repository.ReportedPostsRepository;
import com.pp.api.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class ReportedPostsRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private ReportedPostsRepository reportedPostsRepository;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    void 신고된_게시글_엔티티를_영속화한다() {
        Users user = usersRepository.save(UserFixture.of());

        Posts post = postsRepository.save(PostFixture.ofCreator(user));

        Users reporter = usersRepository.save(
                UserFixture.from(
                        "바다거북맘",
                        "sea-turtles@gmail.com"
                )
        );

        ReportedPosts reportedPost = ReportedPostFixture.from(
                post,
                reporter
        );

        ReportedPosts savedReportedPost = reportedPostsRepository.save(reportedPost);

        assertThat(savedReportedPost.getId()).isNotNull();
        assertThat(savedReportedPost.getPost()).isEqualTo(reportedPost.getPost());
        assertThat(savedReportedPost.getPost().getReports()).contains(reportedPost);
        assertThat(savedReportedPost.getReporter()).isEqualTo(reportedPost.getReporter());
        assertThat(savedReportedPost.getCreatedDate()).isNotNull();
        assertThat(savedReportedPost.getUpdatedDate()).isNotNull();
    }

    @Test
    void 신고된_게시글_엔티티를_조회한다() {
        Users user = usersRepository.save(UserFixture.of());

        Posts post = postsRepository.save(PostFixture.ofCreator(user));

        Users reporter = usersRepository.save(
                UserFixture.from(
                        "바다거북맘",
                        "sea-turtles@gmail.com"
                )
        );

        ReportedPosts reportedPost = ReportedPostFixture.from(
                post,
                reporter
        );

        ReportedPosts savedReportedPost = reportedPostsRepository.save(reportedPost);

        entityManager.clear();

        ReportedPosts foundReportedPost = reportedPostsRepository.findById(savedReportedPost.getId())
                .orElseThrow();

        assertThat(foundReportedPost).isNotSameAs(savedReportedPost);
        assertThat(foundReportedPost.getId()).isEqualTo(savedReportedPost.getId());
        assertThat(foundReportedPost.getPost()).isEqualTo(savedReportedPost.getPost());
        assertThat(foundReportedPost.getReporter()).isEqualTo(savedReportedPost.getReporter());
        assertThat(foundReportedPost.getCreatedDate()).isEqualTo(savedReportedPost.getCreatedDate());
        assertThat(foundReportedPost.getUpdatedDate()).isEqualTo(savedReportedPost.getUpdatedDate());
    }

}
