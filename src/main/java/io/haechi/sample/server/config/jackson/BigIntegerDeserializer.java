package io.haechi.sample.server.config.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import io.haechi.sample.server.domain.Converter;

import java.io.IOException;
import java.math.BigInteger;

public class BigIntegerDeserializer extends JsonDeserializer<BigInteger> {
    @Override
    public BigInteger deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return Converter.hexStringToBigInteger(p.getText());
    }
}
