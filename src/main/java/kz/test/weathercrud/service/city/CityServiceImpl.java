package kz.test.weathercrud.service.city;

import kz.test.weathercrud.model.dto.City;
import kz.test.weathercrud.repository.city.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

  private final CityRepository cityRepository;

  @Override
  public Mono<City> createCity(City city) {
    return cityRepository.save(city);
  }

  @Override
  public Mono<City> getCity(Long id) {
    return cityRepository.findById(id);
  }

  @Override
  public Flux<City> getAllCities() {
    return cityRepository.findAll();
  }

  @Override
  public Mono<City> updateCity(Long id, City city) {
    return cityRepository.findById(id)
        .flatMap(existingCity -> {
          city.setId(id);
          return cityRepository.save(city);
        });
  }

  @Override
  public Mono<Void> deleteCity(Long id) {
    return cityRepository.deleteById(id);
  }
}
