package com.pp.api.fixture;

import com.pp.api.entity.Post;
import com.pp.api.entity.ReportedPost;
import com.pp.api.entity.User;

public class ReportedPostFixture {

    public static ReportedPost from(
            Post post,
            User reporter
    ) {
        return ReportedPost.builder()
                .post(post)
                .reporter(reporter)
                .build();
    }

}
