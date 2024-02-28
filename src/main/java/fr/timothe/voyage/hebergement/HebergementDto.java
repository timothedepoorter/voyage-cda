package fr.timothe.voyage.hebergement;

import fr.timothe.voyage.tag.TagDto;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class HebergementDto {

        private Integer id;
        private String nom;
        private List<TagDto> tags;

}
