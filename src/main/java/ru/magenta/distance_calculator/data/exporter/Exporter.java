package ru.magenta.distance_calculator.data.exporter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

/**
 * Экспорт данных
 * @param <E> Объект, который представляет выгружаемые данные
 */
public interface Exporter<E> {
     ByteArrayOutputStream exportData(List<E> data) throws Exception;
}