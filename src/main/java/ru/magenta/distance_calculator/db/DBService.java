package ru.magenta.distance_calculator.db;

import ru.magenta.distance_calculator.db.connection.MySqlConnection;
import ru.magenta.distance_calculator.db.dao.city.CityDao;
import ru.magenta.distance_calculator.db.dao.city.CityDaoImpl;
import ru.magenta.distance_calculator.db.dao.concat.ConcatDao;
import ru.magenta.distance_calculator.db.dao.concat.ConcatDaoImpl;
import ru.magenta.distance_calculator.db.dao.distance.DistanceDao;
import ru.magenta.distance_calculator.db.dao.distance.DistanceDaoImpl;
import ru.magenta.distance_calculator.db.models.City;
import ru.magenta.distance_calculator.db.models.ConcatData;
import ru.magenta.distance_calculator.db.models.Distance;

import java.sql.Connection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class DBService {

    private final static CityDao CITY_DAO;
    private final static DistanceDao DISTANCE_DAO;
    private final static ConcatDao CONCAT_DAO;

    static {
        Connection connection = new MySqlConnection().getConnection();
        CITY_DAO = new CityDaoImpl(connection);
        CONCAT_DAO = new ConcatDaoImpl(connection);
        DISTANCE_DAO = new DistanceDaoImpl(connection);
    }

    public List<City> getAllCities() throws Exception{
        return CITY_DAO.getAllCities();
    }

    public Optional<City> getCityById(String stringId) throws Exception{
        return CITY_DAO.getCityById(Integer.parseInt(stringId));
    }

    public void saveCity(City city) throws Exception{
        //Если город есть, обновляем. Если нет, то инсерт
        if (getCityById(String.valueOf(city.getId())).isPresent()) {
            CITY_DAO.updateCity(city);
        } else {
            CITY_DAO.insertCity(city);
        }
    }

    public List<ConcatData> getConcatData() throws Exception{
        return CONCAT_DAO.getAll();
    }

    /**
     * При экспорте данных:
     *      Чистим таблицу с городами
     *      Чистим таблицу с расстояниями между городов
     *      Множественной вставкой инсертим города 'ИЗ'
     *      Множественной вставкой инсертим города 'В'
     *      Множественной вставкой инсертим расстояния
     * @throws Exception SQL исключения
     */
    public void saveConcatData(List<ConcatData> data) throws Exception{
        //Тк. в xml города могут присутствовать несколько раз (если несколько связей), необходимо обеспечить уникальность
        Set<City> tempSet = new HashSet<>();
        CITY_DAO.clearTable();
        DISTANCE_DAO.clearTable();
        tempSet.addAll(data.stream().map(ConcatData::getCityFrom).collect(Collectors.toSet()));
        tempSet.addAll(data.stream().map(ConcatData::getCityTo).collect(Collectors.toSet()));
        CITY_DAO.batchInsertCities(tempSet);
        DISTANCE_DAO.batchInsertDistances(data.stream().map(x -> new Distance(x.getCityFrom().getId(), x.getCityTo().getId(), x.getDistance())).collect(Collectors.toList()));
    }
}
