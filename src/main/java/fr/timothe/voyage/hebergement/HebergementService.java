package fr.timothe.voyage.hebergement;

import fr.timothe.voyage.ville.Ville;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HebergementService {

    private final HebergementRepository hebergementRepository;

    public HebergementService(HebergementRepository hebergementRepository) {
        this.hebergementRepository = hebergementRepository;
    }

    public List<Hebergement> findAll(){
        return hebergementRepository.findAll();
    }

    public Hebergement findById(Integer id){
        Optional<Hebergement> optionalHebergement = hebergementRepository.findById(id);
        if (optionalHebergement.isPresent()) {
            return optionalHebergement.get();
        } else {
            throw new NoSuchElementException("Aucun hébergement trouvé avec l'ID spécifié.");
        }
    }

    public Hebergement save(Hebergement hebergement){
        return hebergementRepository.save(hebergement);
    }

    public Hebergement update(Hebergement hebergement){
        return hebergementRepository.save(hebergement);
    }

    public List<Hebergement> FindAll() {
        return hebergementRepository.findAll();
    }
    public void deleteById(Integer id){
        Hebergement hebergement = this.findById(id);
        hebergementRepository.delete(hebergement);
    }


    public List<Hebergement> findAllByFilter(
            Ville ville,
            LocalDate dateArrivee,
            LocalDate dateDepart,
            Double prix,
            List<String> tags
    ) {
        Specification<Hebergement> spec = Specification.where(null);

        if (ville != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ville"), ville));
        }
        if (dateArrivee != null) {
            //greaterThanOrEqualTo (>=)  egal ou posterieur
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("dateArrivee"), dateArrivee));
        }
        if (dateDepart != null) {
            //lessThanOrEqualTo (<=) egal ou anterieur
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("dateDepart"), dateDepart));
        }
        if (prix != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("prix"), prix));
        }
        if (tags != null && !tags.isEmpty()) {
            //indique à JPA de faire une jointure
            spec = spec.and((root, query, criteriaBuilder) -> root.join("tags").get("nom").in(tags));
        }

        return hebergementRepository.findAll(spec);
    }

    public List<Hebergement> findByVille(Ville ville ) {
        List<Hebergement> result = hebergementRepository.findByVille(ville);

        if (result.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Aucun hébergement n'a été trouvé dans cette ville.");
        }

        return result;
    }

    public List<Hebergement> findByDateArriveeAndDateDepart(LocalDate dateArrivee, LocalDate dateDepart) {
        return hebergementRepository.findByDateArriveeAndDateDepart(dateArrivee, dateDepart);
    }

    public void effectuerReservation(Integer id, int nombrePersonnes) {
        Optional<Hebergement> optionalHebergement = hebergementRepository.findById(id);

        if (optionalHebergement.isPresent()) {
            Hebergement hebergement = optionalHebergement.get();
            int placesDisponibles = hebergement.getPlacesTotal() - nombrePersonnes;

            if (placesDisponibles >= 0) {
                hebergement.setPlacesTotal(placesDisponibles);
                hebergementRepository.save(hebergement);
            } else {
                throw new IllegalArgumentException("Nombre de places insuffisant pour la réservation.");
            }
        } else {
            throw new NoSuchElementException("Aucun hébergement trouvé avec l'ID spécifié.");
        }
    }
}


