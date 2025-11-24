package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.DnaRequest;
import org.example.dto.ErrorResponse;
import org.example.dto.StatsResponse;
import org.example.service.MutantService;
import org.example.service.StatsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for Mutant Detection API.
 * 
 * Provides two endpoints:
 * - POST /mutant - Verify if a DNA sequence belongs to a mutant
 * - GET /stats - Retrieve DNA verification statistics
 */
@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
@Tag(name = "Mutant Detector", description = "API for detecting mutants based on DNA sequences")
public class MutantController {

    private final MutantService mutantService;
    private final StatsService statsService;

    /**
     * POST /mutant - Verify if a DNA sequence belongs to a mutant.
     * 
     * Request Body Example:
     * {
     *   "dna": ["ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTG"]
     * }
     * 
     * @param request DnaRequest containing the DNA sequence
     * @return 200 OK if mutant, 403 Forbidden if human
     */
    @PostMapping("/mutant")
    @Operation(
        summary = "Verify if DNA belongs to a mutant",
        description = "Analyzes a DNA sequence to determine if it contains more than one sequence of four identical letters"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "DNA belongs to a mutant (more than one sequence found)",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "403",
            description = "DNA belongs to a human (one or zero sequences found)",
            content = @Content
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid DNA sequence format",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    public ResponseEntity<Void> checkMutant(@Valid @RequestBody DnaRequest request) {
        log.info("Received mutant verification request");
        
        boolean isMutant = mutantService.analyzeDna(request.getDna());
        
        if (isMutant) {
            log.info("Result: MUTANT - Returning 200 OK");
            return ResponseEntity.ok().build();
        } else {
            log.info("Result: HUMAN - Returning 403 Forbidden");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    /**
     * GET /stats - Retrieve DNA verification statistics.
     * 
     * Response Example:
     * {
     *   "count_mutant_dna": 40,
     *   "count_human_dna": 100,
     *   "ratio": 0.4
     * }
     * 
     * @return StatsResponse with verification statistics
     */
    @GetMapping("/stats")
    @Operation(
        summary = "Get DNA verification statistics",
        description = "Returns the count of mutant and human DNAs verified, and the ratio between them"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Statistics retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = StatsResponse.class)
            )
        )
    })
    public ResponseEntity<StatsResponse> getStats() {
        log.info("Received stats request");
        
        StatsResponse stats = statsService.getStats();
        
        log.info("Returning stats: {}", stats);
        return ResponseEntity.ok(stats);
    }
}
