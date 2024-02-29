package fr.timothe.voyage.ville.dto;

import fr.timothe.voyage.hebergement.dto.HebergementSansVilleDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VilleCompletDto {

    private Integer id;
    private String nom;
    private List<HebergementSansVilleDto> hebergements = new ArrayList<>();
}
