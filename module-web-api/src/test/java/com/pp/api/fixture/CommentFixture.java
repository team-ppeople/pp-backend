package com.pp.api.fixture;

import com.pp.api.entity.Comments;
import com.pp.api.entity.Posts;

public class CommentFixture {

    private static final String DEFAULT_CONTENT = "WOW! 29번째 생일 넘우 넘우 축하드려요~ 👏";

    public static Comments from(
            String content,
            Posts post
    ) {
        return Comments.builder()
                .content(content)
                .post(post)
                .build();
    }

    public static Comments ofPost(Posts post) {
        return Comments.builder()
                .content(DEFAULT_CONTENT)
                .post(post)
                .build();
    }

}
