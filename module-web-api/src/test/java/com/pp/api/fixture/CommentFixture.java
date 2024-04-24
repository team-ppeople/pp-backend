package com.pp.api.fixture;

import com.pp.api.entity.Comment;
import com.pp.api.entity.Post;

public class CommentFixture {

    private static final String DEFAULT_CONTENT = "WOW! 29번째 생일 넘우 넘우 축하드려요~ 👏";

    public static Comment from(
            String content,
            Post post
    ) {
        return Comment.builder()
                .content(content)
                .post(post)
                .build();
    }

    public static Comment ofPost(Post post) {
        return Comment.builder()
                .content(DEFAULT_CONTENT)
                .post(post)
                .build();
    }

}
