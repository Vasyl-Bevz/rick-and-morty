package com.example.springboot.rickandmortyapp.dto;

import com.example.springboot.rickandmortyapp.model.Gender;
import com.example.springboot.rickandmortyapp.model.Status;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CharacterResponseDto {
    private Long id;
    private Long externalId;
    private String name;
    private Status status;
    private Gender gender;
    private String species;
    private String type;
}
