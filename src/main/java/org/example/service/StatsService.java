package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.StatsResponse;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for retrieving DNA verification statistics.
 * 
 * Provides aggregated data about mutant and human DNA verifications.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StatsService {

    private final DnaRecordRepository dnaRecordRepository;

    /**
     * Retrieves statistics about DNA verifications.
     * 
     * Returns:
     * - count_mutant_dna: Total mutant DNAs verified
     * - count_human_dna: Total human DNAs verified
     * - ratio: count_mutant_dna / count_human_dna (0 if no humans)
     * 
     * @return StatsResponse with verification statistics
     */
    @Transactional(readOnly = true)
    public StatsResponse getStats() {
        log.debug("Retrieving DNA verification statistics");

        long mutantCount = dnaRecordRepository.countMutants();
        long humanCount = dnaRecordRepository.countHumans();

        // Calculate ratio (avoid division by zero)
        double ratio = humanCount > 0 ? (double) mutantCount / humanCount : 0.0;

        log.info("Statistics - Mutants: {}, Humans: {}, Ratio: {}", 
                 mutantCount, humanCount, ratio);

        return new StatsResponse(mutantCount, humanCount, ratio);
    }
}
