package fr.timothe.voyage.voyage;

import fr.timothe.voyage.exceptions.BadRequestException;
import fr.timothe.voyage.exceptions.NotFoundException;
import fr.timothe.voyage.hebergement.HebergementService;
import fr.timothe.voyage.vol.VolService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Voyage save(Voyage voyage) throws BadRequestException {

        this.volService.effectuerReservation(voyage.getVol().getId(), voyage.getNbVoyageur());
        this.hebergementService.effectuerReservation(voyage.getHebergement().getId(), voyage.getNbVoyageur());

        verifyValuesVoyage(voyage);

        return this.voyageRepository.save(voyage);
    }

    private static void verifyValuesVoyage(Voyage voyage) {
        List<String> erreurs = new ArrayList<>();

        if (voyage.getHebergement() == null) {
            erreurs.add("Le client ne va pas dormir dehors");
        }

        if (voyage.getVol() == null ) {
            erreurs.add("Notre agence ne se soucie pas de l'impact carbone, le choix du vol est obligatoire");
        }

        if (voyage.getNbJour() <= 0) {
            erreurs.add("Pas de jour, pas de voyage");
        }

        if (voyage.getNomClient() == null) {
            erreurs.add("Le nom du client est obligatoire");
        }

        if (voyage.getNbVoyageur() <= 0) {
            erreurs.add("Pas de voyageur, pas de voyage");
        }

        if (voyage.getTarifTotal() <= 0) {
            erreurs.add("Je crains que ça ne va pas être possible");
        }

        if (voyage.getTarifTotal().isNaN()) {
            erreurs.add("Les pièces en chocolat ne sont pas acceptés");
        }

        if (!erreurs.isEmpty()) {
            throw new BadRequestException(erreurs);
        }
    }

    public Voyage update(Integer id, Voyage voyage) {
        this.findById(id);
        return this.voyageRepository.save(voyage);
    }
}
