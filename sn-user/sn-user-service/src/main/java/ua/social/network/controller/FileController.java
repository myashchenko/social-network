package ua.social.network.controller;

import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ua.social.network.service.StorageService;

/**
 * @author Mykola Yashchenko
 */
@RestController
@RequestMapping("files")
@AllArgsConstructor
public class FileController {

    private final StorageService storageService;

    @GetMapping("/{fileName}")
    public Resource getFile(@PathVariable("fileName") String fileName) {
        return storageService.load(fileName);
    }
}
