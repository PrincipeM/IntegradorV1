package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.DnaRecord;
import org.example.exception.DnaHashCalculationException;
import org.example.repository.DnaRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Optional;

/**
 * Service for analyzing DNA sequences and managing verification results.
 * 
 * Responsibilities:
 * - Analyze DNA sequences to detect mutants
 * - Calculate SHA-256 hash of DNA to identify unique sequences
 * - Check database cache before running expensive algorithm
 * - Store verification results in database
 * 
 * Uses SHA-256 hashing to avoid storing duplicate DNA sequences.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository dnaRecordRepository;

    /**
     * Analyzes a DNA sequence to determine if it belongs to a mutant.
     * 
     * Process:
     * 1. Calculate SHA-256 hash of the DNA
     * 2. Check if DNA was previously analyzed (cache)
     * 3. If cached, return stored result
     * 4. If not cached, run detection algorithm
     * 5. Store result in database
     * 
     * @param dna Array of strings representing the DNA matrix
     * @return true if mutant, false if human
     * @throws DnaHashCalculationException if hash calculation fails
     */
    @Transactional
    public boolean analyzeDna(String[] dna) {
        log.debug("Analyzing DNA sequence: {}", Arrays.toString(dna));

        // Calculate unique hash for this DNA sequence
        String dnaHash = calculateDnaHash(dna);
        log.debug("DNA Hash: {}", dnaHash);

        // Check if DNA was already analyzed (database cache)
        Optional<DnaRecord> existingRecord = dnaRecordRepository.findByDnaHash(dnaHash);
        
        if (existingRecord.isPresent()) {
            boolean isMutant = existingRecord.get().getIsMutant();
            log.info("DNA already analyzed. Result from cache: {}", isMutant ? "MUTANT" : "HUMAN");
            return isMutant;
        }

        // DNA not in cache - run detection algorithm
        boolean isMutant = mutantDetector.isMutant(dna);
        log.info("DNA analysis complete. Result: {}", isMutant ? "MUTANT" : "HUMAN");

        // Store result in database
        DnaRecord newRecord = new DnaRecord(dnaHash, isMutant);
        dnaRecordRepository.save(newRecord);
        log.debug("DNA record saved to database");

        return isMutant;
    }

    /**
     * Calculates SHA-256 hash of a DNA sequence.
     * 
     * The hash is calculated from the concatenated DNA strings to create
     * a unique identifier for each DNA sequence.
     * 
     * @param dna Array of strings representing the DNA
     * @return SHA-256 hash as hexadecimal string
     * @throws DnaHashCalculationException if SHA-256 algorithm is not available
     */
    private String calculateDnaHash(String[] dna) {
        try {
            // Concatenate all DNA strings
            String concatenated = String.join("", dna);
            
            // Calculate SHA-256 hash
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(concatenated.getBytes(StandardCharsets.UTF_8));
            
            // Convert to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("SHA-256 algorithm not available", e);
            throw new DnaHashCalculationException("Failed to calculate DNA hash", e);
        }
    }
}
