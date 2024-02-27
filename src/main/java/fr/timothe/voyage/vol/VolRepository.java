package fr.timothe.voyage.vol;

import fr.timothe.voyage.ville.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VolRepository extends JpaRepository<Vol, Integer> {
    public Optional<List<Vol>> findAllByVille(
            Ville ville
    );
}
