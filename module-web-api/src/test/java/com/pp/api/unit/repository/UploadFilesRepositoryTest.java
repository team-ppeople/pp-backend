package com.pp.api.unit.repository;

import com.pp.api.entity.UploadFiles;
import com.pp.api.entity.enums.UploadFileContentTypes;
import com.pp.api.entity.enums.UploadFileTypes;
import com.pp.api.repository.UploadFilesRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class UploadFilesRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private UploadFilesRepository uploadFilesRepository;

    @ParameterizedTest
    @MethodSource(value = "generateFileTypeAndContentType")
    void 업로드파일_엔티티를_영속화한다(
            UploadFileTypes fileType,
            UploadFileContentTypes contentType
    ) {
        // given
        String url = "https://avatars.githubusercontent.com/u/52724515";
        long contentLength = 1048576L;

        // when
        UploadFiles uploadFile = UploadFiles.builder()
                .fileType(fileType)
                .url(url)
                .contentType(contentType)
                .contentLength(contentLength)
                .build();

        UploadFiles savedUploadFile = uploadFilesRepository.save(uploadFile);

        // then
        assertThat(savedUploadFile.getId()).isNotNull();
        assertThat(savedUploadFile.getFileType()).isSameAs(fileType);
        assertThat(savedUploadFile.getContentType()).isSameAs(contentType);
        assertThat(savedUploadFile.getContentLength()).isEqualTo(contentLength);
        assertThat(savedUploadFile.getCreatedDate()).isNotNull();
        assertThat(savedUploadFile.getUpdatedDate()).isNotNull();
    }


    @ParameterizedTest
    @MethodSource(value = "generateFileTypeAndContentType")
    void 업로드파일_엔티티를_조회한다(
            UploadFileTypes fileType,
            UploadFileContentTypes contentType
    ) {
        // given
        String url = "https://avatars.githubusercontent.com/u/52724515";
        long contentLength = 1048576L;

        // when
        UploadFiles uploadFile = UploadFiles.builder()
                .fileType(fileType)
                .url(url)
                .contentType(contentType)
                .contentLength(contentLength)
                .build();

        UploadFiles savedUploadFile = uploadFilesRepository.save(uploadFile);

        entityManager.clear();

        UploadFiles foundUploadFile = uploadFilesRepository.findById(savedUploadFile.getId())
                .orElseThrow();

        // then
        assertThat(foundUploadFile).isNotSameAs(savedUploadFile);
        assertThat(foundUploadFile.getId()).isEqualTo(savedUploadFile.getId());
        assertThat(foundUploadFile.getFileType()).isSameAs(savedUploadFile.getFileType());
        assertThat(foundUploadFile.getUrl()).isEqualTo(savedUploadFile.getUrl());
        assertThat(foundUploadFile.getContentType()).isSameAs(savedUploadFile.getContentType());
        assertThat(foundUploadFile.getContentLength()).isEqualTo(savedUploadFile.getContentLength());
        assertThat(foundUploadFile.getCreatedDate()).isEqualTo(savedUploadFile.getCreatedDate());
        assertThat(foundUploadFile.getUpdatedDate()).isEqualTo(savedUploadFile.getUpdatedDate());
    }

    static Stream<Arguments> generateFileTypeAndContentType() {
        Stream.Builder<Arguments> builder = Stream.builder();

        for (UploadFileTypes fileType : UploadFileTypes.values()) {
            for (UploadFileContentTypes contentType : UploadFileContentTypes.values()) {
                builder.add(
                        Arguments.arguments(
                                fileType,
                                contentType
                        )
                );
            }
        }

        return builder.build();
    }

}
