package fr.timothe.voyage.voyage;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VoyageRepository extends JpaRepository<Voyage, Integer> {
}
