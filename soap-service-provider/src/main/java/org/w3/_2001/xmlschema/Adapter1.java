//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.06 at 10:28:17 PM MSK 
//


package org.w3._2001.xmlschema;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import org.joda.time.LocalDate;

public class Adapter1
    extends XmlAdapter<String, LocalDate>
{


    public LocalDate unmarshal(String value) {
        return (com.epam.brest.course2015.soap.DateAdapter.parseDate(value));
    }

    public String marshal(LocalDate value) {
        return (com.epam.brest.course2015.soap.DateAdapter.printDate(value));
    }

}
