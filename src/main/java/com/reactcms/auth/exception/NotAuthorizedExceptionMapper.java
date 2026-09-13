package com.reactcms.auth.exception;

import com.reactcms.auth.dto.ErrorMessage;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotAuthorizedExceptionMapper implements ExceptionMapper<NotAuthorizedException> {

    @Override
    public Response toResponse(NotAuthorizedException exception) {
        String message = exception.getMessage() != null ? exception.getMessage() : "Unauthorized";
        // NotAuthorizedException message often includes "HTTP 401 Unauthorized" noise; keep short.
        if (message.startsWith("HTTP ")) {
            Throwable cause = exception.getCause();
            message = cause != null && cause.getMessage() != null
                    ? cause.getMessage()
                    : "Unauthorized";
        }
        return Response.status(Response.Status.UNAUTHORIZED)
                .entity(new ErrorMessage(message))
                .build();
    }
}
