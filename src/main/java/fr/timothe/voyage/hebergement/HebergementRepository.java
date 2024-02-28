package fr.timothe.voyage.hebergement;
import fr.timothe.voyage.ville.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HebergementRepository extends JpaRepository<Hebergement, Integer>, JpaSpecificationExecutor<Hebergement> {
   List<Hebergement> findByVille(Ville ville);
    List<Hebergement> findByDateArriveeAndDateDepart(LocalDate dateArrivee, LocalDate dateDepart);

    Optional<List<Hebergement>>
    findAllByVilleAndDateArriveeIsGreaterThanEqualAndDateDepartIsLessThanEqualAndPrixIsLessThanEqual (
            Ville ville,
            LocalDate dateArrivee,
            LocalDate dateDepart,
            Double prix
    );
}