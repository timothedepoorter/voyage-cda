package fr.timothe.voyage.hebergement;

import fr.timothe.voyage.ville.Ville;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Hebergement {
    @Id
    @GeneratedValue
    public Integer id;
    public String nom;
    public int placesTotal;
    public int placesDisponibles;
    public int nombreEtoiles;
    public double prix;

    @ManyToOne // Many hébergements to one ville
    @JoinColumn(name = "ville_id")
    public Ville ville;

//    private List<Tag> tags = new ArrayList<>();


}
