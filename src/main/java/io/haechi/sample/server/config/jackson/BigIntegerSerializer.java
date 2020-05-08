package io.haechi.sample.server.config.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.haechi.sample.server.domain.Converter;

import java.io.IOException;
import java.math.BigInteger;

public class BigIntegerSerializer extends JsonSerializer<BigInteger> {
    @Override
    public void serialize(BigInteger value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(Converter.bigIntegerToHexString(value));
    }
}