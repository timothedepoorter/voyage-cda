package fr.timothe.voyage.ville;
import fr.timothe.voyage.hebergement.Hebergement;
import fr.timothe.voyage.hebergement.HebergementService;
import org.springframework.stereotype.Service;

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
                () -> new IllegalArgumentException("La ville n'existe pas")
        );
    }



    public Ville save(Ville ville){
        return villeRepository.save(ville);
    }


    public Ville addHebergementToVille(Hebergement hebergement, Integer id){
        Ville ville = this.findById(id);
        hebergement = hebergementService.findById(hebergement.getId());
        ville.getHebergements().add(hebergement);
        return villeRepository.save(ville);

    }

    public Ville update(Ville ville){
        return villeRepository.save(ville);
    }

    public void deleteById(Integer id){
        Ville ville = this.findById(id);
        villeRepository.delete(ville);
    }
}
