package ua.social.network.controller;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import ua.social.network.api.UploadFileResponse;
import ua.social.network.domain.FileMetadata;
import ua.social.network.exception.StorageException;
import ua.social.network.service.StorageService;

/**
 * @author Mykola Yashchenko
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/files")
public class FileController {

    private final StorageService storageService;

    @GetMapping("/{filename}")
    public InputStream get(@PathVariable("filename") final String fileName) {
        return storageService.load(fileName);
    }

    @PostMapping
    public UploadFileResponse upload(final MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file %s", file.getOriginalFilename());
        }

        final FileMetadata fileMetadata = new FileMetadata(file);
        final String url = storageService.store(fileMetadata);
        return new UploadFileResponse(url);
    }
}
