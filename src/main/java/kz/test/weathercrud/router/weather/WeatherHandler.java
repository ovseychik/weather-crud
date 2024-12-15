package kz.test.weathercrud.router.weather;

import kz.test.weathercrud.model.dto.weatherapi.response.ForecastResponse;
import kz.test.weathercrud.model.dto.weatherapi.response.WeatherResponse;
import kz.test.weathercrud.service.city.CityService;
import kz.test.weathercrud.service.weather.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class WeatherHandler {

  private final WeatherService weatherService;
  private final CityService cityService;

  public Mono<ServerResponse> getCurrentWeather(ServerRequest request) {
    Long cityId = Long.valueOf(request.pathVariable("cityId"));

    return cityService.getCity(cityId)
        .flatMap(city -> {
          WeatherResponse response = weatherService.getCurrentWeather(
              cityId,
              city.getLatitude(),
              city.getLongitude()
          );
          return ServerResponse.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue(response);
        })
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> getForecast(ServerRequest request) {
    Long cityId = Long.valueOf(request.pathVariable("cityId"));
    int days = Integer.parseInt(request.queryParam("days").orElse("1"));

    if (days < 1 || days > 7) {
      return ServerResponse.badRequest()
          .bodyValue("Days parameter must be between 1 and 7");
    }

    return cityService.getCity(cityId)
        .flatMap(city -> {
          ForecastResponse response = weatherService.getForecast(
              cityId,
              city.getLatitude(),
              city.getLongitude(),
              days
          );
          return ServerResponse.ok()
              .contentType(MediaType.APPLICATION_JSON)
              .bodyValue(response);
        })
        .switchIfEmpty(ServerResponse.notFound().build());
  }
}
