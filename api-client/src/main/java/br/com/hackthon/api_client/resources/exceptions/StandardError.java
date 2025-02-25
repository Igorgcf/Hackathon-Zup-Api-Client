package br.com.hackthon.api_client.resources.exceptions;

import lombok.*;

import java.time.*;

@Data
public class StandardError {

    private Instant timeStamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
