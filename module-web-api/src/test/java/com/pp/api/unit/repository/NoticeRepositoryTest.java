package com.pp.api.unit.repository;

import com.pp.api.entity.Notice;
import com.pp.api.fixture.NoticeFixture;
import com.pp.api.repository.NoticeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class NoticeRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    void 공지사항_엔티티를_영속화한다() {
        Notice notice = NoticeFixture.of();

        Notice savedNotice = noticeRepository.save(notice);

        assertThat(savedNotice.getId()).isNotNull();
        assertThat(savedNotice.getTitle()).isEqualTo(notice.getTitle());
        assertThat(savedNotice.getContent()).isEqualTo(notice.getContent());
        assertThat(savedNotice.getCreatedDate()).isNotNull();
        assertThat(savedNotice.getUpdatedDate()).isNotNull();
    }

    @Test
    void 공지사항_엔티티를_조회한다() {
        Notice notice = NoticeFixture.of();

        Notice savedNotice = noticeRepository.save(notice);

        entityManager.clear();

        Notice foundNotice = noticeRepository.findById(savedNotice.getId())
                .orElseThrow();

        assertThat(foundNotice).isNotSameAs(savedNotice);
        assertThat(foundNotice.getId()).isEqualTo(savedNotice.getId());
        assertThat(foundNotice.getTitle()).isEqualTo(savedNotice.getTitle());
        assertThat(foundNotice.getContent()).isEqualTo(savedNotice.getContent());
        assertThat(foundNotice.getCreatedDate()).isEqualToIgnoringNanos(savedNotice.getCreatedDate());
        assertThat(foundNotice.getUpdatedDate()).isEqualToIgnoringNanos(savedNotice.getUpdatedDate());
    }

}
