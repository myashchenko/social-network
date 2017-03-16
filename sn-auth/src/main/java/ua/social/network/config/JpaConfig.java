package ua.social.network.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.stream.Stream;

/**
 * @author Mykola Yashchenko
 */
@Configuration
public class JpaConfig {

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
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("ua.social.network");
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put(org.hibernate.cfg.Environment.DIALECT, "org.hibernate.dialect.HSQLDialect");
        jpaProperties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "none");
        entityManagerFactoryBean.setJpaProperties(jpaProperties);

        return entityManagerFactoryBean;
    }

    @Bean
    public PlatformTransactionManager transactionManager(LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return jpaTransactionManager;
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
