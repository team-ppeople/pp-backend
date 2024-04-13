package com.pp.api.entity.converter;

import com.pp.api.entity.enums.UploadFileContentTypes;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UploadFileContentTypeConverter implements AttributeConverter<UploadFileContentTypes, String> {

    @Override
    public String convertToDatabaseColumn(UploadFileContentTypes contentType) {
        return contentType.getType();
    }

    @Override
    public UploadFileContentTypes convertToEntityAttribute(String contentType) {
        return UploadFileContentTypes.fromType(contentType);
    }

}
