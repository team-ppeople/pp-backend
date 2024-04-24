package com.pp.api.fixture;

import com.pp.api.entity.Comment;
import com.pp.api.entity.ReportedComment;
import com.pp.api.entity.User;

public class ReportedCommentFixture {

    public static ReportedComment from(
            Comment comment,
            User reporter
    ) {
        return ReportedComment.builder()
                .comment(comment)
                .reporter(reporter)
                .build();
    }

}
