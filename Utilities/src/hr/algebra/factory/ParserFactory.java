package hr.algebra.factory;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.InputStream;

/**
 * @author dnlbe
 */
public class ParserFactory {

    public static XMLEventReader createStaxParser(InputStream stream) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newInstance();
        return factory.createXMLEventReader(stream);
    }
}
