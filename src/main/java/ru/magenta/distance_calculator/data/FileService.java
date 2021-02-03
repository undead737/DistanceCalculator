package ru.magenta.distance_calculator.data;

import ru.magenta.distance_calculator.data.exporter.JAXBExporter;
import ru.magenta.distance_calculator.data.importer.JAXBImporter;
import ru.magenta.distance_calculator.db.models.ConcatData;
import ru.magenta.distance_calculator.data.models.RootElementImpl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Работа с файлами
 */
public class FileService {

    /**
     * Експорт данных в файл
     * @param type Тип файла
     * @param path путь до файла
     * @param data выгружаемые данные
     */
    @SuppressWarnings("all")
    public ByteArrayOutputStream exportData(TypeFile type, List<ConcatData> data) throws Exception {
        //Можно добавлять другие реализации Exporter в зависимости от TypeFile
        switch (type) {
            case XML:
            default:
                JAXBExporter<RootElementImpl, ConcatData> exporter = new JAXBExporter<>(RootElementImpl.class);
                return exporter.exportData(data);
        }
    }

    /**
     * Формирует массив данных из файла
     * @param type Тип файла
     * @param path путь до файла
     * @return данные из файла
     */
    @SuppressWarnings("all")
    public List<ConcatData> importData(TypeFile type, InputStream file) throws Exception {
        if (file == null) throw new DataManipulatingException("file can't be null");
        //Можно добавлять другие реализации Importer в зависимости от TypeFile
        switch (type) {
            case XML:
            default:
                JAXBImporter<RootElementImpl, ConcatData> importer = new JAXBImporter<>(RootElementImpl.class);
                return importer.importData(file);
        }
    }
}
