/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.util;

import java.io.File;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author Lenovo
 */
public class XMLUtil {

    public static File Marshall(Object object, String filePath) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(object.getClass());
        Marshaller u = jc.createMarshaller();
        u.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        u.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        File f = new File(filePath);
        u.marshal(object, f);
        return f;
    }

    public static <T> T UnMarshall(Class<T> objectClass, String filePath) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(objectClass);
        Unmarshaller u = jc.createUnmarshaller();
        File f = new File(filePath);
        return objectClass.cast(u.unmarshal(f));
    }
    public static <T> T UnMarshall(Class<T> objectClass, Node node) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(objectClass);
        Unmarshaller u = jc.createUnmarshaller();
        return objectClass.cast(u.unmarshal(node));
    }
    public static <T> T UnMarshall(Class<T> objectClass, XMLStreamReader reader) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(objectClass);
        Unmarshaller u = jc.createUnmarshaller();
        return objectClass.cast(u.unmarshal(reader));
    }

    public static Document parseDOM(String filePath) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(filePath));
        return doc;
    }

    public static void writeXML(Node node, String filePath) throws TransformerConfigurationException, TransformerException {
        TransformerFactory tff = TransformerFactory.newInstance();
        Transformer trans = tff.newTransformer();
        Source src = new DOMSource(node);
        File file = new File(filePath);
        Result result = new StreamResult(file);
        trans.transform(src, result);
    }
}
