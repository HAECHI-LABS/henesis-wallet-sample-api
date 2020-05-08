package io.haechi.sample.server.domain.value;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionReceipt {
    private String transactionId;
}
