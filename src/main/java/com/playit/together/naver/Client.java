package com.playit.together.naver;

import com.playit.together.naver.dto.ImageSearchRequestDto;
import com.playit.together.naver.dto.ImageSearchResponseDto;
import com.playit.together.naver.dto.LocalSearchRequestDto;
import com.playit.together.naver.dto.LocalSearchResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Component
public class Client {
    @Value("${naver.client.id}")
    private String naverClientId;

    @Value("${naver.client.secret}")
    private String naverSecret;

    @Value("${naver.url.search.local}")
    private String naverLocalSearchUrl;

    @Value("${naver.url.search.image}")
    private String naverImageSearchUrl;

    public LocalSearchResponseDto localSearch(LocalSearchRequestDto localSearchRequestDto) {
        String query = localSearchRequestDto.getQuery();
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(query);
        String encode = StandardCharsets.UTF_8.decode(buffer).toString();

        localSearchRequestDto.setQuery(encode);

        URI uri = UriComponentsBuilder
                .fromUriString(naverLocalSearchUrl)
                .queryParams(localSearchRequestDto.toMultiValueMap())
                .build()
                .encode()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverClientId);
        headers.set("X-Naver-Client-Secret", naverSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ParameterizedTypeReference<LocalSearchResponseDto> responseType = new ParameterizedTypeReference<>() {};

        ResponseEntity<LocalSearchResponseDto> responseEntity = new RestTemplate()
                .exchange(
                        uri,
                        HttpMethod.GET,
                        httpEntity,
                        responseType
                );

        return responseEntity.getBody();
    }

    public ImageSearchResponseDto imageSearch(ImageSearchRequestDto imageSearchRequestDto) {
        String query = imageSearchRequestDto.getQuery();
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(query);
        String encode = StandardCharsets.UTF_8.decode(buffer).toString();

        imageSearchRequestDto.setQuery(encode);

        URI uri = UriComponentsBuilder
                .fromUriString(naverImageSearchUrl)
                .queryParams(imageSearchRequestDto.toMultiValueMap())
                .build()
                .encode()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", naverClientId);
        headers.set("X-Naver-Client-Secret", naverSecret);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
        ParameterizedTypeReference<ImageSearchResponseDto> responseType = new ParameterizedTypeReference<>() {
        };


        ResponseEntity<ImageSearchResponseDto> responseEntity = new RestTemplate()
                .exchange(
                        uri,
                        HttpMethod.GET,
                        httpEntity,
                        responseType
                );

        return responseEntity.getBody();
    }
}
