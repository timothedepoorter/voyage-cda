package fr.timothe.voyage.vol;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor

@Entity
public class Vol {
    @Id
    @GeneratedValue
    private Integer id;
    private String compagnie;
    private int placesTotales;
    private int placesDisponibles;
    private LocalDate dateDepart;
    private LocalDate dateArrive;
    private double prix;

//    private Ville villeDepart;
//    private Ville villeArrive;

}
