package com.pp.api.fixture;

import com.pp.api.entity.PostImage;
import com.pp.api.entity.Post;
import com.pp.api.entity.UploadFile;

public class PostImageFixture {

    public static PostImage from(
            Post post,
            UploadFile uploadFile
    ) {
        return PostImage.builder()
                .post(post)
                .uploadFile(uploadFile)
                .build();
    }

}
