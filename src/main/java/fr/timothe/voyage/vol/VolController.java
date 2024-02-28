package fr.timothe.voyage.vol;

import fr.timothe.voyage.ville.Ville;
import fr.timothe.voyage.ville.VilleService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vols")
public class VolController {
    private final VolService volService;
    private final VilleService villeService;

    public VolController(VolService volService, VilleService villeService) {
        this.volService = volService;
        this.villeService = villeService;
    }

    //GET
    @GetMapping
    public List<Vol> findAll() {
        return volService.findAll();
    }

    @GetMapping("/{id}")
    public Vol findById(@PathVariable Integer id) {
        return volService.findById(id);
    }

    //POST
    @PostMapping
    public Vol save(@RequestBody Vol vol) {
        return volService.save(vol);
    }

    //PUT
    @PutMapping("/{id}")
    public Vol update(@RequestBody Vol vol) {
        return volService.update(vol);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        volService.deleteById(id);
    }

    @GetMapping("/filterBy")
    public List<Vol> findAllByFilter(
            @RequestParam(name = "ville") String nom,
            @RequestParam(name = "dateAller") LocalDate dateAller,
            @RequestParam(name = "dateRetour") LocalDate dateRetour,
            @RequestParam(name = "prix") Double prix
    ) {
        Ville ville = this.villeService.findVilleByNom(nom);
        return this.volService.findAllByFilter(ville, dateAller, dateRetour, prix);
    }
}
