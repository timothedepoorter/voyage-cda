package fr.timothe.voyage.ville;

import fr.timothe.voyage.exceptions.BadRequestException;
import fr.timothe.voyage.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;

import fr.timothe.voyage.hebergement.Hebergement;
import fr.timothe.voyage.hebergement.HebergementService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
public class VilleService {

    private final VilleRepository villeRepository;
    private final HebergementService hebergementService;

    public VilleService(VilleRepository villeRepository, HebergementService hebergementService) {
        this.villeRepository = villeRepository;
        this.hebergementService = hebergementService;
    }

    public List<Ville> findAll(){
        return villeRepository.findAll();
    }

    public Ville findById(Integer id){
        return villeRepository.findById(id).orElseThrow(
                () -> new NotFoundException("La ville n'existe pas")
        );
    }


    public Ville save(Ville ville) throws BadRequestException {

        verifyValuesVille(ville);


        return villeRepository.save(ville);
    }

    private static void verifyValuesVille(Ville ville) {
        List<String> erreurs = new ArrayList<>();

        if (ville.getNom() == null) {
            erreurs.add("Une ville a forcément un nom");
        }

        if (ville.getPays() == null) {
            erreurs.add("La ville se trouve forcément dans un pays");
        }

        if (!erreurs.isEmpty()) {
            throw new BadRequestException(erreurs);
        }


    }


    public Ville addHebergementToVille( Integer id, Hebergement hebergement) {
        Ville ville = this.findById(id);
        hebergement = hebergementService.findById(hebergement.getId());
        ville.getHebergements().add(hebergement);
        hebergement.setVille(ville);
        this.save(ville);
        return ville;
    }


    public Ville update(Ville ville){
        return villeRepository.save(ville);
    }

    public void deleteById(Integer id){
        Ville ville = this.findById(id);
        villeRepository.delete(ville);
    }

    public Ville findVilleByNom(String nomVille) {
        return this.villeRepository.findVilleByNom(nomVille).orElseThrow(
                () -> new NotFoundException("Aucune ville trouvée avec ce nom")
        );
    }
}
