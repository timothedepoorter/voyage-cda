package fr.timothe.voyage.pays;
import org.springframework.stereotype.Service;

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

    public Pays save(Pays pays) {return paysRepository.save(pays);}

    public Pays update(Pays pays){
        return paysRepository.save(pays);
    }

    public void deleteById(Integer id){
        Pays pays = this.findById(id);
        paysRepository.delete(pays);
    }
}
