package io.justice.ec.repository;

import io.justice.ec.domiain.Difficulty;
import io.justice.ec.domiain.Region;
import io.justice.ec.domiain.Tour;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TourRepository extends CrudRepository<Tour,Integer> {
    List<Tour> findByTourPackageCode(String code);
    Optional<Tour> findByTitle(String title);
    List<Tour> findByPrice(Integer price);
    Collection<Tour> findByDifficulty(Difficulty difficulty);
    //Invalid
    //Optional<Tour> findByRegion(Region region); // throws IncorrectResultSizeDataAccessException!!

    //Valid
    List<Tour> findByRegion(Region region);

    //Invalid
    List<Tour> findByRejion(Region region); // PropertyReferenceException: No property rejion found for type tour. Did you mean Region? -> facilitation of fast failure by Spring Data JPA

    //Complex queries with additional filter options
    List<Tour> findByTourPackageCodeAndRegion(String code, Region region);
    List<Tour> findByRegionIn(List<Region> regions);
    List<Tour> findByPriceLessThan(Integer maxPrice);
    List<Tour> findByKeywordsContains(String keyword);
    List<Tour> findByTourPackageCodeAndBulletsLike(String code, String searchString);
    List<Tour> findByTourPackageCodeAndDifficultyAndRegionAndPriceLessThan(String code,Difficulty difficulty,Region region, Integer maxPrice);

    @Query("Select t from Tour t where t.tourPackage.code = ?1 " +
    " and t.difficulty = ?2 and t.region = ?3 and t.price <= ?4")
    List<Tour> lookupTour(String code, Difficulty difficulty, Region region, Integer maxPrice);
    //same as
    //List<Tour> findByTourPackageCodeAndDifficultyAndRegionAndPriceLessThan(String code, Difficulty, Region region, Integer maxPrice);


}
