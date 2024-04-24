package com.pp.api.entity.converter;

import com.pp.api.entity.enums.UploadFileContentType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UploadFileContentTypeConverter implements AttributeConverter<UploadFileContentType, String> {

    @Override
    public String convertToDatabaseColumn(UploadFileContentType contentType) {
        return contentType.getType();
    }

    @Override
    public UploadFileContentType convertToEntityAttribute(String contentType) {
        return UploadFileContentType.fromType(contentType);
    }

}
