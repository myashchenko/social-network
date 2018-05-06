package ua.social.network.domain;

import lombok.AllArgsConstructor;

/**
 * @author Mykola Yashchenko
 */
@AllArgsConstructor
public class FileMetadata {
    private final byte[] binary;
    private final String fileName;
}
