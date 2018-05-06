package ua.social.network.service;

import ua.social.network.domain.FileMetadata;

/**
 * @author Mykola Yashchenko
 */
public interface StorageService {
    String store(FileMetadata request);
}
