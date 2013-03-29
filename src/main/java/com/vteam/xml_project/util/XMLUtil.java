/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vteam.xml_project.util;

import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
}
