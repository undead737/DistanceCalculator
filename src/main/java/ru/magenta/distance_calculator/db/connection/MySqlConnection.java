package ru.magenta.distance_calculator.db.connection;

import ru.magenta.distance_calculator.db.SQLExceptionMessageService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Подключение к MySql
 */
public class MySqlConnection implements DBConnection {
    private static final String host = "localhost";
    private static final short port = 3306;
    private static final String dbName = "DISTANCE-CALCULATOR";
    private Connection connection;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(MySqlConnection.class.getName());
    }

    {
        try {
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d /%s", host, port, dbName), "root", "root");
            connection.setAutoCommit(false);
        } catch (SQLException ex) {
            logger.log(Level.WARNING, SQLExceptionMessageService.returnErrorMessage(ex));
        }
    }

    @Override
    public java.sql.Connection getConnection() {
        return connection;
    }
}
