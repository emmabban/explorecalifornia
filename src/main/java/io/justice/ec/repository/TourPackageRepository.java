package io.justice.ec.repository;

import io.justice.ec.domiain.TourPackage;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TourPackageRepository extends CrudRepository<TourPackage,String> {
    Optional<TourPackage> findByName(String tourPackageName);
}
