package ru.magenta.distance_calculator.db.dao.city;

import ru.magenta.distance_calculator.db.SQLExceptionMessageService;
import ru.magenta.distance_calculator.db.models.City;
import ru.magenta.distance_calculator.db.models.Distance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CityDaoImpl implements CityDao {
    private final Connection connection;

    public CityDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<City> getAllCities() throws Exception{
        List<City> resultList = new ArrayList<>();
        try (Statement st = connection.createStatement()) {
            ResultSet resultSet = st.executeQuery("SELECT * FROM city");
            while (resultSet.next()) {
                resultList.add(new City(resultSet.getInt("ID"),
                        resultSet.getString("NAME"),
                        resultSet.getString("LATITUDE"),
                        resultSet.getString("LONGITUDE")));
            }
            return resultList;
        } catch (SQLException ex) {
            throw new Exception(SQLExceptionMessageService.returnErrorMessage(ex));
        }
    }

    @Override
    public Optional<City> getCityById(int id) throws Exception{
        try (PreparedStatement st = connection.prepareStatement("select id, name, latitude, longitude from city where id=?")) {
            st.setInt(1, id);
            ResultSet resultSet = st.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new City(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("latitude"),
                        resultSet.getString("longitude")));
            } else return Optional.empty();
        } catch (SQLException ex) {
            throw new Exception(SQLExceptionMessageService.returnErrorMessage(ex));
        }
    }

    @Override
    public List<City> getCitiesByName(String name) {
        return null;
    }

    @Override
    public List<City> getCitiesByPosition(String latitude, String longitude) {
        return null;
    }

    @Override
    public void insertCity(City city) throws Exception{
        try (PreparedStatement st = connection.prepareStatement("INSERT INTO CITY (ID, NAME, LATITUDE, LONGITUDE) VALUES (?, ?, ?, ?)")) {
            st.setInt(1, city.getId());
            st.setString(2, city.getName());
            st.setString(3, city.getLatitude());
            st.setString(4, city.getLongitude());
            if (st.execute()) st.getConnection().commit();
        } catch (SQLException ex) {
            throw new Exception(SQLExceptionMessageService.returnErrorMessage(ex));
        }
    }

    @Override
    public void batchInsertCities(Set<City> cities) throws Exception{
        try (PreparedStatement st = connection.prepareStatement("INSERT INTO CITY (ID, NAME, LATITUDE, LONGITUDE) VALUES (?, ?, ?, ?)")){
            for (City city : cities){
                st.setInt(1, city.getId());
                st.setString(2, city.getName());
                st.setString(3, city.getLatitude());
                st.setString(4, city.getLongitude());
                st.addBatch();
            }
            st.executeBatch();
            st.getConnection().commit();
        } catch (SQLException ex) {
            throw new Exception(SQLExceptionMessageService.returnErrorMessage(ex));
        }
    }

    @Override
    public void updateCity(City city) {
        //
    }

    @Override
    public void deleteCity(City city) {
        //
    }

    @Override
    public void clearTable() throws Exception{
        try (Statement st = connection.createStatement()){
            if (st.execute("delete from city")) st.getConnection().commit();
        } catch (SQLException ex) {
            throw new Exception(SQLExceptionMessageService.returnErrorMessage(ex));
        }
    }
}
