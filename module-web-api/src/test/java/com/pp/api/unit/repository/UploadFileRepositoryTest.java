package com.pp.api.unit.repository;

import com.pp.api.entity.UploadFile;
import com.pp.api.entity.User;
import com.pp.api.entity.enums.UploadFileContentType;
import com.pp.api.entity.enums.UploadFileType;
import com.pp.api.fixture.UploadFileFixture;
import com.pp.api.fixture.UserFixture;
import com.pp.api.repository.UploadFileRepository;
import com.pp.api.repository.UserRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class UploadFileRepositoryTest extends AbstractDataJpaTestContext {

    @Autowired
    private UploadFileRepository uploadFileRepository;

    @Autowired
    private UserRepository userRepository;

    @ParameterizedTest
    @MethodSource(value = "generateFileTypeAndContentType")
    void 업로드파일_엔티티를_영속화한다(
            UploadFileType fileType,
            UploadFileContentType contentType
    ) {
        User user = userRepository.save(UserFixture.of());

        UploadFile uploadFile = UploadFileFixture.fromFileTypeAndContentType(
                fileType,
                contentType,
                user
        );

        UploadFile savedUploadFile = uploadFileRepository.save(uploadFile);

        assertThat(savedUploadFile.getId()).isNotNull();
        assertThat(savedUploadFile.getFileType()).isSameAs(fileType);
        assertThat(savedUploadFile.getContentType()).isSameAs(contentType);
        assertThat(savedUploadFile.getContentLength()).isEqualTo(uploadFile.getContentLength());
        assertThat(savedUploadFile.getUploader()).isEqualTo(uploadFile.getUploader());
        assertThat(savedUploadFile.getCreatedDate()).isNotNull();
        assertThat(savedUploadFile.getUpdatedDate()).isNotNull();
    }

    @ParameterizedTest
    @MethodSource(value = "generateFileTypeAndContentType")
    void 업로드파일_엔티티를_조회한다(
            UploadFileType fileType,
            UploadFileContentType contentType
    ) {
        User user = userRepository.save(UserFixture.of());

        UploadFile uploadFile = UploadFileFixture.fromFileTypeAndContentType(
                fileType,
                contentType,
                user
        );

        UploadFile savedUploadFile = uploadFileRepository.save(uploadFile);

        entityManager.clear();

        UploadFile foundUploadFile = uploadFileRepository.findById(savedUploadFile.getId())
                .orElseThrow();

        assertThat(foundUploadFile).isNotSameAs(savedUploadFile);
        assertThat(foundUploadFile.getId()).isEqualTo(savedUploadFile.getId());
        assertThat(foundUploadFile.getFileType()).isSameAs(savedUploadFile.getFileType());
        assertThat(foundUploadFile.getUrl()).isEqualTo(savedUploadFile.getUrl());
        assertThat(foundUploadFile.getContentType()).isSameAs(savedUploadFile.getContentType());
        assertThat(foundUploadFile.getContentLength()).isEqualTo(savedUploadFile.getContentLength());
        assertThat(foundUploadFile.getUploader()).isEqualTo(savedUploadFile.getUploader());
        assertThat(foundUploadFile.getCreatedDate()).isEqualTo(savedUploadFile.getCreatedDate());
        assertThat(foundUploadFile.getUpdatedDate()).isEqualTo(savedUploadFile.getUpdatedDate());
    }

    static Stream<Arguments> generateFileTypeAndContentType() {
        Stream.Builder<Arguments> builder = Stream.builder();

        for (UploadFileType fileType : UploadFileType.values()) {
            for (UploadFileContentType contentType : UploadFileContentType.values()) {
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
