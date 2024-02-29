package fr.timothe.voyage.hebergement;

import fr.timothe.voyage.exceptions.BadRequestException;
import fr.timothe.voyage.exceptions.NotFoundException;
import fr.timothe.voyage.ville.Ville;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
                () -> new NotFoundException("L'hébergement n'existe pas")
        );
    }

    public Hebergement save(Hebergement hebergement) throws BadRequestException {

        verifyDataHebergement(hebergement);

        return hebergementRepository.save(hebergement);
    }

    private static void verifyDataHebergement(Hebergement hebergement) {
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

//    public List<Hebergement> findAllByFilter(Ville ville, LocalDate dateArrivee, LocalDate dateDepart, Double prix) {
//        return this.hebergementRepository.findAllByVilleAndDateArriveeIsGreaterThanEqualAndDateDepartIsLessThanEqualAndPrixIsLessThanEqual(
//                ville,
//                dateArrivee,
//                dateDepart,
//                prix
//        ).orElseThrow(
//                () -> new ResponseStatusException(
//                        HttpStatus.NOT_FOUND,
//                        "Aucun résultat pour votre recherche"
//                )
//        );
//    }

    public List<Hebergement> findAllByFilter(Ville ville, LocalDate dateArrivee, LocalDate dateDepart, Double prix) {
        Specification<Hebergement> spec = Specification.where(null);

        if (ville != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ville"), ville));
        }
        if (dateArrivee != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("dateArrivee"), dateArrivee));
        }
        if (dateDepart != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("dateDepart"), dateDepart));
        }
        if (prix != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("prix"), prix));
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



//    public List<Hebergement> searchByTag(String tag) {
//        return hebergementRepository.findByTags(tag);
//    }
//
//    public List<Hebergement> searchByTarif( double prix) {
//        return hebergementRepository.findByTarif(prix);
//    }
//
//    public List<Hebergement> searchByPlacesDisponibles(Integer placesDisponibles) {
//        return hebergementRepository.findByPlacesDisponibles(placesDisponibles);
//    }
//
}
