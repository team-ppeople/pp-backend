package com.pp.api.service;

import com.pp.api.client.s3.PresignedURL;
import com.pp.api.client.s3.S3PresignedClient;
import com.pp.api.controller.dto.PresignedUploadFile;
import com.pp.api.controller.dto.PresignedUploadUrlRequest;
import com.pp.api.entity.UploadFile;
import com.pp.api.entity.User;
import com.pp.api.repository.UploadFileRepository;
import com.pp.api.repository.UserRepository;
import com.pp.api.util.JwtAuthenticationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UploadFileService {

    private final S3PresignedClient s3PresignedClient;

    private final UserRepository userRepository;

    private final UploadFileRepository uploadFileRepository;

    public PresignedUploadFile savePresignedUploadFile(PresignedUploadUrlRequest request) {

        PresignedURL presignedURL = s3PresignedClient.createPutPresignedUrl(request);

        Long uploaderId = JwtAuthenticationUtil.getAuthenticatedUserId();

        User user = userRepository.getReferenceById(uploaderId);

        UploadFile uploadFile = UploadFile.builder()
                .fileType(request.fileType())
                .url(presignedURL.getFileUrl())
                .contentType(request.fileContentType())
                .contentLength(request.fileContentLength())
                .uploader(user)
                .build();

        UploadFile savedUploadFile = uploadFileRepository.save(uploadFile);

        return new PresignedUploadFile(
                savedUploadFile.getId(),
                request.fileName(),
                presignedURL.getUploadUrl(),
                savedUploadFile.getUrl()
        );
    }

}
