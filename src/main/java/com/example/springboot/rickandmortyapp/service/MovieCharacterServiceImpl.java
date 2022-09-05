package com.example.springboot.rickandmortyapp.service;

import com.example.springboot.rickandmortyapp.dto.external.ApiCharacterDto;
import com.example.springboot.rickandmortyapp.dto.external.ApiResponseDto;
import com.example.springboot.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import com.example.springboot.rickandmortyapp.model.MovieCharacter;
import com.example.springboot.rickandmortyapp.repository.MovieCharacterRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private final HttpClient httpClient;
    private final MovieCharacterRepository movieCharacterRepository;
    
    private final MovieCharacterMapper mapper;

    public MovieCharacterServiceImpl(HttpClient httpClient,
                                     MovieCharacterRepository movieCharacterRepository, 
                                     MovieCharacterMapper mapper) {
        this.httpClient = httpClient;
        this.movieCharacterRepository = movieCharacterRepository;
        this.mapper = mapper;
    }

    @Scheduled(cron = "* 0 8 * * ?")
    @PostConstruct
    @Override
    public void syncExternalCharacters() {
        System.out.println("Now = " + LocalDateTime.now());
        ApiResponseDto apiResponseDto =
                httpClient.get("https://rickandmortyapi.com/api/character", ApiResponseDto.class);

        saveDtosToDb(apiResponseDto);

        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient.get(apiResponseDto.getInfo().getNext(),
                    ApiResponseDto.class);
            saveDtosToDb(apiResponseDto);
        }
    }

    @Override
    public MovieCharacter getRandomCharacter() {
        long count = movieCharacterRepository.count();
        long randomId = (long) (Math.random() * count);
        return movieCharacterRepository
                .findById(randomId)
                .orElseThrow(() ->
                        new RuntimeException("Can't find Character by id: " + randomId));
    }

    @Override
    public List<MovieCharacter> findAllByNameContains(String namePart) {
        return movieCharacterRepository.findAllByNameContains(namePart);
    }

    public List<MovieCharacter> saveDtosToDb(ApiResponseDto apiResponseDto) {
        Map<Long, ApiCharacterDto> externalDtos = Arrays.stream(apiResponseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));
        Set<Long> externalIds = externalDtos.keySet();

        List<MovieCharacter> existingCharacters =
                movieCharacterRepository.findAllByExternalIdIn(externalIds);

        Map<Long, MovieCharacter> existingCharactersWithIds = existingCharacters.stream()
                .collect(Collectors.toMap(MovieCharacter::getExternalId, Function.identity()));

        Set<Long> existingIds = existingCharactersWithIds.keySet();

        externalIds.removeAll(existingIds);

        List<MovieCharacter> charactersToSave =
                externalIds.stream()
                        .map(i -> mapper.parseApiCharacterToDto(externalDtos.get(i)))
                        .collect(Collectors.toList());

        movieCharacterRepository.saveAll(charactersToSave);
        return charactersToSave;
    }
}
