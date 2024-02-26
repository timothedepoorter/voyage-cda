package fr.timothe.voyage.voyage;

import fr.timothe.voyage.hebergement.Hebergement;
import fr.timothe.voyage.vol.Vol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name = "voyage"
)
public class Voyage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Float tarifTotal;

    private String nomClient;

    private Integer nbVoyageur;

    @ManyToOne
    @JoinColumn(name = "vol_id")
    private Vol vol;

    @ManyToOne
    @JoinColumn(name = "hebergement_id")
    private Hebergement hebergement;
}
