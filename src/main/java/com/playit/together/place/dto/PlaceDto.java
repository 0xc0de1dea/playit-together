package com.playit.together.place.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PlaceDto {
    private String category;

    private String name;

    private String address;

    private String homePageLink;

    private String imageLink;

    private Double rating;

    private Integer reviewCount;

    private LocalDateTime createdAt;
}
