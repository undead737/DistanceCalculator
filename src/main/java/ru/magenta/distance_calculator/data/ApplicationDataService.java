package ru.magenta.distance_calculator.data;

import ru.magenta.distance_calculator.db.DBService;
import ru.magenta.distance_calculator.math.MathService;

import java.io.*;

/**
 * Работа с данными
 */
public class ApplicationDataService {

    private static final FileService FILE_SERVICE;
    private static final DBService DB_SERVICE;
    private static final MathService MATH_SERVICE;

    static {
        FILE_SERVICE = new FileService();
        DB_SERVICE = new DBService();
        MATH_SERVICE = new MathService();
    }

    /**
     * Експорт данных из базы в XML
     */
    public ByteArrayInputStream exportData() throws Exception{
        return new ByteArrayInputStream(FILE_SERVICE.exportData(TypeFile.XML, DB_SERVICE.getConcatData()).toByteArray());
    }

    /**
     * Импорт данных из файла в базу
     * @param file файл
     */
    public void importData(InputStream file) throws Exception{
        DB_SERVICE.saveConcatData(FILE_SERVICE.importData(TypeFile.XML, file));
        MATH_SERVICE.updateAlgorithm();
    }
}
