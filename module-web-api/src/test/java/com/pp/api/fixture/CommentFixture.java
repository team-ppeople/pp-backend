package com.pp.api.fixture;

import com.pp.api.entity.Comment;
import com.pp.api.entity.Post;
import com.pp.api.entity.User;

public class CommentFixture {

    private static final String DEFAULT_CONTENT = "WOW! 29번째 생일 넘우 넘우 축하드려요~ 👏";

    public static Comment from(
            String content,
            Post post,
            User creator
    ) {
        return Comment.builder()
                .content(content)
                .post(post)
                .creator(creator)
                .build();
    }

    public static Comment fromPostAndCreator(
            Post post,
            User creator
    ) {
        return Comment.builder()
                .content(DEFAULT_CONTENT)
                .post(post)
                .creator(creator)
                .build();
    }

}
