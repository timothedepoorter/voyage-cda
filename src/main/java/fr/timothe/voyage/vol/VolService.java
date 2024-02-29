package fr.timothe.voyage.vol;

import fr.timothe.voyage.exceptions.NotFoundException;
import fr.timothe.voyage.ville.Ville;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
}
