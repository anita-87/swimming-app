package com.anarodriguez.licenses.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
@Order(-2)
@Slf4j
public class RestExceptionHandler implements WebExceptionHandler {

    private final ObjectMapper objectMapper;

    public RestExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        log.warn("Exception " + ex.getMessage() +" handled");
        if (ex instanceof InvalidLicenceTypeException) {
            ErrorMessage errorMessage = getErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage());
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(objectMapper.writeValueAsBytes(errorMessage));
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            return exchange.getResponse().writeWith(Mono.just(buffer));
        }
        return Mono.error(ex);
    }

    private ErrorMessage getErrorMessage(HttpStatus status, String message) {
        ErrorMessage errorMessage = new ErrorMessage();
        errorMessage.setTimestamp(LocalDate.now().toString());
        errorMessage.setStatus(status.value());
        errorMessage.setError(status.name());
        errorMessage.setMessage(message);
        return errorMessage;
    }
}
