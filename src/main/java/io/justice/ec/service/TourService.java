package io.justice.ec.service;

import io.justice.ec.domiain.Tour;
import io.justice.ec.domiain.TourPackage;
import io.justice.ec.repository.TourPackageRepository;
import io.justice.ec.repository.TourRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@AllArgsConstructor
@Service
public class TourService {
    private final TourRepository tourRepository;
    private final TourPackageRepository tourPackageRepository;

    public Tour createTour(String title, String tourPackageName, Map<String, String> details){

        TourPackage tourPackageValue = tourPackageRepository.findByName(tourPackageName).orElseThrow(() -> new RuntimeException("tour package must exist " + tourPackageName));
        return tourRepository.save(new Tour(title,tourPackageValue,details));
    }

    public long total(){
       return tourRepository.count();
    }
}
