package com.pp.api.entity;

import com.pp.api.entity.converter.UploadFileContentTypeConverter;
import com.pp.api.entity.enums.UploadFileContentType;
import com.pp.api.entity.enums.UploadFileType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
@NoArgsConstructor(access = PROTECTED)
public class UploadFile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(value = STRING)
    private UploadFileType fileType;

    @Column(nullable = false, length = 2083)
    private String url;

    @Column(nullable = false, length = 255)
    @Convert(converter = UploadFileContentTypeConverter.class)
    private UploadFileContentType contentType;

    @Column(nullable = false)
    private Long contentLength;

    @ManyToOne(fetch = LAZY, optional = false)
    private User uploader;

    @Builder
    private UploadFile(
            UploadFileType fileType,
            String url,
            UploadFileContentType contentType,
            Long contentLength,
            User uploader
    ) {
        this.fileType = fileType;
        this.url = url;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.uploader = uploader;
    }

}
