package fr.timothe.voyage.ville;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class VilleService {

    private final VilleRepository villeRepository;

    public VilleService(VilleRepository villeRepository) {
        this.villeRepository = villeRepository;
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
}
