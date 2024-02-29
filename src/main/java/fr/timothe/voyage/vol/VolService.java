package fr.timothe.voyage.vol;

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
                () -> new IllegalArgumentException("Aucun vol ne correspond")
        );
    }


    public Vol save(Vol vol) {
        return volRepository.save(vol);
    }


    public Vol update(Vol vol) {
        return volRepository.save(vol);
    }


    public void deleteById(Integer id) {
        Vol vol = this.findById(id);
        volRepository.delete(vol);
    }

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
