package ru.magenta.distance_calculator.db.dao.distance;

import ru.magenta.distance_calculator.db.SQLExceptionMessageService;
import ru.magenta.distance_calculator.db.models.Distance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DistanceDaoImpl implements DistanceDao {
    private final Connection connection;

    public DistanceDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void clearTable() throws Exception {
        try (Statement st = connection.createStatement()) {
            if (st.execute("delete from distance")) st.getConnection().commit();
        } catch (SQLException ex) {
            throw new Exception(SQLExceptionMessageService.returnErrorMessage(ex));
        }
    }

    @Override
    public List<Distance> getAllDistances() throws Exception {
        List<Distance> resultList = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet resultSet = st.executeQuery("SELECT from_city, to_city, distance FROM DISTANCE");
            while (resultSet.next()) {
                resultList.add(new Distance(resultSet.getInt("from_city"),
                        resultSet.getInt("to_city"),
                        resultSet.getDouble("distance")));
            }
            return resultList;
        } catch (SQLException ex) {
            throw new Exception(SQLExceptionMessageService.returnErrorMessage(ex));
        }
    }

    @Override
    public Optional<Distance> getDistanceById(int id) throws Exception {
        try (PreparedStatement st = connection.prepareStatement("SELECT from_city, to_city, distance FROM DISTANCE where id = ?")) {
            st.setInt(1, id);
            ResultSet resultSet = st.getResultSet();
            if (resultSet.next()) {
                return Optional.of(new Distance(resultSet.getInt("from_city"),
                        resultSet.getInt("to_city"),
                        resultSet.getDouble("distance")));
            } else return Optional.empty();
        } catch (SQLException ex) {
            throw new Exception(SQLExceptionMessageService.returnErrorMessage(ex));
        }
    }

    @Override
    public void insertDistance(Distance distance) throws Exception {
        try (PreparedStatement st = connection.prepareStatement("INSERT INTO distance (id, from_city, to_city, distance) VALUES (?, ?, ?, ?)")) {
            st.setInt(1, distance.getId());
            st.setInt(2, distance.getFromCity());
            st.setInt(3, distance.getToCity());
            st.setDouble(4, distance.getDistance());
            if (st.execute()) st.getConnection().commit();
        } catch (SQLException ex) {
            throw new Exception(SQLExceptionMessageService.returnErrorMessage(ex));
        }
    }

    @Override
    public void batchInsertDistances(List<Distance> distances) throws Exception {
        try (PreparedStatement st = connection.prepareStatement("INSERT INTO distance (id, from_city, to_city, distance) VALUES (?, ?, ?, ?)")) {
            for (Distance distance : distances) {
                st.setInt(1, distance.getId());
                st.setInt(2, distance.getFromCity());
                st.setInt(3, distance.getToCity());
                st.setDouble(4, distance.getDistance());
                st.addBatch();
            }
            st.executeBatch();
            st.getConnection().commit();
        } catch (SQLException ex) {
            throw new Exception(SQLExceptionMessageService.returnErrorMessage(ex));
        }
    }
}
