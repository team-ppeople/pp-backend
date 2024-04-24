package com.pp.api.fixture;

import com.pp.api.entity.Posts;
import com.pp.api.entity.ReportedPosts;
import com.pp.api.entity.Users;

public class ReportedPostFixture {

    public static ReportedPosts from(
            Posts post,
            Users reporter
    ) {
        return ReportedPosts.builder()
                .post(post)
                .reporter(reporter)
                .build();
    }

}
