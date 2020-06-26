package pl.ug.citycourier.internal.security.internal.exception;

import java.util.Map;

public class BindingResultException extends RuntimeException {

    private Map<String, String> errors;

    public BindingResultException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
