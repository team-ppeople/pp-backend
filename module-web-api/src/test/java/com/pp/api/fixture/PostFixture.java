package com.pp.api.fixture;

import com.pp.api.entity.Post;
import com.pp.api.entity.User;

public class PostFixture {

    private static final String DEFAULT_TITLE = "[HBD] 🎂저의 29번째 생일을 축하합니다.🥳";

    private static final String DEFAULT_CONTENT = "yo~ 모두들 10002 10002 축하해주세요 😄";

    public static Post from(
            String title,
            String content,
            User creator
    ) {
        return Post.builder()
                .title(title)
                .content(content)
                .creator(creator)
                .build();
    }

    public static Post fromTitle(
            String title,
            User creator
    ) {
        return Post.builder()
                .title(title)
                .content(DEFAULT_CONTENT)
                .creator(creator)
                .build();
    }

    public static Post fromContent(
            String content,
            User creator
    ) {
        return Post.builder()
                .title(DEFAULT_TITLE)
                .content(content)
                .creator(creator)
                .build();
    }

    public static Post ofCreator(User creator) {
        return Post.builder()
                .title(DEFAULT_TITLE)
                .content(DEFAULT_CONTENT)
                .creator(creator)
                .build();
    }

}
