package ru.magenta.distance_calculator.db;

import java.sql.SQLException;

public class SQLExceptionMessageService {
    public static String returnErrorMessage(SQLException exception){
        switch (exception.getErrorCode()){
            case 1045:
                return "Incorrect login or password";
            case 1049:
                return "Schema not found";
            case 17002:
                return "Unable to connect to the database";
            case 12899:
                return "Exceeded the maximum possible row size";
            case 1062:
                return "Duplicate primary key";
            default:
                return String.valueOf(exception.getErrorCode());
        }
    }
}
