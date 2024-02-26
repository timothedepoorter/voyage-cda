package fr.timothe.voyage.hebergement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HebergementRepository extends JpaRepository<Hebergement, Integer> {
    Optional<List<Hebergement>> findAllByVilleId(Integer id);

}
