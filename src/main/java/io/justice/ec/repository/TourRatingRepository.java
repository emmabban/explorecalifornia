package io.justice.ec.repository;

import io.justice.ec.domiain.TourRating;
import io.justice.ec.domiain.TourRatingPk;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface TourRatingRepository  extends CrudRepository<TourRating, TourRatingPk> {

    List<TourRating> findByPkTourId(Integer tourId);

    Optional<TourRating> findByPkTourIdAndPkCustomerId(Integer tourId, Integer customerId);

    Page<TourRating> findByPkTourId(Integer tourId, Pageable pageable);


    @Override
    @RestResource(exported = false)
    <S extends TourRating> S save(S entity);

    @Override
    @RestResource(exported = false)
    <S extends TourRating> Iterable<S> saveAll(Iterable<S> entities);

    @Override
    @RestResource(exported = false)
    void deleteById(TourRatingPk tourRatingPk);

    @Override
    @RestResource(exported = false)
    void delete(TourRating entity);

    @Override
    @RestResource(exported = false)
    void deleteAllById(Iterable<? extends TourRatingPk> tourRatingPks);

    @Override
    @RestResource(exported = false)
    void deleteAll(Iterable<? extends TourRating> entities);

    @Override
    @RestResource(exported = false)
    void deleteAll();
}
