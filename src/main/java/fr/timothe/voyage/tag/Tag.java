package fr.timothe.voyage.tag;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



import java.util.ArrayList;
import java.util.List;

import fr.timothe.voyage.hebergement.Hebergement;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Tag {
    @Id
    @GeneratedValue
    private Integer id;
    private String nom;

    @ManyToMany(mappedBy="tags")
    private List<Hebergement> hebergements = new ArrayList<>();


}