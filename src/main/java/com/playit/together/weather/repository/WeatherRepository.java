package com.playit.together.weather.repository;

import com.playit.together.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Long> {
    List<Weather> findAllByRegionAndRecordedAt(String region, LocalDate recordedAt);
}
