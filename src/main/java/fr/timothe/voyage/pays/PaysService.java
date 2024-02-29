package fr.timothe.voyage.pays;
import com.sun.net.httpserver.SimpleFileServer;
import fr.timothe.voyage.exceptions.BadRequestException;
import fr.timothe.voyage.exceptions.NotFoundException;
import fr.timothe.voyage.pays.dto.PaysCompletDto;
import fr.timothe.voyage.ville.Ville;
import fr.timothe.voyage.ville.VilleRepository;
import fr.timothe.voyage.ville.VilleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public Pays save(Pays pays) throws BadRequestException {
        verifyDataPays(pays);

        return paysRepository.save(pays);
    }

    /**
     * Vérifie les données d'un Pays pour s'assurer de la présence du nom du pays.
     *
     * @param pays Le pays dont les données doivent etre vérifié.
     * @throws BadRequestException Si des erreurs sont détectées dans les données du pays, une exception BadRequestException est levée.
     */
    private static void verifyDataPays(Pays pays) {
        List<String> erreurs = new ArrayList<>();

        if (pays.getNom() == null) {
            erreurs.add("Le nom du pays est obligatoire");
        }

        if (!erreurs.isEmpty()) {
            throw new BadRequestException(erreurs);
        }

    }


    /**
     * Ajoute une ville à un pays.
     *
     * @param paysId L'id du pays auquel ajouter la ville.
     * @param ville La Ville à ajouter au pays.
     * @return Le pays mis à jour après l'ajout de la ville.
     * @throws NotFoundException Si aucun pays n'est trouvé avec l'ID spécifié, une exception est levée.
     */
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
