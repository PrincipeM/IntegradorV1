package org.example.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for MutantDetector class.
 * 
 * Tests the core algorithm for detecting mutant DNA sequences.
 * Covers all possible scenarios: horizontal, vertical, diagonal patterns,
 * edge cases, and invalid inputs.
 */
@DisplayName("MutantDetector Unit Tests")
class MutantDetectorTest {

    private final MutantDetector mutantDetector = new MutantDetector();

    // ========================
    // MUTANT TEST CASES (TRUE)
    // ========================

    @Test
    @DisplayName("Should detect mutant with horizontal and diagonal sequences")
    void testMutantWithHorizontalAndDiagonalSequences() {
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna), "Should be mutant (horizontal + diagonal)");
    }

    @Test
    @DisplayName("Should detect mutant with multiple horizontal sequences")
    void testMutantWithMultipleHorizontalSequences() {
        String[] dna = {
            "AAAATG",
            "CAGTGC",
            "TTTTGT",
            "AGAAGG",
            "CCGCTA",
            "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna), "Should be mutant (2 horizontal)");
    }

    @Test
    @DisplayName("Should detect mutant with multiple vertical sequences")
    void testMutantWithMultipleVerticalSequences() {
        String[] dna = {
            "ATGCGA",
            "ATGTGC",
            "ATATGT",
            "ATAAG G",
            "CCGCTA",
            "GCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna), "Should be mutant (2 vertical)");
    }

    @Test
    @DisplayName("Should detect mutant with multiple main diagonal sequences")
    void testMutantWithMultipleDiagonalSequences() {
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATGT",
            "AGAAGG",
            "CCCCTA",
            "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna), "Should be mutant (diagonal sequences)");
    }

    @Test
    @DisplayName("Should detect mutant with anti-diagonal sequences")
    void testMutantWithAntiDiagonalSequences() {
        String[] dna = {
            "ATGCGA",
            "CAGAGC",
            "TTATGT",
            "AGAAGG",
            "CCCGTA",
            "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna), "Should be mutant (anti-diagonal)");
    }

    @Test
    @DisplayName("Should detect mutant in 4x4 matrix")
    void testMutantIn4x4Matrix() {
        String[] dna = {
            "AAAA",
            "CCCC",
            "TGTA",
            "GCTG"
        };
        assertTrue(mutantDetector.isMutant(dna), "Should be mutant in 4x4 (2 horizontal)");
    }

    @Test
    @DisplayName("Should detect mutant in larger matrix (10x10)")
    void testMutantInLargeMatrix() {
        String[] dna = {
            "ATGCGATTTT",
            "CAGTGCAAAA",
            "TTATGTACGT",
            "AGAAGGCTGA",
            "CCCCTACTGA",
            "TCACTGCGTA",
            "ATGCGATTTT",
            "CAGTGCAAAA",
            "TTATGTACGT",
            "AGAAGGCTGA"
        };
        assertTrue(mutantDetector.isMutant(dna), "Should be mutant in 10x10");
    }

    // ========================
    // HUMAN TEST CASES (FALSE)
    // ========================

    @Test
    @DisplayName("Should detect human with no sequences")
    void testHumanWithNoSequences() {
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "TTATTT",
            "AGACGG",
            "GCGTCA",
            "TCACTG"
        };
        assertFalse(mutantDetector.isMutant(dna), "Should be human (no sequences)");
    }

    @Test
    @DisplayName("Should detect human with only one sequence")
    void testHumanWithOnlyOneSequence() {
        String[] dna = {
            "AAAAGA",
            "CAGTGC",
            "TTATTT",
            "AGACGG",
            "GCGTCA",
            "TCACTG"
        };
        assertFalse(mutantDetector.isMutant(dna), "Should be human (only 1 sequence)");
    }

    @Test
    @DisplayName("Should detect human in 4x4 matrix with one sequence")
    void testHumanIn4x4Matrix() {
        String[] dna = {
            "AAAA",
            "CCTC",
            "TGTA",
            "GCTG"
        };
        assertFalse(mutantDetector.isMutant(dna), "Should be human in 4x4 (only 1 sequence)");
    }

    // ========================
    // EDGE CASES
    // ========================

    @Test
    @DisplayName("Should throw exception for null DNA")
    void testNullDna() {
        assertThrows(IllegalArgumentException.class,
                () -> mutantDetector.isMutant(null),
                "Should throw exception for null DNA");
    }

    @Test
    @DisplayName("Should throw exception for empty DNA array")
    void testEmptyDna() {
        String[] dna = {};
        assertThrows(IllegalArgumentException.class,
                () -> mutantDetector.isMutant(dna),
                "Should throw exception for empty DNA");
    }

    @Test
    @DisplayName("Should return false for DNA smaller than 4x4")
    void testDnaSmallerThan4x4() {
        String[] dna = {
            "ATG",
            "CAG",
            "TTA"
        };
        assertFalse(mutantDetector.isMutant(dna), "Should be false for 3x3 matrix");
    }

    @Test
    @DisplayName("Should detect mutant with sequence at boundaries")
    void testSequenceAtBoundaries() {
        String[] dna = {
            "ATGCGA",
            "ATGTGC",
            "ATATGT",
            "ATAAGG",
            "CCGCTA",
            "GCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna), "Should detect vertical at left boundary");
    }

    @Test
    @DisplayName("Should detect mutant with sequence at right boundary")
    void testSequenceAtRightBoundary() {
        String[] dna = {
            "ATGCGA",
            "CAGTGA",
            "TTATGA",
            "AGAAGA",
            "CCGCTA",
            "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna), "Should detect vertical at right boundary");
    }

    @Test
    @DisplayName("Should detect mutant with sequence at bottom boundary")
    void testSequenceAtBottomBoundary() {
        String[] dna = {
            "ATGCGA",
            "CAGTGC",
            "ATATGT",
            "GGGGGG",
            "TTTTTT",
            "TCACTG"
        };
        assertTrue(mutantDetector.isMutant(dna), "Should detect horizontals at bottom");
    }

    @Test
    @DisplayName("Should handle all same character matrix")
    void testAllSameCharacter() {
        String[] dna = {
            "AAAA",
            "AAAA",
            "AAAA",
            "AAAA"
        };
        assertTrue(mutantDetector.isMutant(dna), "Should be mutant (multiple sequences)");
    }
}
