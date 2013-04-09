/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.xalan.processor.TransformerFactoryImpl;
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

    public static ByteArrayOutputStream printPDF(String xmlPath, String xslPath)
            throws TransformerConfigurationException, FileNotFoundException, TransformerException, FOPException {
        StreamSource xmlFile = new StreamSource(xmlPath);
        StreamSource xstlFile = new StreamSource(xslPath);
        FopFactory fopFactory = FopFactory.newInstance();
        FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, outStream);
        Result res = new SAXResult(fop.getDefaultHandler());
        Transformer xslfoTransformer = getTransformer(xstlFile);
        xslfoTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        xslfoTransformer.transform(xmlFile, res);
        return outStream;
    }

    private static Transformer getTransformer(StreamSource streamSource) throws TransformerConfigurationException {
        // setup the xslt transformer
        TransformerFactoryImpl impl =
                new TransformerFactoryImpl();
        return impl.newTransformer(streamSource);
    }

    public static String transformOrderXML(String xmlFilePath,String xslFilePath,String userEmail) {
        String formattedOutput = "";
        try {
            Transformer transformer =getTransformer(new StreamSource(xslFilePath));
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setParameter("user_email", userEmail);
            StreamSource xmlSource = new StreamSource(xmlFilePath);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            transformer.transform(xmlSource, new StreamResult(baos));

            formattedOutput = baos.toString("UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return formattedOutput;
    }
}
