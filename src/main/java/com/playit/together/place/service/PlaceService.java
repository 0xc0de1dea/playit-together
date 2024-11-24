package com.playit.together.place.service;

import com.playit.together.naver.Client;
import com.playit.together.naver.dto.ImageSearchRequestDto;
import com.playit.together.naver.dto.ImageSearchResponseDto;
import com.playit.together.naver.dto.LocalSearchRequestDto;
import com.playit.together.naver.dto.LocalSearchResponseDto;
import com.playit.together.place.dto.PlaceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final Client client;

    public PlaceDto search(String query){
        LocalSearchRequestDto localSearchRequestDto = new LocalSearchRequestDto();
        localSearchRequestDto.setQuery(query);

        LocalSearchResponseDto localSearchResponseDto = client.localSearch(localSearchRequestDto);

        if (localSearchResponseDto.getTotal() > 0){
            LocalSearchResponseDto.LocalSearchItem localItem = localSearchResponseDto.getItems().stream().findFirst().get();

            String imageQuery = localItem.getTitle().replaceAll("<[^>]*>", "");
            ImageSearchRequestDto imageSearchRequestDto = new ImageSearchRequestDto();
            imageSearchRequestDto.setQuery(imageQuery);

            ImageSearchResponseDto imageSearchResponseDto = client.imageSearch(imageSearchRequestDto);

            if (imageSearchResponseDto.getTotal() > 0){
                ImageSearchResponseDto.ImageSearchItem imageItem = imageSearchResponseDto.getItems().stream().findFirst().get();

                PlaceDto placeDto = PlaceDto.builder()
                        .category(localItem.getCategory())
                        .name(localItem.getTitle())
                        .address(localItem.getAddress())
                        .homePageLink(localItem.getLink())
                        .imageLink(imageItem.getLink())
                        .createdAt(LocalDateTime.now())
                        .build();

                return placeDto;
            }
        }

        return new PlaceDto();
    }
}
