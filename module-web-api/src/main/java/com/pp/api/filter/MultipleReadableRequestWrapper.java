package com.pp.api.filter;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.springframework.util.StreamUtils.copyToByteArray;

public final class MultipleReadableRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] payload;

    public MultipleReadableRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        this.payload = copyToByteArray(request.getInputStream());
    }

    @Override
    public ServletInputStream getInputStream() {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.payload);

        return new ServletInputStream() {

            @Override
            public boolean isFinished() {
                return byteArrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {
                throw new UnsupportedOperationException();
            }

            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

}
