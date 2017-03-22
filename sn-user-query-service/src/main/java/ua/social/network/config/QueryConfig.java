package ua.social.network.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * @author Mykola Yashchenko
 */
@Configuration
@MapperScan(value = "ua.social.network.query")
public class QueryConfig {

    private static final String TABLES_FOLDER_PATH = "tables/";
    private static final String TABLES_ASSOCS_FOLDER_PATH = "tables_assocs/";

    @Bean
    public DataSource dataSource() throws IOException, URISyntaxException {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL);
        getFiles(TABLES_FOLDER_PATH).forEach(builder::addScript);
        getFiles(TABLES_ASSOCS_FOLDER_PATH).forEach(builder::addScript);
        return builder.build();
    }

    @Bean
    @Autowired
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setConfigLocation(new ClassPathResource("mapper/mybatis-config.xml"));
        return sqlSessionFactory.getObject();
    }

    private Stream<String> getFiles(String rootDirectory) throws IOException {
        Path table = Paths.get(new ClassPathResource(rootDirectory).getURI());
        return Files.walk(table)
                .filter(p -> !p.toFile().isDirectory())
                .map(Path::getFileName)
                .map(Path::toString)
                .map(p -> rootDirectory + p);
    }
}
