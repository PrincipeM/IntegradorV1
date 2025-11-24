package org.example.exception;

/**
 * Custom exception thrown when SHA-256 hash calculation fails.
 * 
 * This is a runtime exception that wraps NoSuchAlgorithmException
 * which should never occur in a properly configured JVM.
 */
public class DnaHashCalculationException extends RuntimeException {

    public DnaHashCalculationException(String message) {
        super(message);
    }

    public DnaHashCalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}
