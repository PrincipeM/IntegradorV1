package org.example.service;

import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for StatsService with mocked dependencies.
 * 
 * Tests statistics calculation and ratio computation.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("StatsService Unit Tests")
class StatsServiceTest {

    @Mock
    private DnaRecordRepository dnaRecordRepository;

    @InjectMocks
    private StatsService statsService;

    @Test
    @DisplayName("Should return correct statistics with mutants and humans")
    void testGetStats_WithMutantsAndHumans() {
        // Arrange
        when(dnaRecordRepository.countMutants()).thenReturn(40L);
        when(dnaRecordRepository.countHumans()).thenReturn(100L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertNotNull(stats);
        assertEquals(40L, stats.getCountMutantDna());
        assertEquals(100L, stats.getCountHumanDna());
        assertEquals(0.4, stats.getRatio(), 0.001);
    }

    @Test
    @DisplayName("Should return ratio 0 when no humans exist")
    void testGetStats_NoHumans() {
        // Arrange
        when(dnaRecordRepository.countMutants()).thenReturn(10L);
        when(dnaRecordRepository.countHumans()).thenReturn(0L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(10L, stats.getCountMutantDna());
        assertEquals(0L, stats.getCountHumanDna());
        assertEquals(0.0, stats.getRatio());
    }

    @Test
    @DisplayName("Should return zeros when no data exists")
    void testGetStats_NoData() {
        // Arrange
        when(dnaRecordRepository.countMutants()).thenReturn(0L);
        when(dnaRecordRepository.countHumans()).thenReturn(0L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(0L, stats.getCountMutantDna());
        assertEquals(0L, stats.getCountHumanDna());
        assertEquals(0.0, stats.getRatio());
    }

    @Test
    @DisplayName("Should calculate ratio correctly with equal counts")
    void testGetStats_EqualCounts() {
        // Arrange
        when(dnaRecordRepository.countMutants()).thenReturn(50L);
        when(dnaRecordRepository.countHumans()).thenReturn(50L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(50L, stats.getCountMutantDna());
        assertEquals(50L, stats.getCountHumanDna());
        assertEquals(1.0, stats.getRatio(), 0.001);
    }

    @Test
    @DisplayName("Should calculate ratio correctly with more humans than mutants")
    void testGetStats_MoreHumans() {
        // Arrange
        when(dnaRecordRepository.countMutants()).thenReturn(30L);
        when(dnaRecordRepository.countHumans()).thenReturn(70L);

        // Act
        StatsResponse stats = statsService.getStats();

        // Assert
        assertEquals(30L, stats.getCountMutantDna());
        assertEquals(70L, stats.getCountHumanDna());
        assertEquals(0.428, stats.getRatio(), 0.001);
    }

    @Test
    @DisplayName("Should call repository methods exactly once")
    void testGetStats_CallsRepositoryOnce() {
        // Arrange
        when(dnaRecordRepository.countMutants()).thenReturn(10L);
        when(dnaRecordRepository.countHumans()).thenReturn(20L);

        // Act
        statsService.getStats();

        // Assert
        verify(dnaRecordRepository, times(1)).countMutants();
        verify(dnaRecordRepository, times(1)).countHumans();
    }
}
