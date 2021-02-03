package ru.magenta.distance_calculator.db.connection;

import java.sql.Connection;

public interface DBConnection {
    Connection getConnection();
}
