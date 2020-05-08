package io.haechi.sample.server.web.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorBody {
    private String message;
    private int code;
}
