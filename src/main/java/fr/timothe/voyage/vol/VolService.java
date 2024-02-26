package fr.timothe.voyage.vol;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolService {

    private final VolRepository volRepository;

    public VolService(VolRepository volRepository) {
        this.volRepository = volRepository;
    }


    public List<Vol> findAll() {
        return volRepository.findAll();
    }

    public Vol findById( Integer id) {
        return  volRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Aucun vol ne correspond")
        );
    }


    public Vol save(Vol vol) {
        return volRepository.save(vol);
    }


    public Vol update(Vol vol) {
        return volRepository.save(vol);
    }


    public void deleteById(Integer id) {
        Vol vol = this.findById(id);
        volRepository.delete(vol);
    }
}
