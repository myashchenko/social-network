package ua.social.network.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Mykola Yashchenko
 */
@Configuration
@MapperScan(value = "ua.social.network.query")
@PropertySource("classpath:tables_order.properties")
public class QueryConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(QueryConfig.class);

    private static final String TABLES_FOLDER_PATH = "tables/";

    private static final List<String> order = new ArrayList<>();

    @Bean
    public DataSource dataSource() throws IOException, URISyntaxException {
        LOGGER.debug("PATHS: " + ClassLoader.getSystemResource(TABLES_FOLDER_PATH));
        Path table = Paths.get(new ClassPathResource(TABLES_FOLDER_PATH).getURI());
//        Path table = Paths.get(ClassLoader.getSystemResource(TABLES_FOLDER_PATH).toURI());
        List<String> tables = Files.walk(table)
                .filter(p -> !p.toFile().isDirectory())
                .map(Path::getFileName)
                .map(Path::toString)
                .sorted((o1, o2) -> order.indexOf(o1) - order.indexOf(o2))
                .map(p -> TABLES_FOLDER_PATH + p)
                .collect(Collectors.toList());
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL);
        for (String tableName : tables) {
            builder.addScript(tableName);
        }
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

    @Value("${order}")
    public void setOrder(String orderStr) {
        if (orderStr != null) {
            for (String tableName : orderStr.split(",")) {
                order.add(tableName);
            }
        }
    }
}
