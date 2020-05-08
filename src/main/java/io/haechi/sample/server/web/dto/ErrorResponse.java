package io.haechi.sample.server.web.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
    private ErrorBody error;
}