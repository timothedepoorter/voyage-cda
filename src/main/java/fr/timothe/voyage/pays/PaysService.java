package fr.timothe.voyage.pays;

import fr.timothe.voyage.exceptions.BadRequestException;
import fr.timothe.voyage.exceptions.NotFoundException;
import fr.timothe.voyage.ville.Ville;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaysService {
    private final PaysRepository paysRepository;

    public PaysService(PaysRepository paysRepository) {
        this.paysRepository = paysRepository;
    }

    public List<Pays> findAll() {
        return paysRepository.findAll();
    }

    public Pays findById(Integer id) {

        return paysRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Le pays n'existe pas")
        );
    }

    public Pays save(Pays pays) throws BadRequestException {
        verifyDataPays(pays);

        return paysRepository.save(pays);
    }

    private static void verifyDataPays(Pays pays) {
        List<String> erreurs = new ArrayList<>();

        if (pays.getNom() == null) {
            erreurs.add("Le nom du pays est obligatoire");
        }

        if (!erreurs.isEmpty()) {
            throw new BadRequestException(erreurs);
        }

    }


    public Pays addVilleToPays(Integer paysId, Ville ville){
        Pays pays = paysRepository.findById(paysId)
                .orElseThrow(() -> new NotFoundException("Aucun pays trouvé avec l'ID spécifié."));

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
