package com.pp.api.fixture;

import com.pp.api.entity.Posts;
import com.pp.api.entity.UserActionsOfPost;
import com.pp.api.entity.Users;
import com.pp.api.entity.enums.UserActions;

import static com.pp.api.entity.enums.UserActions.THUMBS_UP;

public class UserActionOfPostFixture {

    private static final UserActions DEFAULT_ACTION = THUMBS_UP;

    public static UserActionsOfPost from(
            Posts post,
            Users user,
            UserActions action
    ) {
        return UserActionsOfPost.builder()
                .post(post)
                .user(user)
                .userAction(action)
                .build();
    }

}
