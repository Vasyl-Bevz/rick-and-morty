package com.example.springboot.rickandmortyapp.controller;

import com.example.springboot.rickandmortyapp.dto.CharacterResponseDto;
import com.example.springboot.rickandmortyapp.dto.mapper.MovieCharacterMapper;
import com.example.springboot.rickandmortyapp.model.MovieCharacter;
import com.example.springboot.rickandmortyapp.service.MovieCharacterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie-characters")
@AllArgsConstructor
public class MovieCharacterController {
    private final MovieCharacterService characterService;
    private final MovieCharacterMapper mapper;

    @GetMapping("/random")
    @ApiOperation(value = "Get random character from DB")
    public CharacterResponseDto getRandomCharacter() {
        MovieCharacter character = characterService.getRandomCharacter();
        return mapper.characterToDto(character);
    }

    @GetMapping("by-name")
    @ApiOperation(value = "Get all characters where name contains search string")
    public List<CharacterResponseDto> getAllByPartName(
            @RequestParam(value = "name", defaultValue = "")
            @ApiParam(value = "Default value: empty line") String namePart) {
        return characterService.findAllByNameContains(namePart)
                .stream()
                .map(mapper::characterToDto)
                .collect(Collectors.toList());
    }
}
