package fr.timothe.voyage.pays;
import fr.timothe.voyage.ville.Ville;
import fr.timothe.voyage.ville.VilleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaysService {
    private final PaysRepository paysRepository;
    private final VilleService villeService;

    public PaysService(PaysRepository paysRepository, VilleService villeService) {
        this.paysRepository = paysRepository;
        this.villeService = villeService;
    }

    public List<Pays> findAll() {
        return paysRepository.findAll();
    }

    public Pays findById(Integer id) {
        return paysRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Le pays n'existe pas")
        );
    }

    public Pays save(Pays pays) {return paysRepository.save(pays);}


    public Pays addVilleToPays(Ville ville, Integer id){
        Pays pays = this.findById(id);
        ville = villeService.findById(ville.getId());
        pays.getVilles().add(ville);
        return paysRepository.save(pays);

    }

    public Pays update(Pays pays){
        return paysRepository.save(pays);
    }

    public void deleteById(Integer id){
        Pays pays = this.findById(id);
        paysRepository.delete(pays);
    }
}
