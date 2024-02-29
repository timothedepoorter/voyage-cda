package fr.timothe.voyage.hebergement;

import fr.timothe.voyage.exceptions.BadRequestException;
import fr.timothe.voyage.exceptions.NotFoundException;
import fr.timothe.voyage.ville.Ville;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


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
            throw new NotFoundException("Aucun hébergement trouvé avec l'ID spécifié.");
        }
    }

    public Hebergement save(Hebergement hebergement) throws BadRequestException {

        verifyValuesHebergement(hebergement);

        return hebergementRepository.save(hebergement);
    }

    private static void verifyValuesHebergement(Hebergement hebergement) {
        List<String> erreurs = new ArrayList<>();

        if (hebergement.getNom() == null) {
            erreurs.add("Le nom de logement est obligatoire");
        }

        if (hebergement.getVille() == null) {
            erreurs.add("L'hébergement se situe forcément dans une ville");
        }

        if (hebergement.getPrix() <= 0) {
            erreurs.add("C'est pas gratuit");
        }

        if (hebergement.getNombreEtoiles() < 0) {
            erreurs.add("L'hébergement ne peut pas avoir moins de 0 étoiles");
        }

        if (hebergement.getDateDepart().isBefore(LocalDate.now())) {
            erreurs.add("On ne peut pas retourner dans le passé");
        }

        if ((hebergement.getDateArrivee().isAfter(hebergement.getDateDepart()))) {
            erreurs.add("On ne peut pas arriver avant d'être parti");
        }

        if (!erreurs.isEmpty()) {
            throw new BadRequestException(erreurs);
        }
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


