package com.epam.brest.course2015.test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Component;
import java.io.IOException;

/**
 * Created by antonsavitsky on 20.01.16.
 */
@Component
public class JsonDateSerializer extends JsonSerializer<LocalDate> {
    @Override
    public void serialize(LocalDate date, JsonGenerator gen, SerializerProvider provider)
            throws IOException{
        String formattedDate =date.toString("dd/MM/yyyy");
        gen.writeString(formattedDate);
    }
}
