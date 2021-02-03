package ru.magenta.distance_calculator.db.dao.concat;

import ru.magenta.distance_calculator.db.models.ConcatData;

import java.util.List;

public interface ConcatDao {
    List<ConcatData> getAll() throws Exception;
}
