package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.validation.ValidDnaSequence;

/**
 * Data Transfer Object for DNA verification requests.
 * 
 * Represents the JSON payload received in POST /mutant endpoint:
 * {
 *   "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DnaRequest {

    /**
     * DNA sequence represented as an array of strings.
     * Each string is a row in the NxN matrix.
     * 
     * Validations:
     * - Must be a square matrix (NxN)
     * - Minimum size: 4x4
     * - Only contains characters: A, T, C, G
     */
    @JsonProperty("dna")
    @ValidDnaSequence
    private String[] dna;
}
