package io.justice.ec.web;


import io.justice.ec.domiain.TourRating;
import io.justice.ec.repository.TourRatingRepository;
import io.justice.ec.repository.TourRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "tour/{tourId}/ratings")
@AllArgsConstructor
public class TourRatingController {

    final TourRatingRepository tourRatingRepository;
    final TourRepository tourRepository;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createRating(@PathVariable("tourId") String tourId, @RequestBody @Validated TourRating tourRating){
         verifyTour(tourId);

        TourRating tourRatingValue = new TourRating(tourId,tourRating.getCustomerId(),tourRating.getScore(),tourRating.getComment());
        tourRatingRepository.save(tourRatingValue);



    }

    @GetMapping
    public List<TourRating> getTourRatings(@PathVariable(name = "tourId",value = "tourId") String tourId){
        verifyTour(tourId);
        return tourRatingRepository.findByTourId(tourId);
    }

    //@GetMapping("/average")
    @GetMapping(path = "/average")
    public Map<String, Double> getAverage(@PathVariable(value = "tourId") String tourId){
        verifyTour(tourId);
        return Map.of("average", tourRatingRepository.findByTourId(tourId).stream().mapToInt(TourRating::getScore).average().orElseThrow(() -> new NoSuchElementException("Tour has no ratings")));
    }

    @PutMapping
    public TourRating  updateWithPut(@PathVariable(value = "tourId") String tourId, @RequestBody @Validated TourRating tourRating){
        TourRating rating = verifyTourRating(tourId,tourRating.getCustomerId());
        rating.setScore(tourRating.getScore());
        rating.setComment(tourRating.getComment());
        return tourRatingRepository.save(rating);
    }

    @PatchMapping
    public TourRating updateWithPatch(@PathVariable(value = "tourId") String tourId, @RequestBody @Validated TourRating tourRating){
        TourRating rating = verifyTourRating(tourId,tourRating.getCustomerId());

        if(rating.getScore() != null) {
            rating.setScore(tourRating.getScore());
        }

        if(rating.getComment() != null) {
        rating.setComment(tourRating.getComment());
        }
        return tourRatingRepository.save(rating);
    }






    @DeleteMapping("/{customerId}")
    public void deleteRating(@PathVariable(value = "tourId") String tourId, @PathVariable(value = "customerId") int customerId){
        TourRating rating = verifyTourRating(tourId,customerId);

        tourRatingRepository.delete(rating);
    }



    @GetMapping("/pages")
    public Page<TourRating> getTourRatingsByPage(@PathVariable(name = "tourId",value = "tourId") String tourId, Pageable pageable){
        verifyTour(tourId);
        Page<TourRating> ratings =  tourRatingRepository.findByTourId(tourId,pageable);

        return ratings;
    }

    private void verifyTour(String tourId) throws NoSuchElementException {
        if (!tourRepository.existsById(tourId)) {
            throw new NoSuchElementException("Tour does not Exist " + tourId);
        }
    }

    private TourRating verifyTourRating(String tourId, int customerId) throws NoSuchElementException{
        return tourRatingRepository.findByTourIdAndCustomerId(tourId,customerId).orElseThrow(() -> new NoSuchElementException("Tour-Rating pair for request(" +tourId + " for customer " + customerId));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex){
        return ex.getMessage();
    }

}
