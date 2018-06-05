package ua.social.network.storage.domain;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import lombok.SneakyThrows;

/**
 * @author Mykola Yashchenko
 */
public class FileMetadata {
    public final InputStream inputStream;
    public final String fileName;
    public final String contentType;
    public final Long contentLength;

    @SneakyThrows
    public FileMetadata(final MultipartFile file) {
        inputStream = file.getInputStream();
        fileName = file.getOriginalFilename();
        contentType = file.getContentType();
        contentLength = file.getSize();
    }
}
