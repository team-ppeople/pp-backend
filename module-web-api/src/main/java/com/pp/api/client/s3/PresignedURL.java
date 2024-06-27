package com.pp.api.client.s3;

public record PresignedURL(
        String uploadUrl,
        String fileUrl
) {
}
