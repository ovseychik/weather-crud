package kz.test.weathercrud.repository.city;

import static kz.test.weather.jooq.generated.Tables.CITY;

import kz.test.weather.jooq.generated.tables.records.CityRecord;
import kz.test.weathercrud.model.dto.City;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class CityRepositoryImpl implements CityRepository {

  private final DSLContext dsl;

  @Override
  public Mono<City> save(City city) {
    return Mono.fromCallable(() -> {
      CityRecord cityRecord = dsl.newRecord(CITY);
      cityRecord.setName(city.getName());
      cityRecord.setCountry(city.getCountry());
      cityRecord.setLatitude(city.getLatitude());
      cityRecord.setLongitude(city.getLongitude());

      if (city.getId() != null) {
        cityRecord.setId(city.getId());
      }

      cityRecord.store();
      return mapToCity(cityRecord);
    });
  }

  @Override
  public Mono<City> findById(Long id) {
    return Mono.fromCallable(() ->
        dsl.selectFrom(CITY)
            .where(CITY.ID.eq(id))
            .fetchOptional()
            .map(this::mapToCity)
            .orElse(null)
    );
  }

  @Override
  public Flux<City> findAll() {
    return Mono.fromCallable(() ->
        dsl.selectFrom(CITY)
            .fetch()
            .map(this::mapToCity)
    ).flatMapMany(Flux::fromIterable);
  }

  @Override
  public Mono<Void> deleteById(Long id) {
    return Mono.fromRunnable(() ->
        dsl.deleteFrom(CITY)
            .where(CITY.ID.eq(id))
            .execute()
    );
  }

  private City mapToCity(CityRecord cityRecord) {
    return new City(
        cityRecord.getId(),
        cityRecord.getName(),
        cityRecord.getCountry(),
        cityRecord.getLatitude(),
        cityRecord.getLongitude(),
        cityRecord.getCreatedAt(),
        cityRecord.getUpdatedAt()
    );
  }
}
