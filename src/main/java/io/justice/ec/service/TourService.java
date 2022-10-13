package io.justice.ec.service;

import io.justice.ec.domiain.Difficulty;
import io.justice.ec.domiain.Region;
import io.justice.ec.domiain.Tour;
import io.justice.ec.domiain.TourPackage;
import io.justice.ec.repository.TourPackageRepository;
import io.justice.ec.repository.TourRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TourService {
    private final TourRepository tourRepository;
    private final TourPackageRepository tourPackageRepository;

    public Tour createTour(String title,String description,String blurb,Integer price,String duration,String bullets,String keywords,String tourPackage,Difficulty difficulty,
                           Region region){

        //TourPackage tourPackageValue = tourPackageRepository.findById(tourPackage.getCode()).orElseGet(() -> tourPackageRepository.save(new TourPackage(tourPackage.getCode(),tourPackage.getName())));
        //TourPackage tourPackageValue = tourPackageRepository.findById(tourPackage).orElseThrow(() -> new RuntimeException("tour package must exist " + tourPackage));
        TourPackage tourPackageValue = tourPackageRepository.findByName(tourPackage).orElseThrow(() -> new RuntimeException("tour package must exist " + tourPackage));
        return tourRepository.save(new Tour(title,description,blurb,price,duration,bullets,keywords,tourPackageValue,difficulty));
    }

    public long total(){
       return tourRepository.count();
    }
}
