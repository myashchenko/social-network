package ua.social.network.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * @author Mykola Yashchenko
 */
@Configuration
@MapperScan(value = "ua.social.network.query")
public class QueryConfig extends AbstractDbConfig {

    @Bean
    public DataSource dataSource() throws IOException, URISyntaxException {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL);
        getScripts().forEach(builder::addScript);
        return builder.build();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(dataSource);
        sqlSessionFactory.setConfigLocation(new ClassPathResource("mapper/mybatis-config.xml"));
        return sqlSessionFactory.getObject();
    }
}
