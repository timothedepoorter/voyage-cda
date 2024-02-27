package fr.timothe.voyage.pays;

import fr.timothe.voyage.ville.Ville;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "pays"
)
public class Pays {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String nom;

    @OneToMany(mappedBy = "pays")
    private List<Ville> villes= new ArrayList<>();


}
