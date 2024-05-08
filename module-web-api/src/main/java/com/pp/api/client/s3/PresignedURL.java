package com.pp.api.client.s3;

import java.net.URL;

public record PresignedURL(
        URL url
) {

    public String getUploadUrl() {
        return url.toExternalForm();
    }

    public String getFileUrl() {
        return url.getProtocol() + "://" + url.getHost() + url.getPath();
    }
}
