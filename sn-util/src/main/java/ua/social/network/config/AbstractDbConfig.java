package ua.social.network.config;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author Mykola Yashchenko
 */
public abstract class AbstractDbConfig {

    private static final String TABLES_FOLDER_PATH = "tables/";
    private static final String TABLES_ASSOCS_FOLDER_PATH = "tables_assocs/";

    protected Stream<String> getScripts() throws IOException {
        return Stream.concat(getFiles(TABLES_FOLDER_PATH), getFiles(TABLES_ASSOCS_FOLDER_PATH));
    }

    protected Stream<String> getFiles(String rootDirectory) throws IOException {
        Path table = Paths.get(new ClassPathResource(rootDirectory).getURI());
        return Files.walk(table)
                .filter(p -> !p.toFile().isDirectory())
                .map(Path::getFileName)
                .map(Path::toString)
                .map(p -> rootDirectory + p);
    }
}
