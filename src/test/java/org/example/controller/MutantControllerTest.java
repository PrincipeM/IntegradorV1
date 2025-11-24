package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.DnaRequest;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for MutantController.
 * 
 * Tests the REST endpoints with mocked services.
 */
@WebMvcTest(MutantController.class)
@DisplayName("MutantController Integration Tests")
class MutantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MutantService mutantService;

    @MockBean
    private StatsService statsService;

    private final String[] mutantDna = {
        "ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"
    };

    private final String[] humanDna = {
        "ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"
    };

    // ========================
    // POST /mutant TESTS
    // ========================

    @Test
    @DisplayName("POST /mutant should return 200 OK for mutant DNA")
    void testCheckMutant_ReturnOk_WhenIsMutant() throws Exception {
        // Arrange
        DnaRequest request = new DnaRequest(mutantDna);
        when(mutantService.analyzeDna(any(String[].class))).thenReturn(true);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /mutant should return 403 Forbidden for human DNA")
    void testCheckMutant_ReturnForbidden_WhenIsHuman() throws Exception {
        // Arrange
        DnaRequest request = new DnaRequest(humanDna);
        when(mutantService.analyzeDna(any(String[].class))).thenReturn(false);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /mutant should return 400 for invalid DNA (not square)")
    void testCheckMutant_ReturnBadRequest_WhenNotSquare() throws Exception {
        // Arrange
        String[] invalidDna = {"ATGC", "CAGT", "TTAT"};
        DnaRequest request = new DnaRequest(invalidDna);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mutant should return 400 for invalid DNA (invalid characters)")
    void testCheckMutant_ReturnBadRequest_WhenInvalidCharacters() throws Exception {
        // Arrange
        String[] invalidDna = {"ATXC", "CAGT", "TTAT", "AGAC"};
        DnaRequest request = new DnaRequest(invalidDna);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mutant should return 400 for empty DNA array")
    void testCheckMutant_ReturnBadRequest_WhenEmptyArray() throws Exception {
        // Arrange
        String[] emptyDna = {};
        DnaRequest request = new DnaRequest(emptyDna);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /mutant should return 400 for DNA smaller than 4x4")
    void testCheckMutant_ReturnBadRequest_WhenTooSmall() throws Exception {
        // Arrange
        String[] smallDna = {"ATG", "CAG", "TTA"};
        DnaRequest request = new DnaRequest(smallDna);

        // Act & Assert
        mockMvc.perform(post("/mutant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // ========================
    // GET /stats TESTS
    // ========================

    @Test
    @DisplayName("GET /stats should return statistics correctly")
    void testGetStats_ReturnsCorrectData() throws Exception {
        // Arrange
        StatsResponse statsResponse = new StatsResponse(40, 100, 0.4);
        when(statsService.getStats()).thenReturn(statsResponse);

        // Act & Assert
        mockMvc.perform(get("/stats")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(40))
                .andExpect(jsonPath("$.count_human_dna").value(100))
                .andExpect(jsonPath("$.ratio").value(0.4));
    }

    @Test
    @DisplayName("GET /stats should return zeros when no data")
    void testGetStats_ReturnsZeros_WhenNoData() throws Exception {
        // Arrange
        StatsResponse statsResponse = new StatsResponse(0, 0, 0.0);
        when(statsService.getStats()).thenReturn(statsResponse);

        // Act & Assert
        mockMvc.perform(get("/stats")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count_mutant_dna").value(0))
                .andExpect(jsonPath("$.count_human_dna").value(0))
                .andExpect(jsonPath("$.ratio").value(0.0));
    }
}
