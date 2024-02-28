package fr.timothe.voyage.ville;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.timothe.voyage.hebergement.Hebergement;
import fr.timothe.voyage.ville.dto.VilleDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/villes")
public class VilleController {
    private final VilleService villeService;
    private final ObjectMapper objectMapper;

    public VilleController(VilleService villeService, ObjectMapper objectMapper){
        this.villeService = villeService;
        this.objectMapper = objectMapper;
    }

    //Create
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Ville save(@RequestBody Ville ville) {
        return villeService.save(ville);
    }

    //Read
    @GetMapping
    public List<VilleDto> findAll(){
        return villeService.findAll().stream().map(
                ville -> objectMapper.convertValue(ville, VilleDto.class)
        ).toList();
    }

    @GetMapping("/{id}")
    public Ville findById(@PathVariable Integer id){
        return villeService.findById(id);
    }


    //Update
    @PutMapping("/{id}")
    public Ville update(@RequestBody Ville ville){
        return villeService.update(ville);
    }


    //Delete
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        villeService.deleteById(id);
    }

    @PostMapping("/{id}/hebergements")
    public Hebergement addHebergementToVille(@PathVariable Integer id, @RequestBody Hebergement hebergement) {
        this.villeService.addHebergementToVille(hebergement, id);
        return hebergement;
    }
}
