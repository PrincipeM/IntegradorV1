package org.example.service;

import org.springframework.stereotype.Component;

/**
 * Core algorithm for detecting mutant DNA sequences.
 * 
 * A DNA sequence belongs to a mutant if it contains MORE THAN ONE sequence
 * of four identical letters (A, T, C, G) in any direction:
 * - Horizontal (→)
 * - Vertical (↓)
 * - Diagonal (↘)
 * - Anti-diagonal (↙)
 * 
 * Performance optimizations:
 * - Early termination: stops as soon as >1 sequences are found
 * - Converts to char[][] for O(1) access
 * - Boundary checking to avoid unnecessary iterations
 * - Direct comparisons without loops
 * 
 * Time Complexity: O(N²) worst case, ~O(N) average with early termination
 * Space Complexity: O(N²) for the char matrix conversion
 */
@Component
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;
    private static final int MUTANT_THRESHOLD = 2; // More than 1 sequence

    /**
     * Determines if a DNA sequence belongs to a mutant.
     * 
     * @param dna Array of strings representing the NxN DNA matrix
     * @return true if mutant (>1 sequences found), false otherwise
     * @throws IllegalArgumentException if DNA is null or invalid
     */
    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length == 0) {
            throw new IllegalArgumentException("DNA array cannot be null or empty");
        }

        final int n = dna.length;

        // Validate minimum size
        if (n < SEQUENCE_LENGTH) {
            return false; // Can't form a sequence of 4 in a matrix smaller than 4x4
        }

        // Convert to char matrix for faster access
        final char[][] matrix = convertToCharMatrix(dna, n);

        // Search for sequences in all directions
        int sequenceCount = 0;

        // Search horizontally and vertically simultaneously
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                // Check horizontal (→)
                if (col <= n - SEQUENCE_LENGTH) {
                    if (checkHorizontal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount >= MUTANT_THRESHOLD) {
                            return true; // Early termination
                        }
                    }
                }

                // Check vertical (↓)
                if (row <= n - SEQUENCE_LENGTH) {
                    if (checkVertical(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount >= MUTANT_THRESHOLD) {
                            return true; // Early termination
                        }
                    }
                }

                // Check main diagonal (↘)
                if (row <= n - SEQUENCE_LENGTH && col <= n - SEQUENCE_LENGTH) {
                    if (checkMainDiagonal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount >= MUTANT_THRESHOLD) {
                            return true; // Early termination
                        }
                    }
                }

                // Check anti-diagonal (↙)
                if (row <= n - SEQUENCE_LENGTH && col >= SEQUENCE_LENGTH - 1) {
                    if (checkAntiDiagonal(matrix, row, col)) {
                        sequenceCount++;
                        if (sequenceCount >= MUTANT_THRESHOLD) {
                            return true; // Early termination
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Converts String[] DNA to char[][] matrix for faster access.
     */
    private char[][] convertToCharMatrix(String[] dna, int n) {
        char[][] matrix = new char[n][];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }
        return matrix;
    }

    /**
     * Checks for a horizontal sequence of 4 identical characters (→).
     */
    private boolean checkHorizontal(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row][col + 1] == base &&
               matrix[row][col + 2] == base &&
               matrix[row][col + 3] == base;
    }

    /**
     * Checks for a vertical sequence of 4 identical characters (↓).
     */
    private boolean checkVertical(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col] == base &&
               matrix[row + 2][col] == base &&
               matrix[row + 3][col] == base;
    }

    /**
     * Checks for a main diagonal sequence of 4 identical characters (↘).
     */
    private boolean checkMainDiagonal(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col + 1] == base &&
               matrix[row + 2][col + 2] == base &&
               matrix[row + 3][col + 3] == base;
    }

    /**
     * Checks for an anti-diagonal sequence of 4 identical characters (↙).
     */
    private boolean checkAntiDiagonal(char[][] matrix, int row, int col) {
        final char base = matrix[row][col];
        return matrix[row + 1][col - 1] == base &&
               matrix[row + 2][col - 2] == base &&
               matrix[row + 3][col - 3] == base;
    }
}
