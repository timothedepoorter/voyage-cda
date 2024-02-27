package fr.timothe.voyage.hebergement;

import fr.timothe.voyage.ville.Ville;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface HebergementRepository extends JpaRepository<Hebergement, Integer> {
    List<Hebergement> findByVille(Ville ville);
    List<Hebergement> findBetweenAllerRetour(LocalDate dateRetour, LocalDate dateDepart);

//    List<Hebergement> findByTags(String tag);
//    List<Hebergement> findByTarif(double prix);
//    List<Hebergement> findByPlacesDisponibles(int placesTotal);


}
