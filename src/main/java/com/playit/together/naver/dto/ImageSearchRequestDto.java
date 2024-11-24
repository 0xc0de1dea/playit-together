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
public class ImageSearchRequestDto {
    @Builder.Default
    private String query = "";

    @Builder.Default
    private int start = 1;

    @Builder.Default
    private int display = 5;

    @Builder.Default
    private String sort = "sim"; // sim:유사도순, date:날짜순

    @Builder.Default
    private String filter = "all"; // all(전체), large(큰 사이즈), medium(중간 사이즈), small(작은 사이즈)

    public MultiValueMap<String, String> toMultiValueMap(){
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("query", query);
        map.add("display", String.valueOf(display));
        map.add("start", String.valueOf(start));
        map.add("sort", sort);
        map.add("filter", filter);

        return map;
    }
}
