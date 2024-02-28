package fr.timothe.voyage.pays.dto;

import fr.timothe.voyage.ville.dto.VilleSansPaysDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaysCompletDto {

    private Integer id;
    private String nom;
    private List<VilleSansPaysDto> villes = new ArrayList<>();


}

