package io.justice.ec.domiain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
/*@Data*/
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class TourPackage {
    @Id
    private String code;

    @Column
    private String name;


}
