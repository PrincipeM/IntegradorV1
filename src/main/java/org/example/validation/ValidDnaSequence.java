package org.example.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Custom validation annotation for DNA sequences.
 * 
 * Validates that:
 * - DNA array is not null or empty
 * - Matrix is square (NxN)
 * - Minimum size is 4x4
 * - All rows have the same length
 * - Only contains valid nitrogenous bases: A, T, C, G
 * 
 * Usage:
 * @ValidDnaSequence
 * private String[] dna;
 */
@Documented
@Constraint(validatedBy = ValidDnaSequenceValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDnaSequence {

    String message() default "Invalid DNA sequence: must be a square NxN matrix (minimum 4x4) with only A, T, C, G characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
