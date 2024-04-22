package com.pp.api.fixture;

import com.pp.api.entity.PostImages;
import com.pp.api.entity.Posts;
import com.pp.api.entity.UploadFiles;

public class PostImageFixture {

    public static PostImages from(
            Posts post,
            UploadFiles uploadFile
    ) {
        return PostImages.builder()
                .post(post)
                .uploadFile(uploadFile)
                .build();
    }

}
