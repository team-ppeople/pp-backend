package com.pp.api.unit.repository;

import com.pp.api.entity.Notices;
import com.pp.api.fixture.NoticeFixture;
import com.pp.api.repository.NoticesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class NoticesRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private NoticesRepository noticesRepository;

    @Test
    void 공지사항_엔티티를_영속화한다() {
        Notices notice = NoticeFixture.of();

        Notices savedNotice = noticesRepository.save(notice);

        assertThat(savedNotice.getId()).isNotNull();
        assertThat(savedNotice.getTitle()).isEqualTo(notice.getTitle());
        assertThat(savedNotice.getContent()).isEqualTo(notice.getContent());
        assertThat(savedNotice.getCreatedDate()).isNotNull();
        assertThat(savedNotice.getUpdatedDate()).isNotNull();
    }

    @Test
    void 공지사항_엔티티를_조회한다() {
        Notices notice = NoticeFixture.of();

        Notices savedNotice = noticesRepository.save(notice);

        entityManager.clear();

        Notices foundNotice = noticesRepository.findById(savedNotice.getId())
                .orElseThrow();

        assertThat(foundNotice).isNotSameAs(savedNotice);
        assertThat(foundNotice.getId()).isEqualTo(savedNotice.getId());
        assertThat(foundNotice.getTitle()).isEqualTo(savedNotice.getTitle());
        assertThat(foundNotice.getContent()).isEqualTo(savedNotice.getContent());
        assertThat(foundNotice.getCreatedDate()).isEqualTo(savedNotice.getCreatedDate());
        assertThat(foundNotice.getUpdatedDate()).isEqualTo(savedNotice.getUpdatedDate());
    }

}
