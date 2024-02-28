package fr.timothe.voyage.vol;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.timothe.voyage.ville.Ville;
import fr.timothe.voyage.ville.VilleService;
import fr.timothe.voyage.vol.dto.VolDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vols")
public class VolController {
    private final VolService volService;
    private final VilleService villeService;
    private ObjectMapper objectMapper;

    public VolController(VolService volService, VilleService villeService, ObjectMapper objectMapper) {
        this.volService = volService;
        this.villeService = villeService;
        this.objectMapper = objectMapper;
    }

    //GET
    @GetMapping
    public List<VolDto> findAll() {
        return volService.findAll().stream().map(
                vol -> objectMapper.convertValue(vol, VolDto.class)
        ).toList();
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
            @RequestParam(name = "ville", required = false) String nom,
            @RequestParam(name = "dateAller", required = false) LocalDate dateAller,
            @RequestParam(name = "dateRetour", required = false) LocalDate dateRetour,
            @RequestParam(name = "prix", required = false) Double prix
    ) {
        Ville ville = (nom != null) ? this.villeService.findVilleByNom(nom) : null;
        return this.volService.findAllByFilter(ville, dateAller, dateRetour, prix);
    }
}
