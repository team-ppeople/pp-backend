package com.pp.api.entity;

import com.pp.api.entity.converter.UploadFileContentTypeConverter;
import com.pp.api.entity.enums.UploadFileContentTypes;
import com.pp.api.entity.enums.UploadFileTypes;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = PROTECTED)
public class UploadFiles extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private UploadFileTypes fileType;

    @Column(nullable = false, length = 2083)
    private String url;

    @Column(nullable = false, length = 255)
    @Convert(converter = UploadFileContentTypeConverter.class)
    private UploadFileContentTypes contentType;

    @Column(nullable = false)
    private Long contentLength;

    @Builder
    private UploadFiles(
            UploadFileTypes fileType,
            String url,
            UploadFileContentTypes contentType,
            Long contentLength
    ) {
        this.fileType = fileType;
        this.url = url;
        this.contentType = contentType;
        this.contentLength = contentLength;
    }

}
