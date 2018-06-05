package ua.social.network.storage.service;

import org.springframework.core.io.Resource;

import ua.social.network.exception.SnException;
import ua.social.network.exception.StorageServiceExceptionCode;
import ua.social.network.storage.domain.FileMetadata;

/**
 * @author Mykola Yashchenko
 */
public interface StorageService {
    String store(FileMetadata request);

    default Resource load(String fileName) {
        throw new SnException(StorageServiceExceptionCode.FILE_NOT_FOUND);
    }
}
