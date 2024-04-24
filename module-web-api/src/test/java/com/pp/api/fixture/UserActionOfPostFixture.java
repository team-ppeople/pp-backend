package com.pp.api.fixture;

import com.pp.api.entity.Post;
import com.pp.api.entity.UserActionOfPost;
import com.pp.api.entity.User;
import com.pp.api.entity.enums.UserAction;

import static com.pp.api.entity.enums.UserAction.THUMBS_UP;

public class UserActionOfPostFixture {

    private static final UserAction DEFAULT_ACTION = THUMBS_UP;

    public static UserActionOfPost from(
            Post post,
            User user,
            UserAction action
    ) {
        return UserActionOfPost.builder()
                .post(post)
                .user(user)
                .userAction(action)
                .build();
    }

}
