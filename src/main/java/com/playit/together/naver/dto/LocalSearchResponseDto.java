package com.playit.together.naver.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class LocalSearchResponseDto {
    private String lastBuildDate;

    private int total;

    private int start;

    private int display;

    private List<LocalSearchItem> items;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class LocalSearchItem {
        private String title;

        private String link;

        private String category;

        private String description;

        private String telephone;

        private String address;

        private String roadAddress;

        private int mapx;

        private int mapy;
    }
}
