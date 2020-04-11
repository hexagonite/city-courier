package pl.ug.citycourier.internal.algorithm.exception;

public class InternalAlgorithmException extends Exception{

    public InternalAlgorithmException(String message) {
        super("Internal Algorithm exception: " + message);
    }

}
