package com.pp.api.fixture;

import com.pp.api.entity.ProfileImages;
import com.pp.api.entity.UploadFiles;
import com.pp.api.entity.Users;

public class ProfileImageFixture {

    public static ProfileImages from(
            Users user,
            UploadFiles uploadFile
    ) {
        return ProfileImages.builder()
                .user(user)
                .uploadFile(uploadFile)
                .build();
    }

}
