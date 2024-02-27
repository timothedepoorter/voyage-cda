package fr.timothe.voyage.voyage;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
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
