package ua.social.network.service;

import java.io.InputStream;

import ua.social.network.domain.FileMetadata;
import ua.social.network.exception.FileNotFoundException;

/**
 * @author Mykola Yashchenko
 */
public interface StorageService {
    String store(FileMetadata request);

    default InputStream load(String fileName) {
        throw new FileNotFoundException();
    }
}
