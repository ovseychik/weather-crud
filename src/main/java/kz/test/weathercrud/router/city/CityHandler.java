package kz.test.weathercrud.router.city;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import kz.test.weathercrud.model.dto.City;
import kz.test.weathercrud.service.city.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CityHandler {

  private final CityService cityService;

  public Mono<ServerResponse> createCity(ServerRequest request) {
    return request.bodyToMono(City.class)
        .flatMap(cityService::createCity)
        .flatMap(city -> ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .bodyValue(city));
  }

  public Mono<ServerResponse> getCity(ServerRequest request) {
    long id = Long.parseLong(request.pathVariable("id"));
    return cityService.getCity(id)
        .flatMap(city -> ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .bodyValue(city))
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> getAllCities() {
    return ServerResponse.ok()
        .contentType(APPLICATION_JSON)
        .body(cityService.getAllCities(), City.class);
  }

  public Mono<ServerResponse> updateCity(ServerRequest request) {
    long id = Long.parseLong(request.pathVariable("id"));
    return request.bodyToMono(City.class)
        .flatMap(city -> cityService.updateCity(id, city))
        .flatMap(city -> ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .bodyValue(city))
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  public Mono<ServerResponse> deleteCity(ServerRequest request) {
    long id = Long.parseLong(request.pathVariable("id"));
    return cityService.deleteCity(id)
        .then(ServerResponse.noContent().build());
  }
}
