package io.haechi.sample.server.domain.value;

import io.haechi.sample.server.domain.AssertionConcern;
import io.haechi.sample.server.domain.Converter;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.web3j.utils.Strings;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Address extends AssertionConcern {
    private String value;

    public Address(String value) {
        this.assertArgumentNotNull(value, "value must not be null");
        this.assertArgumentLength(value, 42, 66, "length of address string is out of range: " + value.trim().length());

        String without0x = Converter.remove0x(value.toLowerCase());

        this.assertArgumentEquals(
                without0x.substring(0, without0x.length() - 40),
                Strings.zeros(without0x.length() - 40),
                "value must be padded with 0"
        );
        this.value = "0x" + without0x.substring(without0x.length() - 40);
    }

    public byte[] toBytes() {
        return Converter.hexStringToByteArray(this.value.toLowerCase().substring(2));
    }
}
