package kz.test.weathercrud.repository.city;

import kz.test.weathercrud.model.dto.City;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CityRepository {

  /**
   * Saves or updates a city.
   *
   * @param city the city to save or update
   * @return the saved city
   */
  Mono<City> save(City city);

  /**
   * Finds a city by its ID.
   *
   * @param id the ID of the city to find
   * @return the city if found, empty Mono otherwise
   */
  Mono<City> findById(Long id);

  /**
   * Retrieves all cities.
   *
   * @return flux of all cities
   */
  Flux<City> findAll();

  /**
   * Deletes a city by its ID.
   *
   * @param id the ID of the city to delete
   * @return empty Mono when complete
   */
  Mono<Void> deleteById(Long id);
}
