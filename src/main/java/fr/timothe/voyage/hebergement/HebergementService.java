package fr.timothe.voyage.hebergement;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HebergementService {
    private final HebergementRepository hebergementRepository;

    public HebergementService(HebergementRepository hebergementRepository) {
        this.hebergementRepository = hebergementRepository;
    }

    public List<Hebergement> findAll(){
        return hebergementRepository.findAll();
    }

    public Hebergement findById(Integer id){
        return hebergementRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("L'hébergement n'existe pas")
        );
    }

    public Hebergement save(Hebergement hebergement){
        return hebergementRepository.save(hebergement);
    }

    public Hebergement update(Hebergement hebergement){
        return hebergementRepository.save(hebergement);
    }

    public void deleteById(Integer id){
        Hebergement hebergement = this.findById(id);
        hebergementRepository.delete(hebergement);
    }

}
