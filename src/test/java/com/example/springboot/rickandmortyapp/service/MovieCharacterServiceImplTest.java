package com.example.springboot.rickandmortyapp.service;

import com.example.springboot.rickandmortyapp.dto.external.ApiCharacterDto;
import com.example.springboot.rickandmortyapp.dto.external.ApiInfoDto;
import com.example.springboot.rickandmortyapp.dto.external.ApiResponseDto;
import com.example.springboot.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import com.example.springboot.rickandmortyapp.model.Gender;
import com.example.springboot.rickandmortyapp.model.MovieCharacter;
import com.example.springboot.rickandmortyapp.model.Status;
import com.example.springboot.rickandmortyapp.repository.MovieCharacterRepository;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MovieCharacterServiceImplTest {
    @InjectMocks
    private MovieCharacterServiceImpl movieCharacterService;

    @Mock
    private MovieCharacterRepository movieCharacterRepository;
    @Mock
    private MovieCharacterMapper mapper;
    private MovieCharacter bob;
    private ApiCharacterDto apiCharacterDto;
    private ApiResponseDto apiResponseDto;

    @BeforeEach
    void setUp() {
        bob = new MovieCharacter();
        bob.setId(1L);
        bob.setExternalId(1L);
        bob.setName("Bob Alice");
        bob.setStatus(Status.ALIVE);
        bob.setGender(Gender.MALE);
        bob.setType("Human with baby legs");
        bob.setSpecies("Humanoid");

        ApiInfoDto apiInfoDto = new ApiInfoDto();

        apiCharacterDto = new ApiCharacterDto();
        apiCharacterDto.setId(1L);
        apiCharacterDto.setName("Bob Alice");
        apiCharacterDto.setStatus("Alive");
        apiCharacterDto.setGender("Male");
        apiCharacterDto.setType("Human with baby legs");
        apiCharacterDto.setSpecies("Humanoid");

        apiResponseDto = new ApiResponseDto();
        apiResponseDto.setInfo(apiInfoDto);
        apiResponseDto.setResults(new ApiCharacterDto[]{apiCharacterDto});
    }

    @Test
    void properSavesDtosToDb_Ok() {
        Mockito.when(movieCharacterRepository.findAllByExternalIdIn(any())).thenReturn(Collections.emptyList());
        Mockito.when((mapper.parseApiCharacterToDto(apiCharacterDto))).thenReturn(bob);

        List<MovieCharacter> actual = movieCharacterService.saveDtosToDb(apiResponseDto);
        Assertions.assertEquals(1, actual.size());
        Assertions.assertEquals("Bob Alice", actual.get(0).getName());
        Assertions.assertEquals("Humanoid", actual.get(0).getSpecies());
    }

}