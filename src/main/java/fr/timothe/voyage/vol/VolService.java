package fr.timothe.voyage.vol;

import fr.timothe.voyage.exceptions.BadRequestException;
import fr.timothe.voyage.exceptions.NotFoundException;
import fr.timothe.voyage.ville.Ville;
import fr.timothe.voyage.vol.dto.PlaceRestanteVolDto;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class VolService {

    private final VolRepository volRepository;

    public VolService(VolRepository volRepository) {
        this.volRepository = volRepository;
    }


    public List<Vol> findAll() {
        return volRepository.findAll();
    }

    public Vol findById( Integer id) {
        return  volRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Aucun vol ne correspond")
        );
    }


    public Vol save(Vol vol) throws BadRequestException {

        verifyValuesVol(vol);

        return volRepository.save(vol);
    }

    private static void verifyValuesVol(Vol vol) {
        List<String> erreurs = new ArrayList<>();

        if (vol.getVille() == null) {
            erreurs.add("La ville de destination est obligatoire");
        }
        if (vol.getPrix() <= 0) {
            erreurs.add("C'est pas gratuit");
        }

        if (vol.getPrix() <= 50) {
            erreurs.add("A ce prix, le crash de l'avion est garanti");
        }

        if (vol.getPlacesTotales() == 0 ) {
            erreurs.add("Pas de vol si aucune places");
        }

        if (vol.getCompagnie() == null) {
            erreurs.add("Le vol doit appartenir à une compagnie");
        }
        if (vol.getDateAller().isBefore(LocalDate.now())) {
            erreurs.add("On ne peut pas retourner dans le passé");
        }

        if ((vol.getDateRetour().isBefore(vol.getDateAller()))) {
            erreurs.add("On ne peut pas arriver avant d'être parti");
        }

        if (!erreurs.isEmpty()) {
            throw new BadRequestException(erreurs);
        }


    }


    public Vol update(Vol vol) {
        return volRepository.save(vol);
    }


    public void deleteById(Integer id) {
        Vol vol = this.findById(id);
        volRepository.delete(vol);
    }

    /**
     * Recherche des vols en fonction de plusieurs critères de filtre.
     * @param ville la ville.
     * @param dateAller la date de départ du vol souhaité.
     * @param dateRetour la date retour souhaité.
     * @param prix le prix maximum souhaité.
     * @return Une liste d'objets Vols répondant aux critères de filtre.
     */
    public List<Vol> findAllByFilter(Ville ville, LocalDate dateAller, LocalDate dateRetour, Double prix) {
        Specification<Vol> spec = Specification.where(null);

        if (ville != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("ville"), ville));
        }
        if (dateAller != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("dateAller"), dateAller));
        }
        if (dateRetour != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("dateRetour"), dateRetour));
        }
        if (prix != null) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("prix"), prix));
        }

        return volRepository.findAll(spec);
    }

    /**
     * Effectue une réservation pour un vol.
     *
     * @param id l'id du vol.
     * @param nombrePersonnes le nombre de personne pour lesquelles la réservation est effectuée.
     */
    public void effectuerReservation(Integer id, int nombrePersonnes) {
        Optional<Vol> optionalVol = volRepository.findById(id);
        if (optionalVol.isPresent()) {
            Vol vol = optionalVol.get();
            int placesDisponibles = vol.getPlacesTotales() - nombrePersonnes;

            if (placesDisponibles >= 0) {
                vol.setPlacesTotales(placesDisponibles);
                volRepository.save(vol);
            } else {
                throw new IllegalArgumentException("Nombre de places insuffisant pour la réservation.");
            }
        } else {
            throw new NoSuchElementException("Aucun vol trouvé avec l'ID spécifié.");
        }
    }


    /**
     * Récupère le nombre de places restantes pour un vol spécifié.
     *
     * @param volId L'id du vol pour lequel on récupère les places restantes.
     * @return Une ResponseEntity contenant un DTO  indiquant l'id du vol et le nombre de places restantes.
     *         En cas d'échec, renvoie un message d'erreur avec le code HTTP approprié (404 pour Not Found).
     */
    public ResponseEntity<?> getPlaceRestante(Integer volId) {
        try {
            Vol vol = this.findById(volId);
            PlaceRestanteVolDto responseDto = new PlaceRestanteVolDto();
            responseDto.setVolId(vol.getId());
            responseDto.setPlacesRestantes(vol.getPlacesTotales());
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Aucun vol trouvé avec l'ID spécifié.");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
}
