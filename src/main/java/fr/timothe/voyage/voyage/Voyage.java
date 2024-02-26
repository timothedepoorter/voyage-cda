package fr.timothe.voyage.voyage;

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
}
