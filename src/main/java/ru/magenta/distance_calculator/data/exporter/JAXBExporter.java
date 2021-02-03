package ru.magenta.distance_calculator.data.exporter;

import ru.magenta.distance_calculator.data.DataManipulatingException;
import ru.magenta.distance_calculator.data.models.RootElement;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Маршалинг в XML
 * @param <T> Объект, который представляет из себя корневой элемент XML, включает в себя коллекцию вложенных элементов
 * @param <E> Объект, который представляет вложенные повторяющиеся элементы (данные приложения)
 */
public class JAXBExporter<T extends RootElement<E>, E> implements Exporter<E> {
    private final Class<T> typeRoot;

    public JAXBExporter (Class<T> typeRoot){
        this.typeRoot = typeRoot;
    }

    public ByteArrayOutputStream exportData(List<E> data)throws DataManipulatingException {
        try {
            RootElement<E> root = typeRoot.newInstance();
            root.setData(data);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            JAXBContext.newInstance(typeRoot).createMarshaller().marshal(root, stream);
            return stream;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new DataManipulatingException("Application logic exporter error");
        } catch (JAXBException e){
            throw new DataManipulatingException("XML write error: " + e.getMessage());
        }
    }
}