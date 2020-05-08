package io.haechi.sample.server.infra.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignedMultiSigPayloadDTO {
    private String signature;
    @JsonProperty("payload")
    private MultiSigPayloadDTO payloadDTO;
}
