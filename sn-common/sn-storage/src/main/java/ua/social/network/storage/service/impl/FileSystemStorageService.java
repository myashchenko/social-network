package ua.social.network.storage.service.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ua.social.network.exception.SnException;
import ua.social.network.exception.StorageServiceExceptionCode;
import ua.social.network.storage.domain.FileMetadata;
import ua.social.network.storage.service.StorageService;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * @author Mykola Yashchenko
 */
@Service
@Profile("!production")
public class FileSystemStorageService implements StorageService {

    private static final String FILE_URL_PATTERN = "%s/api/files/%s";

    private final Path filesRoot;
    private final String host;

    public FileSystemStorageService(@Value("${filesystem.storage.path}") final String path,
                                    @Value("${filesystem.storage.hostname}") final String hostname) {
        this.filesRoot = Paths.get(path);
        this.host = hostname;
    }

    @Override
    public String store(final FileMetadata fileMetadata) {
        final String filename = StringUtils.cleanPath(fileMetadata.fileName);

        try {
            if (filename.contains("..")) {
                throw new SnException(StorageServiceExceptionCode.STORAGE_EXCEPTION);
            }

            final String newFileName = UUID.randomUUID().toString() + "_" + filename;
            final Path filePath = filesRoot.resolve(newFileName);
            Files.copy(fileMetadata.inputStream, filePath, REPLACE_EXISTING);
            return String.format(FILE_URL_PATTERN, host, newFileName);
        } catch (final IOException e) {
            throw new SnException(StorageServiceExceptionCode.STORAGE_EXCEPTION);
        }
    }

    @Override
    public Resource load(final String fileName) {
        try {
            final Path file = filesRoot.resolve(fileName);
            final Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new SnException(StorageServiceExceptionCode.FILE_NOT_FOUND);
            }
        } catch (final IOException e) {
            throw new SnException(StorageServiceExceptionCode.FILE_NOT_FOUND);
        }
    }
}
