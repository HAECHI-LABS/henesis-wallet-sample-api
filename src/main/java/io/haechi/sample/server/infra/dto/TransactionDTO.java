package io.haechi.sample.server.infra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionDTO {
    private String id;
    private String blockchain;
    private String sender;
    private String hash;
    private String error;
    private String status;
    @JsonProperty("key_id")
    private String keyId;
    @JsonProperty("signed_multi_sig_payload")
    private SignedMultiSigPayloadDTO signedMultiSigPayloadDTO;
    @JsonProperty("raw_transaction")
    private RawTransactionDTO rawTransactionDTO;
}
