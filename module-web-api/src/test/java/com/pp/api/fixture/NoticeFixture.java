package com.pp.api.fixture;

import com.pp.api.entity.Notice;

public class NoticeFixture {

    private static final String DEFAULT_TITLE = "[ALL] 📣PP를 사용해주시는 고객님들께 감사의 말씀을 전해드립니다 ❤️";

    private static final String DEFAULT_CONTENT = "hi~ 모두들 10002 10002 이용해주세요 🙇🏻‍";

    public static Notice createNotice(
            String title,
            String content
    ) {
        return Notice.builder()
                .title(title)
                .content(content)
                .build();
    }

    public static Notice ofTitle(
            String title
    ) {
        return Notice.builder()
                .title(title)
                .content(DEFAULT_CONTENT)
                .build();
    }

    public static Notice ofContent(
            String content
    ) {
        return Notice.builder()
                .title(DEFAULT_TITLE)
                .content(content)
                .build();
    }

    public static Notice of() {
        return Notice.builder()
                .title(DEFAULT_TITLE)
                .content(DEFAULT_CONTENT)
                .build();
    }

}
