package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for statistics response.
 * 
 * Represents the JSON response for GET /stats endpoint:
 * {
 *   "count_mutant_dna": 40,
 *   "count_human_dna": 100,
 *   "ratio": 0.4
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StatsResponse {

    /**
     * Total count of verified mutant DNAs.
     */
    @JsonProperty("count_mutant_dna")
    private long countMutantDna;

    /**
     * Total count of verified human DNAs.
     */
    @JsonProperty("count_human_dna")
    private long countHumanDna;

    /**
     * Ratio of mutant DNAs vs human DNAs.
     * Formula: count_mutant_dna / count_human_dna
     * Returns 0 if there are no human DNAs to avoid division by zero.
     */
    @JsonProperty("ratio")
    private double ratio;
}
