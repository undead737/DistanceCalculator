package ru.magenta.distance_calculator.db.dao;

/**
 * При импорте новых данных, все таблицы будут чиститься
 */
public interface ProjectDao {
    void clearTable() throws Exception;
}
