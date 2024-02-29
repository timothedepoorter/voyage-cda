package fr.timothe.voyage.voyage;

import fr.timothe.voyage.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoyageService {
    private final VoyageRepository voyageRepository;
    public VoyageService(VoyageRepository voyageRepository) {
        this.voyageRepository = voyageRepository;
    }

    public List<Voyage> findAll() {
        return this.voyageRepository.findAll();
    }

    public Voyage findById(Integer id) {
        return this.voyageRepository.findById(id).orElseThrow(
                () -> new NotFoundException(
                        "Voyage " + id + " introuvable"
                )
        );
    }

    public void delete(Integer id) {
        this.findById(id);
        this.voyageRepository.deleteById(id);
    }

    public Voyage save(Voyage voyage) {
        return this.voyageRepository.save(voyage);
    }

    public Voyage update(Integer id, Voyage voyage) {
        this.findById(id);
        return this.voyageRepository.save(voyage);
    }
}
