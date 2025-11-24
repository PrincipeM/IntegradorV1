package org.example.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

/**
 * Validator implementation for @ValidDnaSequence annotation.
 * 
 * Performs comprehensive validation of DNA sequences:
 * 1. Not null or empty
 * 2. Square matrix (N rows, each with N characters)
 * 3. Minimum size 4x4
 * 4. No null rows
 * 5. Only valid characters: A, T, C, G (case-insensitive)
 */
public class ValidDnaSequenceValidator implements ConstraintValidator<ValidDnaSequence, String[]> {

    private static final int MIN_SIZE = 4;
    private static final Pattern VALID_DNA_PATTERN = Pattern.compile("^[ATCG]+$", Pattern.CASE_INSENSITIVE);

    @Override
    public void initialize(ValidDnaSequence constraintAnnotation) {
        // No initialization needed
    }

    @Override
    public boolean isValid(String[] dna, ConstraintValidatorContext context) {
        // 1. Check if DNA is null or empty
        if (dna == null || dna.length == 0) {
            addCustomMessage(context, "DNA array cannot be null or empty");
            return false;
        }

        final int n = dna.length;

        // 2. Check minimum size
        if (n < MIN_SIZE) {
            addCustomMessage(context, String.format("DNA matrix must be at least %dx%d", MIN_SIZE, MIN_SIZE));
            return false;
        }

        // 3. Validate each row
        for (int i = 0; i < n; i++) {
            String row = dna[i];

            // Check for null rows
            if (row == null) {
                addCustomMessage(context, String.format("Row %d cannot be null", i));
                return false;
            }

            // Check if matrix is square (each row must have N characters)
            if (row.length() != n) {
                addCustomMessage(context, String.format(
                    "Matrix must be square NxN. Expected %d characters in row %d, but got %d",
                    n, i, row.length()
                ));
                return false;
            }

            // Check if row contains only valid characters (A, T, C, G)
            if (!VALID_DNA_PATTERN.matcher(row).matches()) {
                addCustomMessage(context, String.format(
                    "Row %d contains invalid characters. Only A, T, C, G are allowed",
                    i
                ));
                return false;
            }
        }

        // All validations passed
        return true;
    }

    /**
     * Adds a custom error message to the validation context.
     */
    private void addCustomMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
