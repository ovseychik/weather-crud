package kz.test.weathercrud.client.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@Slf4j
public class WeatherApiExceptionHandler {

  @ExceptionHandler(WeatherApiException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public Mono<ProblemDetail> handleWeatherApiException(WeatherApiException ex) {
    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
        HttpStatus.BAD_REQUEST,
        ex.getMessage()
    );
    problemDetail.setProperty("code", ex.getCode());

    return Mono.just(problemDetail);
  }
}
