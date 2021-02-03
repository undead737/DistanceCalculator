package ru.magenta.distance_calculator.db.dao.concat;

import ru.magenta.distance_calculator.db.SQLExceptionMessageService;
import ru.magenta.distance_calculator.db.models.City;
import ru.magenta.distance_calculator.db.models.ConcatData;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ConcatDaoImpl implements ConcatDao{
    private final Connection connection;

    public ConcatDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<ConcatData> getAll() throws Exception{
        List<ConcatData> resultList = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet resultSet = st.executeQuery("SELECT cFrom.ID FROM_ID, cFrom.NAME FROM_NAME, cFrom.LATITUDE FROM_LATITUDE, " +
                    "cFrom.LONGITUDE FROM_LONGITUDE, cTo.ID TO_ID, cTo.NAME TO_NAME, cTo.LATITUDE TO_LATITUDE, cTo.LONGITUDE TO_LONGITUDE, " +
                    "di.DISTANCE FROM DISTANCE di " +
                    "INNER JOIN CITY cFrom on di.FROM_CITY=cFrom.ID " +
                    "INNER JOIN CITY cTo on di.TO_CITY=cTo.ID");
            while (resultSet.next()) {
                resultList.add(new ConcatData(
                        new City(resultSet.getInt("FROM_ID"), resultSet.getString("FROM_NAME"), resultSet.getString("FROM_LATITUDE"), resultSet.getString("FROM_LONGITUDE")),
                        new City(resultSet.getInt("TO_ID"),resultSet.getString("TO_NAME"), resultSet.getString("TO_LATITUDE"), resultSet.getString("TO_LONGITUDE")),
                        resultSet.getDouble("DISTANCE")));
            }
            return resultList;
        } catch (SQLException ex) {
            throw new Exception(SQLExceptionMessageService.returnErrorMessage(ex));
        }
    }
}
