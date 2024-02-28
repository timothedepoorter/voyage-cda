package fr.timothe.voyage.hebergement;
import fr.timothe.voyage.ville.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface HebergementRepository extends JpaRepository<Hebergement, Integer> {
   List<Hebergement> findByVille(Ville ville);
    List<Hebergement> findByDateArriveeAndDateDepart(LocalDate dateArrivee, LocalDate dateDepart);
}