package io.haechi.sample.server.domain.value;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Asset {
    private String name;
    private Token token;
}
