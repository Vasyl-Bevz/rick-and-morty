package com.example.springboot.rickandmortyapp.controller;

import com.example.springboot.rickandmortyapp.model.Gender;
import com.example.springboot.rickandmortyapp.model.MovieCharacter;
import com.example.springboot.rickandmortyapp.model.Status;
import com.example.springboot.rickandmortyapp.service.MovieCharacterServiceImpl;
import java.util.List;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MovieCharacterControllerTest {
    @MockBean
    private MovieCharacterServiceImpl movieCharacterService;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void getRandomCharacter_Ok() {
        MovieCharacter bob = new MovieCharacter();
        bob.setId(10L);
        bob.setExternalId(25L);
        bob.setName("Bob Alice");
        bob.setStatus(Status.ALIVE);
        bob.setGender(Gender.MALE);
        bob.setSpecies("Humanoid");
        bob.setType("Human with baby legs");
        Mockito.when(movieCharacterService.getRandomCharacter()).thenReturn(bob);
        RestAssuredMockMvc.when()
                .get("/movie-characters/random")
                .then()
                .statusCode(200)
                .body("id", Matchers.equalTo(10))
                .body("externalId", Matchers.equalTo(25))
                .body("name", Matchers.equalTo("Bob Alice"))
                .body("status", Matchers.equalTo("ALIVE"))
                .body("gender", Matchers.equalTo("MALE"))
                .body("species", Matchers.equalTo("Humanoid"))
                .body("type", Matchers.equalTo("Human with baby legs"));
    }

    @Test
    public void findAllByName_Ok() {
        MovieCharacter bob = new MovieCharacter();
        bob.setId(10L);
        bob.setExternalId(25L);
        bob.setName("Bob and Alice");
        bob.setStatus(Status.ALIVE);
        bob.setGender(Gender.MALE);
        bob.setSpecies("Humanoid");
        bob.setType("Human with baby legs");

        MovieCharacter alice = new MovieCharacter();
        alice.setId(203L);
        alice.setExternalId(456L);
        alice.setName("Alice in red");
        alice.setStatus(Status.DEAD);
        alice.setGender(Gender.FEMALE);
        alice.setSpecies("Humanoid");
        alice.setType("Human with baby legs");

        List<MovieCharacter> characters = List.of(bob, alice);

        String namePart = "Alic";
        Mockito.when(movieCharacterService.findAllByNameContains(namePart)).thenReturn(characters);
        RestAssuredMockMvc.given()
                .queryParam("name", namePart)
                .when()
                .get("/movie-characters/by-name")
                .then()
                .statusCode(200)
                .body("size()", Matchers.equalTo(2))
                .body("[0].name", Matchers.equalTo("Bob and Alice"))
                .body("[0].gender", Matchers.equalTo("MALE"))
                .body("[0].status", Matchers.equalTo("ALIVE"))
                .body("[0].species", Matchers.equalTo("Humanoid"))
                .body("[0].type", Matchers.equalTo("Human with baby legs"))
                .body("[1].name", Matchers.equalTo("Alice in red"))
                .body("[1].gender", Matchers.equalTo("FEMALE"))
                .body("[1].status", Matchers.equalTo("DEAD"))
                .body("[1].species", Matchers.equalTo("Humanoid"))
                .body("[1].type", Matchers.equalTo("Human with baby legs"));
    }
}