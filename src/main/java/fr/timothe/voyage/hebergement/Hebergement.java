package fr.timothe.voyage.hebergement;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

//    public Ville ville
//    private List<Tag> tags = new ArrayList<>();


}
