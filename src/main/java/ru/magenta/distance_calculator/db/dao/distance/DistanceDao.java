package ru.magenta.distance_calculator.db.dao.distance;

import ru.magenta.distance_calculator.db.dao.ProjectDao;
import ru.magenta.distance_calculator.db.models.Distance;

import java.util.List;
import java.util.Optional;

public interface DistanceDao extends ProjectDao {
    List<Distance> getAllDistances() throws Exception;
    Optional<Distance> getDistanceById(int id) throws Exception;

    void insertDistance(Distance distance) throws Exception;
    void batchInsertDistances(List<Distance> distances) throws Exception;
}
