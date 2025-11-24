package org.example.service;

import org.example.entity.DnaRecord;
import org.example.exception.DnaHashCalculationException;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for MutantService with mocked dependencies.
 * 
 * Tests the orchestration logic, caching, and database operations.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("MutantService Unit Tests")
class MutantServiceTest {

    @Mock
    private MutantDetector mutantDetector;

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private MutantService mutantService;

    private String[] mutantDna;
    private String[] humanDna;

    @BeforeEach
    void setUp() {
        mutantDna = new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        humanDna = new String[]{"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
    }

    @Test
    @DisplayName("Should analyze DNA and return true for mutant (not cached)")
    void testAnalyzeDna_Mutant_NotCached() {
        // Arrange
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(mutantDna)).thenReturn(true);
        when(dnaRecordRepository.save(any(DnaRecord.class))).thenReturn(new DnaRecord());

        // Act
        boolean result = mutantService.analyzeDna(mutantDna);

        // Assert
        assertTrue(result, "Should return true for mutant DNA");
        verify(mutantDetector, times(1)).isMutant(mutantDna);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Should analyze DNA and return false for human (not cached)")
    void testAnalyzeDna_Human_NotCached() {
        // Arrange
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(humanDna)).thenReturn(false);
        when(dnaRecordRepository.save(any(DnaRecord.class))).thenReturn(new DnaRecord());

        // Act
        boolean result = mutantService.analyzeDna(humanDna);

        // Assert
        assertFalse(result, "Should return false for human DNA");
        verify(mutantDetector, times(1)).isMutant(humanDna);
        verify(dnaRecordRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Should return cached result for mutant DNA")
    void testAnalyzeDna_Mutant_Cached() {
        // Arrange
        DnaRecord cachedRecord = new DnaRecord("hash123", true);
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(cachedRecord));

        // Act
        boolean result = mutantService.analyzeDna(mutantDna);

        // Assert
        assertTrue(result, "Should return cached mutant result");
        verify(mutantDetector, never()).isMutant(any());
        verify(dnaRecordRepository, never()).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Should return cached result for human DNA")
    void testAnalyzeDna_Human_Cached() {
        // Arrange
        DnaRecord cachedRecord = new DnaRecord("hash456", false);
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.of(cachedRecord));

        // Act
        boolean result = mutantService.analyzeDna(humanDna);

        // Assert
        assertFalse(result, "Should return cached human result");
        verify(mutantDetector, never()).isMutant(any());
        verify(dnaRecordRepository, never()).save(any(DnaRecord.class));
    }

    @Test
    @DisplayName("Should save DNA record after analysis")
    void testAnalyzeDna_SavesRecord() {
        // Arrange
        when(dnaRecordRepository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(mutantDetector.isMutant(any())).thenReturn(true);

        // Act
        mutantService.analyzeDna(mutantDna);

        // Assert
        verify(dnaRecordRepository, times(1)).save(argThat(record ->
                record.getIsMutant() == true && record.getDnaHash() != null
        ));
    }
}
