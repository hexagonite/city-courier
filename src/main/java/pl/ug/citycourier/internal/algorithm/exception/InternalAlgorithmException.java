package pl.ug.citycourier.internal.algorithm.exception;

public class InternalAlgorithmException extends RuntimeException {

    public InternalAlgorithmException(String message) {
        super("Internal Algorithm exception: " + message);
    }

}
