package io.justice.ec.service;

import io.justice.ec.domiain.TourPackage;
import io.justice.ec.repository.TourPackageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class TourPackageService {
    private final TourPackageRepository tourPackageRepository;


    public TourPackage createTourPackage(String code, String name){

        return tourPackageRepository.findById(code).orElseGet(() -> tourPackageRepository.save(new TourPackage(code,name)));

    }

    public  Iterable<TourPackage> lookup() {
        return tourPackageRepository.findAll();
    }

    public Optional<TourPackage> lookupByName(String tourPackageName) {
        return tourPackageRepository.findByName(tourPackageName);
    }

    public long total(){
        return tourPackageRepository.count();
    }

}
