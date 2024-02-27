package fr.timothe.voyage.ville;

import fr.timothe.voyage.pays.Pays;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ville {
    @Id
    @GeneratedValue
    public Integer id;
    public String nom;

    @ManyToOne // Many villes to one pays
    @JoinColumn(name = "pays_id")
    public Pays pays;
}
