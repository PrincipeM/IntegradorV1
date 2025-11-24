package org.example.repository;

import org.example.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for DnaRecord entity.
 * 
 * Provides database operations for DNA verification records.
 * Spring Data JPA automatically implements this interface.
 */
@Repository
public interface DnaRecordRepository extends JpaRepository<DnaRecord, Long> {

    /**
     * Find a DNA record by its hash.
     * Used to check if a DNA sequence has already been verified.
     * 
     * @param dnaHash The SHA-256 hash of the DNA sequence
     * @return Optional containing the DnaRecord if found
     */
    Optional<DnaRecord> findByDnaHash(String dnaHash);

    /**
     * Count the number of mutant DNA records.
     * 
     * @return The total count of mutant DNAs
     */
    @Query("SELECT COUNT(d) FROM DnaRecord d WHERE d.isMutant = true")
    long countMutants();

    /**
     * Count the number of human DNA records.
     * 
     * @return The total count of human DNAs
     */
    @Query("SELECT COUNT(d) FROM DnaRecord d WHERE d.isMutant = false")
    long countHumans();
}
