package fr.timothe.voyage.pays;

import fr.timothe.voyage.exceptions.BadRequestException;
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

    public Pays update(Pays pays){
        return paysRepository.save(pays);
    }

    public void deleteById(Integer id){
        Pays pays = this.findById(id);
        paysRepository.delete(pays);
    }
}
