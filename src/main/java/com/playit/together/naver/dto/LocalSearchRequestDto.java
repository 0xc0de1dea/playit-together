package com.playit.together.naver.dto;

import lombok.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LocalSearchRequestDto {
    @Builder.Default
    private String query = "";

    @Builder.Default
    private int start = 1;

    @Builder.Default
    private int display = 5;

    @Builder.Default
    private String sort = "comment"; // random(유사도순), comment(카페/블로그 리뷰 개수 순)

    public MultiValueMap<String, String> toMultiValueMap(){
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("query", query);
        map.add("start", String.valueOf(start));
        map.add("display", String.valueOf(display));
        map.add("sort", sort);

        return map;
    }
}
