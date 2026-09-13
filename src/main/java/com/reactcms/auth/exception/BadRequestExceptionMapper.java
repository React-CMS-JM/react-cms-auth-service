package com.reactcms.auth.exception;

import com.reactcms.auth.dto.ErrorMessage;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequestException> {

    @Override
    public Response toResponse(BadRequestException exception) {
        String message = exception.getMessage() != null ? exception.getMessage() : "Bad request";
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorMessage(message))
                .build();
    }
}
