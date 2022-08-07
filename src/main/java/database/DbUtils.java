package database;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DbUtils {
    private static MysqlDataSource dataSource;

    public static Connection connection() throws SQLException {
        if (dataSource == null) {
            initDataSource();
        }
        return dataSource.getConnection();
    }

    private static void initDataSource() {
        dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://127.0.0.1:3306/investment?characterEncoding=utf8");
        dataSource.setUser("investment");
        dataSource.setPassword("investment90042703");
    }
}
