package fr.timothe.voyage.hebergement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationHebergementDto {
    private Integer hebergementId;
    private int nombrePersonnes;
    private int placesRestantes;

}
