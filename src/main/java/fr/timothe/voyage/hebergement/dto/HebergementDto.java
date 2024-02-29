package fr.timothe.voyage.hebergement.dto;

import fr.timothe.voyage.tag.dto.TagDto;
import fr.timothe.voyage.ville.dto.VilleDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class HebergementDto {

        private Integer id;
        private String nom;
        public int placesTotal;
        public int nombreEtoiles;
        private LocalDate dateArrivee;
        private LocalDate dateDepart;
        public double prix;
        public VilleDto ville;
     private List<TagDto> tags = new ArrayList<>();

}
