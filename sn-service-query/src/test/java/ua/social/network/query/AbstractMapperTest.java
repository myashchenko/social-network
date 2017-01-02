package ua.social.network.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Mykola Yashchenko
 */
public abstract class AbstractMapperTest {

    private Connection connection;

    protected void loadSql(String sqlFileName) {
        ScriptUtils.executeSqlScript(connection, new FileSystemResource(sqlFileName));
    }

    @Autowired
    public void setConnection(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }
}
