package com.epam.brest.course2015.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

/**
 * Created by antonsavitsky on 20.01.16.
 */
public class JodaObjectMapper extends ObjectMapper {
    public JodaObjectMapper() {
        super();
        registerModule(new JodaModule());
    }
}
