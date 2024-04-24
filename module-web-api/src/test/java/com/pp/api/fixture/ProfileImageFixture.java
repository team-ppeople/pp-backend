package com.pp.api.fixture;

import com.pp.api.entity.ProfileImage;
import com.pp.api.entity.UploadFile;
import com.pp.api.entity.User;

public class ProfileImageFixture {

    public static ProfileImage from(
            User user,
            UploadFile uploadFile
    ) {
        return ProfileImage.builder()
                .user(user)
                .uploadFile(uploadFile)
                .build();
    }

}
