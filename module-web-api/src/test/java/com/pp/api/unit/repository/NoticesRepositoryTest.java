package com.pp.api.unit.repository;

import com.pp.api.entity.Notices;
import com.pp.api.repository.NoticesRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class NoticesRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private NoticesRepository noticesRepository;

    @Test
    void 공지사항_엔티티를_영속화한다() {
        // given
        String title = "[ALL] 📣PP를 사용해주시는 고객님들께 감사의 말씀을 전해드립니다 ❤️";
        String content = "hi~ 모두들 10002 10002 이용해주세요 🙇🏻‍";

        // when
        Notices notice = Notices.builder()
                .title(title)
                .content(content)
                .build();

        Notices savedNotice = noticesRepository.save(notice);

        // then
        assertThat(savedNotice.getId()).isNotNull();
        assertThat(savedNotice.getTitle()).isEqualTo(title);
        assertThat(savedNotice.getContent()).isEqualTo(content);
        assertThat(savedNotice.getCreatedDate()).isNotNull();
        assertThat(savedNotice.getUpdatedDate()).isNotNull();
    }

    @Test
    void 공지사항_엔티티를_조회한다() {
        // given
        String title = "[ALL] 📣PP를 사용해주시는 고객님들께 감사의 말씀을 전해드립니다 ❤️";
        String content = "hi~ 모두들 10002 10002 이용해주세요 🙇🏻‍";

        // when
        Notices notice = Notices.builder()
                .title(title)
                .content(content)
                .build();

        Notices savedNotice = noticesRepository.save(notice);

        entityManager.clear();

        Notices foundNotice = noticesRepository.findById(savedNotice.getId())
                .orElseThrow();

        // then
        assertThat(foundNotice).isNotSameAs(savedNotice);
        assertThat(foundNotice.getId()).isEqualTo(savedNotice.getId());
        assertThat(foundNotice.getTitle()).isEqualTo(savedNotice.getTitle());
        assertThat(foundNotice.getContent()).isEqualTo(savedNotice.getContent());
        assertThat(foundNotice.getCreatedDate()).isEqualTo(savedNotice.getCreatedDate());
        assertThat(foundNotice.getUpdatedDate()).isEqualTo(savedNotice.getUpdatedDate());
    }

}
