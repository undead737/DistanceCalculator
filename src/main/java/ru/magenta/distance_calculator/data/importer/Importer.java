package ru.magenta.distance_calculator.data.importer;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Импорт данных
 * @param <E> Объект, который представляет загружаемые данные
 */
public interface Importer<E> {
    List<E> importData(InputStream file) throws Exception;
}
