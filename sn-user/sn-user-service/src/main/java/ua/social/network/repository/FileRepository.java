package ua.social.network.repository;

import org.springframework.data.repository.CrudRepository;

import ua.social.network.entity.File;

/**
 * @author Mykola Yashchenko
 */
public interface FileRepository extends CrudRepository<File, String> {
    File findByFilePath(String filePath);
}
