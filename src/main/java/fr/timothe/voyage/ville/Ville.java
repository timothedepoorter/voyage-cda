package fr.timothe.voyage.ville;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import fr.timothe.voyage.hebergement.Hebergement;
import fr.timothe.voyage.pays.Pays;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Ville {
    @Id
    @GeneratedValue
    public Integer id;
    public String nom;

    @ManyToOne // Many villes to one pays
    @JoinColumn(name = "pays_id")
    public Pays pays;

    @OneToMany(mappedBy = "ville", cascade = CascadeType.ALL)
    private List<Hebergement> hebergements = new ArrayList<>();
}
