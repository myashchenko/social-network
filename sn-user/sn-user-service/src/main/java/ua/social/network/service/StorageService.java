package ua.social.network.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Mykola Yashchenko
 */
public interface StorageService {
    String store(MultipartFile file);
    Resource load(String fileName);
}
