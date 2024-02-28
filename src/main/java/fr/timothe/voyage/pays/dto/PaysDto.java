package fr.timothe.voyage.pays.dto;

import fr.timothe.voyage.ville.Ville;
import fr.timothe.voyage.ville.dto.VilleDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaysDto {
    private Integer id;
    private String nom;
}
