package io.justice.ec.repository;

import io.justice.ec.domiain.Tour;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;
import java.util.Optional;

//public interface TourRepository extends CrudRepository<Tour,Integer> {
public interface TourRepository extends PagingAndSortingRepository<Tour,String> {
   // List<Tour> findByTourPackageCode(String code);
   // List<Tour> findByTourPackageCode(@Param("code")String code);// old version of spring data rest
    Page<Tour> findByTourPackageCode(String code, Pageable pageable);

 @Query(value = "{'tourPackageCode' : ?0 }", fields =  "{ 'id' :1 , 'title' : 1, 'tourPackageCode' :1, 'tourPackageName':1}")
 Page<Tour> findSummaryByTourPackageCode(@Param("code")String code,Pageable pageable);

/* @Query(value = "{'tourPackageCode' : ?0 }",
         fields = "{ 'id':1, 'title':1, 'tourPackageCode':1, 'tourPackageName':1}")
 Page<Tour> findSummaryByTourPackageCode(@Param("code")String code, Pageable pageable);*/

    Optional<Tour> findByTitle(String title);
    //List<Tour> findByPrice(Integer price);


    //Invalid
    //List<Tour> findByRejion(Region region); // PropertyReferenceException: No property rejion found for type tour. Did you mean Region? -> facilitation of fast failure by Spring Data JPA
    //Caused by: org.springframework.data.mapping.PropertyReferenceException: No property 'rejion' found for type 'Tour' Did you mean ''region''

    //Complex queries with additional filter options
    //List<Tour> findByPriceLessThan(Integer maxPrice);
   // List<Tour> findByKeywordsContains(String keyword);
   // List<Tour> findByTourPackageCodeAndBulletsLike(String code, String searchString);


 @Override
 @RestResource(exported = false)
 <S extends Tour> S save(S entity);

 @Override
 @RestResource(exported = false)
 <S extends Tour> Iterable<S> saveAll(Iterable<S> entities);

 @Override
 @RestResource(exported = false)
 void deleteById(String string);

 @Override
 @RestResource(exported = false)
 void delete(Tour entity);

 @Override
 @RestResource(exported = false)
 void deleteAllById(Iterable<? extends String> strings);

 @Override
 @RestResource(exported = false)
 void deleteAll(Iterable<? extends Tour> entities);

 @Override
 @RestResource(exported = false)
 void deleteAll();
}
