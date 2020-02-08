package com.demo.imdb.controller;

public abstract class RestController {

    protected void assertParameterNotNull(String parameterName, String value) {
        if (parameterName == null )
            throw new RequestRequiredParameterIsMissing("Required parameter " + parameterName + " is missing");
    }

    class RequestRequiredParameterIsMissing extends RuntimeException {
        RequestRequiredParameterIsMissing(String message) {
            super(message);
        }
    }
}
