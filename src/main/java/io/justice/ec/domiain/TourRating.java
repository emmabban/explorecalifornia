package io.justice.ec.domiain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Document
@Data
@RequiredArgsConstructor
public class TourRating {

    @Id
    private String id;

    @NonNull
    private String tourId;

    @NotNull
    @NonNull
    private Integer customerId;

    @Min(0)
    @Max(5)
    @NonNull
    private Integer score;

    @Size(max = 255)
    @NonNull
    private String comment;


}
