package fr.timothe.voyage.voyage;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/voyage")
public class VoyageController {
    private final VoyageService voyageService;

    public VoyageController(VoyageService voyageService) {
        this.voyageService = voyageService;
    }
}
