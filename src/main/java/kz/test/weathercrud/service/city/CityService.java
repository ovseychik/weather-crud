package kz.test.weathercrud.service.city;

import kz.test.weathercrud.model.dto.City;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CityService {

  Mono<City> createCity(City city);

  Mono<City> getCity(Long id);

  Flux<City> getAllCities();

  Mono<City> updateCity(Long id, City city);

  Mono<Void> deleteCity(Long id);
}
