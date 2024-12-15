package kz.test.weathercrud.router.weather;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kz.test.weathercrud.model.dto.weatherapi.response.ForecastResponse;
import kz.test.weathercrud.model.dto.weatherapi.response.WeatherResponse;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class WeatherRouter {

  public static final String PATH = "/api/v1/weather";
  public static final String CURRENT = "/current/{cityId}";
  public static final String FORECAST = "/forecast/{cityId}";

  @Bean
  @RouterOperations({
      @RouterOperation(
          path = PATH + CURRENT,
          method = RequestMethod.GET,
          beanClass = WeatherHandler.class,
          beanMethod = "getCurrentWeather",
          operation = @Operation(
              operationId = "getCurrentWeather",
              summary = "Get current weather for a city",
              tags = {"Weather"},
              parameters = {
                  @Parameter(
                      in = ParameterIn.PATH,
                      name = "cityId",
                      description = "City ID",
                      required = true,
                      schema = @Schema(type = "integer", format = "int64")
                  )
              },
              responses = {
                  @ApiResponse(
                      responseCode = "200",
                      description = "Successful operation",
                      content = @Content(
                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                          schema = @Schema(implementation = WeatherResponse.class)
                      )
                  ),
                  @ApiResponse(
                      responseCode = "404",
                      description = "City not found"
                  )
              }
          )
      ),
      @RouterOperation(
          path = PATH + FORECAST,
          method = RequestMethod.GET,
          beanClass = WeatherHandler.class,
          beanMethod = "getForecast",
          operation = @Operation(
              operationId = "getForecast",
              summary = "Get weather forecast for a city",
              tags = {"Weather"},
              parameters = {
                  @Parameter(
                      in = ParameterIn.PATH,
                      name = "cityId",
                      description = "City ID",
                      required = true,
                      schema = @Schema(type = "integer", format = "int64")
                  ),
                  @Parameter(
                      in = ParameterIn.QUERY,
                      name = "days",
                      description = "Number of days for forecast",
                      required = true,
                      schema = @Schema(type = "integer", minimum = "1", maximum = "7")
                  )
              },
              responses = {
                  @ApiResponse(
                      responseCode = "200",
                      description = "Successful operation",
                      content = @Content(
                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                          schema = @Schema(implementation = ForecastResponse.class)
                      )
                  ),
                  @ApiResponse(
                      responseCode = "404",
                      description = "City not found"
                  )
              }
          )
      )
  })
  public RouterFunction<ServerResponse> weatherRoutes(WeatherHandler weatherHandler) {
    return route()
        .path(PATH, builder -> builder
            .nest(accept(APPLICATION_JSON), b -> b
                .GET(CURRENT, weatherHandler::getCurrentWeather)
                .GET(FORECAST, weatherHandler::getForecast)
            )
        )
        .build();
  }
}
