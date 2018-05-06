package ua.social.network.controller;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import ua.social.network.api.UploadFileRequest;
import ua.social.network.api.UploadFileResponse;
import ua.social.network.domain.FileMetadata;
import ua.social.network.service.StorageService;

/**
 * @author Mykola Yashchenko
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/files")
public class FileController {

    private final StorageService storageService;

    @PostMapping
    public UploadFileResponse upload(final MultipartFile file, @RequestBody final UploadFileRequest request) throws IOException {
        final byte[] binary = IOUtils.toByteArray(file.getInputStream());
        final String url = storageService.store(new FileMetadata(binary, request.getFileName()));
        return new UploadFileResponse(url);
    }
}
