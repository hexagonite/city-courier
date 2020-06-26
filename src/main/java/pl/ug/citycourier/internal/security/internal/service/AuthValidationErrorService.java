package pl.ug.citycourier.internal.security.internal.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import pl.ug.citycourier.internal.security.internal.exception.BindingResultException;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthValidationErrorService {

    public void validateFromBindingResult(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : result.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new BindingResultException(errorMap);
        }
    }
}