package io.haechi.sample.server.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pagination<T> {
    private PaginationMeta pagination;
    private List<T> results;

    @Builder
    public Pagination(String nextUrl, String previousUrl, Long totalCount, List<T> results) {
        this.pagination = new PaginationMeta(nextUrl, previousUrl, totalCount);
        this.results = results;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    public class PaginationMeta {
        @JsonProperty("next_url")
        private String nextUrl;
        @JsonProperty("previous_url")
        private String previousUrl;
        @JsonProperty("total_count")
        private Long totalCount;
    }
}
