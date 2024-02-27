package fr.timothe.voyage.vol;

import fr.timothe.voyage.ville.Ville;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VolRepository extends JpaRepository<Vol, Integer> {
    public Optional<List<Vol>> findAllByVilleAndDateAllerIsGreaterThanEqualAndDateRetourIsLessThanEqual(
            Ville ville,
            LocalDate dateAller,
            LocalDate dateRetour
    );
}
