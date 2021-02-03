package ru.magenta.distance_calculator.db.dao.city;

import ru.magenta.distance_calculator.db.dao.ProjectDao;
import ru.magenta.distance_calculator.db.models.City;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CityDao extends ProjectDao {
    List<City> getAllCities() throws Exception;
    Optional<City> getCityById(int id) throws Exception;
    List<City> getCitiesByName(String name) throws Exception;
    List<City> getCitiesByPosition(String latitude, String longitude) throws Exception;

    void insertCity(City city) throws Exception;
    void batchInsertCities(Set<City> cities) throws Exception;
    void updateCity(City city) throws Exception;
    void deleteCity(City city) throws Exception;
}
