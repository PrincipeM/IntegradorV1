package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JPA Entity representing a DNA verification record.
 * 
 * This entity stores:
 * - A unique hash of the DNA sequence (to avoid duplicates)
 * - Whether the DNA belongs to a mutant or human
 * 
 * Only one record per unique DNA sequence is stored in the database.
 */
@Entity
@Table(name = "dna_records", indexes = {
    @Index(name = "idx_dna_hash", columnList = "dna_hash", unique = true)
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DnaRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * SHA-256 hash of the DNA sequence.
     * Used to identify unique DNA sequences and prevent duplicates.
     */
    @Column(name = "dna_hash", nullable = false, unique = true, length = 64)
    private String dnaHash;

    /**
     * Indicates whether this DNA belongs to a mutant.
     * true = mutant, false = human
     */
    @Column(name = "is_mutant", nullable = false)
    private Boolean isMutant;

    /**
     * Constructor for creating a new DNA record.
     * 
     * @param dnaHash The SHA-256 hash of the DNA sequence
     * @param isMutant Whether the DNA is from a mutant
     */
    public DnaRecord(String dnaHash, Boolean isMutant) {
        this.dnaHash = dnaHash;
        this.isMutant = isMutant;
    }
}
