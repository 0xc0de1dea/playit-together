package com.playit.together.place.sevice;

import com.playit.together.place.dto.PlaceDto;
import com.playit.together.place.service.PlaceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PlaceServiceTest {
    @Autowired
    private PlaceService placeService;

    @Test
    @DisplayName("플레이스 서치 테스트")
    public void searchTest(){
        PlaceDto placeDto = placeService.search("강남구 백화점");

        Assertions.assertNotNull(placeDto);
    }
}
