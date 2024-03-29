package fr.timothe.voyage.vol;

import fr.timothe.voyage.ville.Ville;
import jakarta.persistence.*;
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
    private LocalDate dateAller;
    private LocalDate dateRetour;
    private double prix;

    @ManyToOne //Many = vols ToOne = ville
    @JoinColumn(name = "ville_id")
    private Ville ville;



}
