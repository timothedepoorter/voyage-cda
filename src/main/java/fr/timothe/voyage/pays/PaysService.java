package fr.timothe.voyage.pays;
import fr.timothe.voyage.pays.dto.PaysCompletDto;
import fr.timothe.voyage.ville.Ville;
import fr.timothe.voyage.ville.VilleRepository;
import fr.timothe.voyage.ville.VilleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PaysService {
    private final PaysRepository paysRepository;

    private final VilleService villeService;
    private final VilleRepository villeRepository;

    public PaysService(PaysRepository paysRepository, VilleService villeService, VilleRepository villeRepository) {
        this.paysRepository = paysRepository;
        this.villeService = villeService;
        this.villeRepository = villeRepository;
    }

    public List<Pays> findAll() {
        return paysRepository.findAll();
    }

    public Pays findById(Integer id) {

        return paysRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Le pays n'existe pas")
        );
    }

    public Pays save(Pays pays) {
        return paysRepository.save(pays);
    }


    public Pays addVilleToPays(Integer paysId, Ville ville){
        Pays pays = paysRepository.findById(paysId)
                .orElseThrow(() -> new NoSuchElementException("Aucun pays trouvé avec l'ID spécifié."));

        ville.setPays(pays);
        return this.save(pays);

//        Pays pays = this.findById(id);
//        ville = villeService.findById(ville.getId());
//
//        pays.getVilles().add(ville);
//        return this.save(pays);
    }



    public Pays update(Pays pays){
        return paysRepository.save(pays);
    }

    public void deleteById(Integer id){
        Pays pays = this.findById(id);
        paysRepository.delete(pays);
    }
}
