package fr.timothe.voyage.hebergement;

import fr.timothe.voyage.ville.Ville;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;

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
        return hebergementRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("L'hébergement n'existe pas")
        );
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

}
