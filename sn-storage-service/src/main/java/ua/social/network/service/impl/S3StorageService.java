package ua.social.network.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ua.social.network.domain.FileMetadata;
import ua.social.network.service.StorageService;

/**
 * @author Mykola Yashchenko
 */
@Service
@Profile("production")
public class S3StorageService implements StorageService {

    @Override
    public String store(final FileMetadata fileMetadata) {
        return null;
    }
}
