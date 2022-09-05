package com.example.springboot.rickandmortyapp.dto.mapper;

import com.example.springboot.rickandmortyapp.dto.CharacterResponseDto;
import com.example.springboot.rickandmortyapp.dto.external.ApiCharacterDto;
import com.example.springboot.rickandmortyapp.model.Gender;
import com.example.springboot.rickandmortyapp.model.MovieCharacter;
import com.example.springboot.rickandmortyapp.model.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieCharacterMapperTest {
    @InjectMocks
    private MovieCharacterMapper mapper;
    private MovieCharacter bob;
    private ApiCharacterDto apiCharacterDto;
    private CharacterResponseDto bobResponseDto;

    @BeforeEach
    void setUp() {
        apiCharacterDto = new ApiCharacterDto();
        apiCharacterDto.setId(1L);
        apiCharacterDto.setName("Bob Alice");
        apiCharacterDto.setStatus("Alive");
        apiCharacterDto.setGender("Male");
        apiCharacterDto.setType("Human with baby legs");
        apiCharacterDto.setSpecies("Humanoid");

        bob = new MovieCharacter();
        bob.setId(1L);
        bob.setExternalId(1L);
        bob.setName("Bob Alice");
        bob.setStatus(Status.ALIVE);
        bob.setGender(Gender.MALE);
        bob.setType("Human with baby legs");
        bob.setSpecies("Humanoid");

        bobResponseDto = new CharacterResponseDto();
        bobResponseDto.setId(1L);
        bobResponseDto.setExternalId(1L);
        bobResponseDto.setName("Bob Alice");
        bobResponseDto.setStatus(Status.ALIVE);
        bobResponseDto.setGender(Gender.MALE);
        bobResponseDto.setType("Human with baby legs");
        bobResponseDto.setSpecies("Humanoid");
    }

    @Test
    void parseExternalCharacterToDto_Ok() {
        MovieCharacter movieCharacter = mapper.parseApiCharacterToDto(apiCharacterDto);
        Assertions.assertEquals(1L, movieCharacter.getExternalId());
        Assertions.assertEquals("Bob Alice", movieCharacter.getName());
    }

    @Test
    void getCharacterResponseDto_Ok() {
        CharacterResponseDto actualCharacterResponse = mapper.characterToDto(bob);
        Assertions.assertEquals(1L, actualCharacterResponse.getId());
        Assertions.assertEquals(bobResponseDto.getName(), actualCharacterResponse.getName());
        Assertions.assertEquals(bobResponseDto.getStatus(), actualCharacterResponse.getStatus());
    }
}