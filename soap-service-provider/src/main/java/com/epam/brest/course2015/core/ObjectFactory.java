//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.06 at 10:28:17 PM MSK 
//


package com.epam.brest.course2015.core;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import org.joda.time.LocalDate;
import org.w3._2001.xmlschema.Adapter1;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.epam.brest.course2015.core package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ProducerId_QNAME = new QName("http://epam.com/brest/course2015/core", "producerId");
    private final static QName _CarId_QNAME = new QName("http://epam.com/brest/course2015/core", "carId");
    private final static QName _DateOfCreation_QNAME = new QName("http://epam.com/brest/course2015/core", "dateOfCreation");
    private final static QName _Total_QNAME = new QName("http://epam.com/brest/course2015/core", "total");
    private final static QName _Country_QNAME = new QName("http://epam.com/brest/course2015/core", "country");
    private final static QName _ProducerName_QNAME = new QName("http://epam.com/brest/course2015/core", "producerName");
    private final static QName _CarName_QNAME = new QName("http://epam.com/brest/course2015/core", "carName");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.epam.brest.course2015.core
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Cars }
     * 
     */
    public Cars createCars() {
        return new Cars();
    }

    /**
     * Create an instance of {@link Car }
     * 
     */
    public Car createCar() {
        return new Car();
    }

    /**
     * Create an instance of {@link Producer }
     * 
     */
    public Producer createProducer() {
        return new Producer();
    }

    /**
     * Create an instance of {@link Cardto }
     * 
     */
    public Cardto createCardto() {
        return new Cardto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://epam.com/brest/course2015/core", name = "producerId")
    public JAXBElement<Integer> createProducerId(Integer value) {
        return new JAXBElement<Integer>(_ProducerId_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://epam.com/brest/course2015/core", name = "carId")
    public JAXBElement<Integer> createCarId(Integer value) {
        return new JAXBElement<Integer>(_CarId_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LocalDate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://epam.com/brest/course2015/core", name = "dateOfCreation")
    @XmlJavaTypeAdapter(Adapter1 .class)
    public JAXBElement<LocalDate> createDateOfCreation(LocalDate value) {
        return new JAXBElement<LocalDate>(_DateOfCreation_QNAME, LocalDate.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://epam.com/brest/course2015/core", name = "total")
    public JAXBElement<Integer> createTotal(Integer value) {
        return new JAXBElement<Integer>(_Total_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://epam.com/brest/course2015/core", name = "country")
    public JAXBElement<String> createCountry(String value) {
        return new JAXBElement<String>(_Country_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://epam.com/brest/course2015/core", name = "producerName")
    public JAXBElement<String> createProducerName(String value) {
        return new JAXBElement<String>(_ProducerName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://epam.com/brest/course2015/core", name = "carName")
    public JAXBElement<String> createCarName(String value) {
        return new JAXBElement<String>(_CarName_QNAME, String.class, null, value);
    }

}
