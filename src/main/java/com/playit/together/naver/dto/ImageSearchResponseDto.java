package com.playit.together.naver.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ImageSearchResponseDto {
    private String lastBuildDate;

    private int total;

    private int start;

    private int display;

    private List<ImageSearchItem> items;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    public static class ImageSearchItem {
        private String title;

        private String link;

        private String thumbnail;

        private String sizeheight;

        private String sizewidth;
    }
}
