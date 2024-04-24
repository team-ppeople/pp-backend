package com.pp.api.fixture;

import com.pp.api.entity.Posts;
import com.pp.api.entity.Users;

public class PostFixture {

    private static final String DEFAULT_TITLE = "[HBD] 🎂저의 29번째 생일을 축하합니다.🥳";

    private static final String DEFAULT_CONTENT = "yo~ 모두들 10002 10002 축하해주세요 😄";

    public static Posts from(
            String title,
            String content,
            Users creator
    ) {
        return Posts.builder()
                .title(title)
                .content(content)
                .creator(creator)
                .build();
    }

    public static Posts fromTitle(
            String title,
            Users creator
    ) {
        return Posts.builder()
                .title(title)
                .content(DEFAULT_CONTENT)
                .creator(creator)
                .build();
    }

    public static Posts fromContent(
            String content,
            Users creator
    ) {
        return Posts.builder()
                .title(DEFAULT_TITLE)
                .content(content)
                .creator(creator)
                .build();
    }

    public static Posts ofCreator(Users creator) {
        return Posts.builder()
                .title(DEFAULT_TITLE)
                .content(DEFAULT_CONTENT)
                .creator(creator)
                .build();
    }

}
