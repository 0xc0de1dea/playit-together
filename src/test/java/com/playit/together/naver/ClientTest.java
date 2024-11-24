package com.playit.together.naver;

import com.playit.together.naver.dto.ImageSearchRequestDto;
import com.playit.together.naver.dto.ImageSearchResponseDto;
import com.playit.together.naver.dto.LocalSearchRequestDto;
import com.playit.together.naver.dto.LocalSearchResponseDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClientTest {
    @Autowired
    private Client client;

    @Test
    @DisplayName("지역 검색 테스트")
    public void localSearchTest(){
        LocalSearchRequestDto localSearchRequestDto = new LocalSearchRequestDto();
        localSearchRequestDto.setQuery("강남구 백화점");

        LocalSearchResponseDto localSearchResponseDto = client.localSearch(localSearchRequestDto);
        System.out.println(localSearchResponseDto);

        ImageSearchRequestDto imageSearchRequestDto = new ImageSearchRequestDto();
        imageSearchRequestDto.setQuery("현대<b>백화점</b> 무역센터점");

        ImageSearchResponseDto imageSearchResponseDto = client.imageSearch(imageSearchRequestDto);
        System.out.println(imageSearchResponseDto);
    }
}
