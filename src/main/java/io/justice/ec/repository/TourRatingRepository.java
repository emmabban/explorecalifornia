package io.justice.ec.repository;

import io.justice.ec.domiain.TourRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface TourRatingRepository  extends CrudRepository<TourRating, String> {

    List<TourRating> findByTourId(String tourId);

    Optional<TourRating> findByTourIdAndCustomerId(String tourId, Integer customerId);

    Page<TourRating> findByTourId(String tourId, Pageable pageable);


    @Override
    @RestResource(exported = false)
    <S extends TourRating> S save(S entity);

    @Override
    @RestResource(exported = false)
    <S extends TourRating> Iterable<S> saveAll(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    void deleteById(String string);

    @Override
    @RestResource(exported = false)
    void delete(TourRating entity);

    @Override
    @RestResource(exported = false)
    void deleteAllById(Iterable<? extends String> strings);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends TourRating> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}
