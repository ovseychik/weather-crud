package kz.test.weathercrud.router.city;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import kz.test.weathercrud.model.dto.CityResponse;
import kz.test.weathercrud.model.dto.CreateCityRequest;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CityRouter {

  public static final String PATH = "/api/v1/cities";
  public static final String CITY_ID = "/{id}";

  @Bean
  @RouterOperations({
      @RouterOperation(
          path = PATH,
          method = RequestMethod.GET,
          beanClass = CityHandler.class,
          beanMethod = "getAllCities",
          operation = @Operation(
              operationId = "getAllCities",
              summary = "Get all cities",
              tags = {"City"},
              responses = {
                  @ApiResponse(
                      responseCode = "200",
                      description = "Successful operation",
                      content = @Content(
                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                          schema = @Schema(implementation = CityResponse.class)
                      )
                  )
              }
          )
      ),
      @RouterOperation(
          path = PATH,
          method = RequestMethod.POST,
          beanClass = CityHandler.class,
          beanMethod = "createCity",
          operation = @Operation(
              operationId = "createCity",
              summary = "Create a new city",
              tags = {"City"},
              requestBody = @RequestBody(
                  required = true,
                  content = @Content(
                      mediaType = MediaType.APPLICATION_JSON_VALUE,
                      schema = @Schema(implementation = CreateCityRequest.class)
                  )
              ),
              responses = {
                  @ApiResponse(
                      responseCode = "200",
                      description = "City created successfully",
                      content = @Content(
                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                          schema = @Schema(implementation = CityResponse.class)
                      )
                  )
              }
          )
      ),
      @RouterOperation(
          path = PATH + CITY_ID,
          method = RequestMethod.GET,
          beanClass = CityHandler.class,
          beanMethod = "getCity",
          operation = @Operation(
              operationId = "getCityById",
              summary = "Get city by ID",
              tags = {"City"},
              parameters = {
                  @Parameter(
                      in = ParameterIn.PATH,
                      name = "id",
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
                          schema = @Schema(implementation = CityResponse.class)
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
          path = PATH + CITY_ID,
          method = RequestMethod.PUT,
          beanClass = CityHandler.class,
          beanMethod = "updateCity",
          operation = @Operation(
              operationId = "updateCity",
              summary = "Update an existing city",
              tags = {"City"},
              parameters = {
                  @Parameter(
                      in = ParameterIn.PATH,
                      name = "id",
                      description = "City ID",
                      required = true,
                      schema = @Schema(type = "integer", format = "int64")
                  )
              },
              requestBody = @RequestBody(
                  required = true,
                  content = @Content(
                      mediaType = MediaType.APPLICATION_JSON_VALUE,
                      schema = @Schema(implementation = CreateCityRequest.class)
                  )
              ),
              responses = {
                  @ApiResponse(
                      responseCode = "200",
                      description = "City updated successfully",
                      content = @Content(
                          mediaType = MediaType.APPLICATION_JSON_VALUE,
                          schema = @Schema(implementation = CityResponse.class)
                      )
                  ),
                  @ApiResponse(
                      responseCode = "404",
                      description = "City not found"
                  ),
                  @ApiResponse(
                      responseCode = "400",
                      description = "Invalid input"
                  )
              }
          )
      ),
      @RouterOperation(
          path = PATH + CITY_ID,
          method = RequestMethod.DELETE,
          beanClass = CityHandler.class,
          beanMethod = "deleteCity",
          operation = @Operation(
              operationId = "deleteCity",
              summary = "Delete a city",
              tags = {"City"},
              parameters = {
                  @Parameter(
                      in = ParameterIn.PATH,
                      name = "id",
                      description = "City ID",
                      required = true,
                      schema = @Schema(type = "integer", format = "int64")
                  )
              },
              responses = {
                  @ApiResponse(
                      responseCode = "204",
                      description = "City deleted successfully"
                  ),
                  @ApiResponse(
                      responseCode = "404",
                      description = "City not found"
                  )
              }
          )
      )
  })
  public RouterFunction<ServerResponse> cityRoutes(CityHandler cityHandler) {
    return route()
        .path(PATH, builder -> builder
            .nest(accept(APPLICATION_JSON), b -> b
                .GET(ignored -> cityHandler.getAllCities())
                .POST("", cityHandler::createCity)
                .GET(CITY_ID, cityHandler::getCity)
                .PUT(CITY_ID, cityHandler::updateCity)
                .DELETE(CITY_ID, cityHandler::deleteCity)
            )
        )
        .build();
  }
}
