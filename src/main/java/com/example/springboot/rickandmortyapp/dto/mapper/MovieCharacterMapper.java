package com.example.springboot.rickandmortyapp.dto.mapper;

import com.example.springboot.rickandmortyapp.dto.CharacterResponseDto;
import com.example.springboot.rickandmortyapp.dto.external.ApiCharacterDto;
import com.example.springboot.rickandmortyapp.model.Gender;
import com.example.springboot.rickandmortyapp.model.MovieCharacter;
import com.example.springboot.rickandmortyapp.model.Status;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterMapper {
    public MovieCharacter parseApiCharacterToDto(ApiCharacterDto dto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setName(dto.getName());
        movieCharacter.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        movieCharacter.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
        movieCharacter.setExternalId(dto.getId());
        movieCharacter.setSpecies(dto.getSpecies());
        movieCharacter.setType(dto.getType());
        return movieCharacter;
    }

    public CharacterResponseDto characterToDto(MovieCharacter movieCharacter) {
        CharacterResponseDto characterResponseDto = new CharacterResponseDto();
        characterResponseDto.setId(movieCharacter.getId());
        characterResponseDto.setGender(movieCharacter.getGender());
        characterResponseDto.setName(movieCharacter.getName());
        characterResponseDto.setExternalId(movieCharacter.getExternalId());
        characterResponseDto.setStatus(movieCharacter.getStatus());
        characterResponseDto.setType(movieCharacter.getType());
        characterResponseDto.setSpecies(movieCharacter.getSpecies());
        return characterResponseDto;
    }
}
