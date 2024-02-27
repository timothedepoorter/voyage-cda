package fr.timothe.voyage.hebergement;

import fr.timothe.voyage.ville.Ville;
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

    public void deleteById(Integer id){
        Hebergement hebergement = this.findById(id);
        hebergementRepository.delete(hebergement);
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
public List<Hebergement> findByDisponibilite(LocalDate dateDepart, LocalDate dateRetour) {
    return hebergementRepository.findBetweenAllerRetour(dateRetour, dateDepart);
}

}
