package io.haechi.sample.server.domain.value;

import io.haechi.sample.server.domain.AssertionConcern;
import io.haechi.sample.server.domain.Converter;
import lombok.*;
import org.web3j.utils.Strings;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionHash extends AssertionConcern {
    private String value;

    public TransactionHash(String value) {
        this.assertArgumentNotNull(value, "value must not be null");
        this.assertArgumentLength(value, 66, "length of address string is out of range: " + value.trim().length());

        String without0x = Converter.remove0x(value.toLowerCase());

        this.assertArgumentEquals(
                without0x.substring(0, without0x.length() - 64),
                Strings.zeros(without0x.length() - 64),
                "value must be padded with 0"
        );
        this.value = "0x" + without0x.substring(without0x.length() - 64);
    }
}
