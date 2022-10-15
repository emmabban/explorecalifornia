package io.justice.ec.domiain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/*@Data*/
@NoArgsConstructor
@AllArgsConstructor
@Document
@Data
public class TourPackage {
    @Id
    private String code;

    private String name;


}
