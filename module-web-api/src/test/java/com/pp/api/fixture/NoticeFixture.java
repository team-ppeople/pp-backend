package com.pp.api.fixture;

import com.pp.api.entity.Notices;

public class NoticeFixture {

    private static final String DEFAULT_TITLE = "[ALL] 📣PP를 사용해주시는 고객님들께 감사의 말씀을 전해드립니다 ❤️";

    private static final String DEFAULT_CONTENT = "hi~ 모두들 10002 10002 이용해주세요 🙇🏻‍";

    public static Notices createNotice(
            String title,
            String content
    ) {
        return Notices.builder()
                .title(title)
                .content(content)
                .build();
    }

    public static Notices ofTitle(
            String title
    ) {
        return Notices.builder()
                .title(title)
                .content(DEFAULT_CONTENT)
                .build();
    }

    public static Notices ofContent(
            String content
    ) {
        return Notices.builder()
                .title(DEFAULT_TITLE)
                .content(content)
                .build();
    }

    public static Notices of() {
        return Notices.builder()
                .title(DEFAULT_TITLE)
                .content(DEFAULT_CONTENT)
                .build();
    }

}
