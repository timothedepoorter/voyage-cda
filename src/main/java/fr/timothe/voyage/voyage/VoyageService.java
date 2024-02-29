package fr.timothe.voyage.voyage;

import fr.timothe.voyage.exceptions.NotFoundException;
import fr.timothe.voyage.hebergement.HebergementService;
import fr.timothe.voyage.vol.VolService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoyageService {
    private final VoyageRepository voyageRepository;
    private final VolService volService;
    private final HebergementService hebergementService;
    public VoyageService(VoyageRepository voyageRepository, VolService volService, HebergementService hebergementService) {
        this.voyageRepository = voyageRepository;
        this.volService = volService;
        this.hebergementService = hebergementService;
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
        this.volService.effectuerReservation(voyage.getVol().getId(), voyage.getNbVoyageur());
        this.hebergementService.effectuerReservation(voyage.getHebergement().getId(), voyage.getNbVoyageur());
        return this.voyageRepository.save(voyage);
    }

    public Voyage update(Integer id, Voyage voyage) {
        this.findById(id);
        return this.voyageRepository.save(voyage);
    }
}
