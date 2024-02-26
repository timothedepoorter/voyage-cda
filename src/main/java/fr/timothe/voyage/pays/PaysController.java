package fr.timothe.voyage.pays;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/pays")
public class PaysController {
    private final PaysService paysService;

    public PaysController(PaysService paysService) {
        this.paysService = paysService;
    }
}
