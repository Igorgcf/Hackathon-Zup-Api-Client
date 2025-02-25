package br.com.hackthon.api_client.resources.exceptions;

import lombok.*;

import java.util.*;

@Data
public class ValidatorError extends StandardError{

    private List<FieldMessage> errors = new ArrayList<>();

    public void addError(String fieldName, String message){
        errors.add(new FieldMessage(fieldName, message));
    }
}
