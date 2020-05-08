package io.haechi.sample.server.application.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TransactionResponseDTO {
    private String id;
    private String from;
    private String to;
    private String hash;
    private String status;
    private String error;
}
