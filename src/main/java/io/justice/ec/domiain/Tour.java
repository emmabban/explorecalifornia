package io.justice.ec.domiain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;


/*@Data*/
@RequiredArgsConstructor
@NoArgsConstructor
@Document
@Data
public class Tour {

    @Id
    private String id;

    @NonNull
    @Indexed
    private String title;

    @Indexed
    @NonNull
    private String tourPackageCode;

    @NonNull
    private String tourPackageName;

    @NonNull
    private Map<String, String> details;

    public Tour(@NonNull String title, @NonNull TourPackage  tourPackage, @NonNull Map<String, String> details) {
        this.title = title;
        this.tourPackageCode = tourPackage.getCode();
        this.tourPackageName = tourPackage.getName();
        this.details = details;
    }
}
