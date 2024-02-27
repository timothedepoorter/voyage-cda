package fr.timothe.voyage.hebergement;

import fr.timothe.voyage.tag.Tag;
import fr.timothe.voyage.ville.Ville;
import fr.timothe.voyage.vol.Vol;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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
    public int nombreEtoiles;
    private LocalDate dateArrivee;
    private LocalDate dateDepart;
    public double prix;

    @ManyToOne // Many hébergements to one ville
    @JoinColumn(name = "ville_id")
    public Ville ville;

    @ManyToMany
    @JoinTable(
            name = "tag_hebergement",
            joinColumns = @JoinColumn(name="hebergement_id"),
            inverseJoinColumns = @JoinColumn(name="tag_id")
    )
    private List<Tag> tags = new ArrayList<>();


}
