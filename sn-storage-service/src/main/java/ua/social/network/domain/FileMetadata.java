package ua.social.network.domain;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Mykola Yashchenko
 */
public class FileMetadata {
    public final InputStream inputStream;
    public final String fileName;
    public final String contentType;
    public final Long contentLength;

    public FileMetadata(final MultipartFile file) throws IOException {
        inputStream = file.getInputStream();
        fileName = file.getOriginalFilename();
        contentType = file.getContentType();
        contentLength = file.getSize();
    }
}
