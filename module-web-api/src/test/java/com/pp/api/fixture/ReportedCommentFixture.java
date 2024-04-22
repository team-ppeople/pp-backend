package com.pp.api.fixture;

import com.pp.api.entity.Comments;
import com.pp.api.entity.ReportedComments;
import com.pp.api.entity.Users;

public class ReportedCommentFixture {

    public static ReportedComments from(
            Comments comment,
            Users reporter
    ) {
        return ReportedComments.builder()
                .comment(comment)
                .reporter(reporter)
                .build();
    }

}
