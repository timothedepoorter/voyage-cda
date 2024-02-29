package fr.timothe.voyage.ville;

import fr.timothe.voyage.exceptions.NotFoundException;
import fr.timothe.voyage.pays.Pays;
import fr.timothe.voyage.pays.PaysService;
import org.springframework.http.HttpStatus;

import fr.timothe.voyage.hebergement.Hebergement;
import fr.timothe.voyage.hebergement.HebergementService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class VilleService {

    private final VilleRepository villeRepository;
    private final HebergementService hebergementService;
    private final PaysService paysService;

    public VilleService(VilleRepository villeRepository, HebergementService hebergementService, PaysService paysService) {
        this.villeRepository = villeRepository;
        this.hebergementService = hebergementService;
        this.paysService = paysService;
    }

    public List<Ville> findAll(){
        return villeRepository.findAll();
    }

    public Ville findById(Integer id){
        return villeRepository.findById(id).orElseThrow(
                () -> new NotFoundException("La ville n'existe pas")
        );
    }

    public Ville save(Ville ville){
        return villeRepository.save(ville);
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
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aucune ville trouvée avec ce nom"
                )
        );
    }

    public Ville addPaysToVille(Integer id, Pays pays) {
        Ville ville = this.findById(id);
        pays = paysService.findById(pays.getId());
        ville.setPays(pays);
        this.save(ville);
        return ville;
    }
}
