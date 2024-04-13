package com.pp.api.unit.repository;

import com.pp.api.entity.Posts;
import com.pp.api.entity.ReportedPosts;
import com.pp.api.entity.Users;
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
        // when
        ReportedPosts reportedPost = ReportedPosts.builder()
                .post(createAndSavePost())
                .reporter(createAndSaveReporter())
                .build();

        ReportedPosts savedReportedPost = reportedPostsRepository.save(reportedPost);

        // then
        assertThat(savedReportedPost.getId()).isNotNull();
        assertThat(savedReportedPost.getPost()).isEqualTo(reportedPost.getPost());
        assertThat(savedReportedPost.getPost().getReports()).contains(reportedPost);
        assertThat(savedReportedPost.getReporter()).isEqualTo(reportedPost.getReporter());
        assertThat(savedReportedPost.getCreatedDate()).isNotNull();
        assertThat(savedReportedPost.getUpdatedDate()).isNotNull();
    }

    @Test
    void 신고된_게시글_엔티티를_조회한다() {
        // when
        ReportedPosts reportedPost = ReportedPosts.builder()
                .post(createAndSavePost())
                .reporter(createAndSaveReporter())
                .build();

        ReportedPosts savedReportedPost = reportedPostsRepository.save(reportedPost);

        entityManager.clear();

        ReportedPosts foundReportedPost = reportedPostsRepository.findById(savedReportedPost.getId())
                .orElseThrow();

        // then
        assertThat(foundReportedPost).isNotSameAs(savedReportedPost);
        assertThat(foundReportedPost.getId()).isEqualTo(savedReportedPost.getId());
        assertThat(foundReportedPost.getPost()).isEqualTo(savedReportedPost.getPost());
        assertThat(foundReportedPost.getReporter()).isEqualTo(savedReportedPost.getReporter());
        assertThat(foundReportedPost.getCreatedDate()).isEqualTo(savedReportedPost.getCreatedDate());
        assertThat(foundReportedPost.getUpdatedDate()).isEqualTo(savedReportedPost.getUpdatedDate());
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

}
