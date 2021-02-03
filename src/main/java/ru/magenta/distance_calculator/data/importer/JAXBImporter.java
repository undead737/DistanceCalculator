package ru.magenta.distance_calculator.data.importer;

import org.xml.sax.SAXParseException;
import ru.magenta.distance_calculator.data.DataManipulatingException;
import ru.magenta.distance_calculator.data.models.RootElement;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Анмаршалинг из XML
 * @param <T> Объект, который представляет из себя корневой элемент XML, включает в себя коллекцию вложенных элементов
 * @param <E> Объект, который представляет вложенные повторяющиеся элементы (данные приложения)
 */
public class JAXBImporter<T extends RootElement<E>, E> implements Importer<E>{
    private final Class<T> typeRoot;

    public JAXBImporter (Class<T> typeRoot){
        this.typeRoot = typeRoot;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<E> importData(InputStream file) throws DataManipulatingException {
        try {
            T root = (T) JAXBContext.newInstance(typeRoot).createUnmarshaller().unmarshal(file);
            return root.getData();
        } catch (JAXBException e){
            throw new DataManipulatingException("XML read error: " + e.getMessage());
        }
    }
}
