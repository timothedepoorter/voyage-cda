package fr.timothe.voyage.ville;

import fr.timothe.voyage.hebergement.Hebergement;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ville")
public class VilleController {
    private final VilleService villeService;

    public VilleController(VilleService villeService){
        this.villeService = villeService;
    }

    //Create
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Ville save(@RequestBody Ville ville) {
        return villeService.save(ville);
    }

    //Read
    @GetMapping
    public List<Ville> findAll(){
        return villeService.findAll();
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
