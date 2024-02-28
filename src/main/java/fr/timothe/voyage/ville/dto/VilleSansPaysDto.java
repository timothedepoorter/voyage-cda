package fr.timothe.voyage.ville.dto;

import fr.timothe.voyage.hebergement.Hebergement;
import lombok.Data;

import java.util.List;

@Data
public class VilleSansPaysDto {
    public Integer id;
    public String nom;
    private List<Hebergement> hebergements;
}
