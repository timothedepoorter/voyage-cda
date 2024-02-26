package fr.timothe.voyage.pays;

import org.springframework.stereotype.Service;

@Service
public class PaysService {
    private final PaysRepository paysRepository;

    public PaysService(PaysRepository paysRepository) {
        this.paysRepository = paysRepository;
    }
}
