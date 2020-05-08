package io.haechi.sample.server.infra.utils;

import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static java.lang.String.format;

public final class Convert {
    public static BigInteger fromDate(LocalDateTime date) {
        return BigInteger.valueOf(date.toInstant(ZoneOffset.UTC).getEpochSecond());
    }

    public static LocalDateTime toDate(BigInteger i) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(i.longValue()), ZoneOffset.UTC);
    }

    public static String bytesToHexString(byte[] bytes) {
        return Numeric.toHexString(bytes);
    }

    public static byte[] hexStringToBytes32(String string) {
        byte[] byteValue = Numeric.hexStringToByteArray(string);
        if (byteValue.length > 32) {
            throw new IllegalArgumentException(
                    format("converted byte length must be smaller than 32: %s", string));
        }
        byte[] byteValueLen32 = new byte[32];
        System.arraycopy(byteValue, 0, byteValueLen32, 0, byteValue.length);
        return byteValueLen32;
    }
}
