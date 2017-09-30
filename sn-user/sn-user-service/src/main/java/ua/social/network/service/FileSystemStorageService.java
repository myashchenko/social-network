package ua.social.network.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import ua.social.network.exception.EntityNotFoundException;
import ua.social.network.exception.StorageException;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * @author Mykola Yashchenko
 *
 * todo save files in different folders
 */
@Service
public class FileSystemStorageService implements StorageService {

    private final Path filesRootPath;

    public FileSystemStorageService(@Value("${filesRootPath}") String filesRootPath) {
        this.filesRootPath = Paths.get(filesRootPath);
    }

    @Override
    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {

            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file %s", filename);
            }

            if (filename.contains("..")) {
                throw new StorageException(
                        "Cannot store file with relative path outside current directory %s", filename);
            }

            Files.copy(file.getInputStream(), filesRootPath.resolve(filename), REPLACE_EXISTING);
            return filesRootPath.resolve(filename).toString();
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Resource load(String fileName) {
        try {
            Path file = filesRootPath.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new EntityNotFoundException("Could not read file: %s", fileName);

            }
        } catch (MalformedURLException e) {
            throw new EntityNotFoundException("Could not read file: " + fileName, e);
        }
    }
}
