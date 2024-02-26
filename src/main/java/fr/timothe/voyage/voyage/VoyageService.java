package fr.timothe.voyage.voyage;

import org.springframework.stereotype.Service;

@Service
public class VoyageService {
    private final VoyageRepository voyageRepository;
    public VoyageService(VoyageRepository voyageRepository) {
        this.voyageRepository = voyageRepository;
    }
}
