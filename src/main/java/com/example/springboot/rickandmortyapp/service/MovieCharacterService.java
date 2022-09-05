package com.example.springboot.rickandmortyapp.service;

import com.example.springboot.rickandmortyapp.dto.external.ApiResponseDto;
import com.example.springboot.rickandmortyapp.model.MovieCharacter;
import java.util.List;

public interface MovieCharacterService {
    void syncExternalCharacters();

    MovieCharacter getRandomCharacter();

    List<MovieCharacter> findAllByNameContains(String namePart);

    List<MovieCharacter> saveDtosToDb(ApiResponseDto apiResponseDto);
}
