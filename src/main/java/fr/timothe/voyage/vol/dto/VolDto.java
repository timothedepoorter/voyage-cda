package fr.timothe.voyage.vol.dto;

import fr.timothe.voyage.ville.dto.VilleDto;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VolDto {
    private Integer id;
    private String compagnie;
    private int placesTotales;
    private LocalDate dateAller;
    private LocalDate dateRetour;
    private double prix;
    private VilleDto ville;
}
