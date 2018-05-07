package ua.social.network.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import ua.social.network.api.UploadFileResponse;
import ua.social.network.domain.FileMetadata;
import ua.social.network.exception.SnException;
import ua.social.network.exception.StorageServiceExceptionCode;
import ua.social.network.service.StorageService;

/**
 * @author Mykola Yashchenko
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/files")
public class FileController {

    private final StorageService storageService;

    @GetMapping("/{filename}")
    public Resource get(@PathVariable("filename") final String fileName) {
        return storageService.load(fileName);
    }

    @PostMapping
    public UploadFileResponse upload(@RequestParam("file") final MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new SnException(StorageServiceExceptionCode.STORAGE_EXCEPTION);
        }

        final String url = storageService.store(new FileMetadata(file));
        return new UploadFileResponse(url);
    }
}
