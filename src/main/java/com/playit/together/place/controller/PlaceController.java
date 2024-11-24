package com.playit.together.place.controller;

import com.playit.together.place.dto.PlaceDto;
import com.playit.together.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PlaceController {
    private final PlaceService placeService;

    @GetMapping("/place")
    public ResponseEntity<PlaceDto> search(@RequestParam String query){
        return ResponseEntity.ok(placeService.search(query));
    }
}
