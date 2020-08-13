package hr.algebra.repository.sql;

import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.sql.DataSource;

/**
 * @author Kevin Furjan
 */
public class DataSourceSingleton {

    private static final String SERVER_NAME = "localhost";
    private static final String DATABASE_NAME = "PUJ1-Database";
    private static final String USER = "sql-login";
    private static final String PASSWORD = "sqlServer01.";

    private static DataSource instance;

    private DataSourceSingleton() {}

    public static DataSource getInstance() {

        if (instance == null) {
            instance = createInstance();
        }
        return instance;
    }

    private static DataSource createInstance() {

        SQLServerDataSource dataSource = new SQLServerDataSource();

        dataSource.setServerName(SERVER_NAME);
        dataSource.setDatabaseName(DATABASE_NAME);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);

        return dataSource;
    }
}
