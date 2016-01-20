package com.epam.brest.course2015.test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Created by antonsavitsky on 20.01.16.
 */
public class JsonDateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException{
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/yyyy");
        return fmt.parseLocalDate(jp.getValueAsString());
    }
}
