package fr.timothe.voyage.hebergement;
import fr.timothe.voyage.ville.Ville;
import fr.timothe.voyage.ville.VilleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/hebergement")
public class HebergementController {

    private final HebergementService hebergementService;
    private final VilleService villeService;


    public HebergementController(HebergementService hebergementService, VilleService villeService){
        this.hebergementService = hebergementService;

        this.villeService = villeService;
    }

    //Create
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Hebergement save(@RequestBody Hebergement hebergement) {
        return hebergementService.save(hebergement);
    }

    //Read
    @GetMapping("/{id}")
    public Hebergement findById(@PathVariable Integer id){
        return hebergementService.findById(id);
    }

    //Update
    @PutMapping("/{id}")
    public Hebergement update(@RequestBody Hebergement hebergement){
        return hebergementService.update(hebergement);
    }

    //Delete
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        hebergementService.deleteById(id);
    }



//Chercher un hébergement par ...

//Destination
//http://localhost:8080/hebergement/findByVille?villeId=1
@GetMapping("/findByVille")
public List<Hebergement> findByVille(@RequestParam Integer villeId) {
    Ville ville = villeService.findById(villeId);
    return hebergementService.findByVille(ville);
}

//    @GetMapping("/searchByTag")
//    public ResponseEntity<List<Hebergement>> searchByTag(@RequestParam String tag) {
//        List<Hebergement> result = hebergementService.searchByTag(tag);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @GetMapping("/searchByTarif")
//    public ResponseEntity<List<Hebergement>> searchByTarif(@RequestParam double prix) {
//        List<Hebergement> result = hebergementService.searchByTarif(prix);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @GetMapping("/searchByDateVol")
//    public ResponseEntity<List<Hebergement>> searchByDateVol(@RequestParam String dateVol) {
//        LocalDate parsedDate = LocalDate.parse(dateVol);
//        List<Hebergement> result = hebergementService.searchByDateVol(parsedDate);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//    @GetMapping("/searchByPlacesDisponibles")
//    public ResponseEntity<List<Hebergement>> searchByPlacesDisponibles(@RequestParam Integer placesDisponibles) {
//        List<Hebergement> result = hebergementService.searchByPlacesDisponibles(placesDisponibles);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

}
