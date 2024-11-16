package com.decerto.typer.integration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Service
public class FootballPrediction {

    private static final String API_URL = "https://football-prediction-api.p.rapidapi.com/api/v2/predictions";

    public Optional<FootballPredictionItem> getPredictions(String team) {
        String tomorrow = LocalDate.of(2024, 11, 16).format(DateTimeFormatter.ISO_LOCAL_DATE);

        WebClient client = WebClient.builder()
                .baseUrl(API_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-RapidAPI-Key", "44da146ee5msh4d0c0a3756aeb4ap12176ajsnb8e8e8b50462")
                .defaultCookie("cookieKey", "cookieValue")
                .defaultUriVariables(Map.of("federation", "UEFA", "market", "classic", "iso_date", tomorrow))
                .build();

        FootballPredictionData data = client.method(HttpMethod.GET).retrieve().bodyToMono(FootballPredictionData.class).block();
        return Arrays.stream(data.getData())
                .filter(item -> item.getHome_team().contains(team))
                .findFirst();
    }

}
