package ua.social.network.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import ua.social.network.domain.FileMetadata;
import ua.social.network.exception.FileNotFoundException;
import ua.social.network.exception.StorageException;
import ua.social.network.service.StorageService;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * @author Mykola Yashchenko
 */
@Service
@Profile("!production")
public class FileSystemStorageService implements StorageService {

    private static final Path FILES_ROOT = Paths.get("/files");

    @Override
    public String store(final FileMetadata fileMetadata) {
        final String filename = StringUtils.cleanPath(fileMetadata.fileName);

        try {
            if (filename.contains("..")) {
                throw new StorageException(
                        "Cannot store file with relative path outside current directory %s", filename);
            }

            final Path filePath = FILES_ROOT.resolve(filename);
            Files.copy(fileMetadata.inputStream, filePath, REPLACE_EXISTING);
            return filePath.toString();
        } catch (final IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public InputStream load(final String fileName) {
        try {
            final Path file = FILES_ROOT.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource.getInputStream();
            } else {
                throw new FileNotFoundException();

            }
        } catch (final IOException e) {
            throw new FileNotFoundException();
        }
    }
}
