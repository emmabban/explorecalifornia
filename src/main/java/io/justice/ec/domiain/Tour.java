package io.justice.ec.domiain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

/*@Data*/
@RequiredArgsConstructor
@Entity
public class Tour {

    @Id
    @GeneratedValue
    private Integer id;

    @NonNull
    @Column
    private String title;

    @NonNull
    @Column(length = 2000)
    private String description;

    @NonNull
    @Column(length = 2000)
    private String blurb;

    @NonNull
    @Column
    private Integer price;

    @NonNull
    @Column
    private String duration;

    @NonNull
    @Column(length = 2000)
    private String bullets;

    @NonNull
    @Column
    private String keywords;

    @NonNull
    @ManyToOne
    private TourPackage tourPackage;

    @NonNull
    @Column
    @Enumerated
    private Difficulty difficulty;

    /*@NonNull*/
    @Column
    @Enumerated
    private Region region;


}
