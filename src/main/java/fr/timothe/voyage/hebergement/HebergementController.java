package fr.timothe.voyage.hebergement;
import fr.timothe.voyage.ville.Ville;
import fr.timothe.voyage.ville.VilleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path="/hebergements")
public class HebergementController {

    private final HebergementService hebergementService;
    private final VilleService villeService;


    public HebergementController(HebergementService hebergementService, VilleService villeService) {
        this.hebergementService = hebergementService;
        this.villeService = villeService;
    }

    //Create
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Hebergement save(@RequestBody Hebergement hebergement) {
        return hebergementService.save(hebergement);
    }

    // Liste tous les hébergements
    @GetMapping
    public List<Hebergement> findAll() {
        return hebergementService.FindAll();
    }
    //Read
    @GetMapping("/{id}")
    public Hebergement findById(@PathVariable Integer id) {
        return hebergementService.findById(id);
    }

    //Update
    @PutMapping("/{id}")
    public Hebergement update(@RequestBody Hebergement hebergement) {
        return hebergementService.update(hebergement);
    }

    //Delete
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        hebergementService.deleteById(id);
    }

    //Chercher un hébergement par ...

    @GetMapping("/filterBy")
    public List<Hebergement> findAllByFilter(
            @RequestParam(name = "ville",required = false) String nom,
            @RequestParam(name = "dateArrivee",required = false) LocalDate dateArrivee,
            @RequestParam(name = "dateDepart",required = false) LocalDate dateDepart,
            @RequestParam(name = "prix",required = false) Double prix
    ) {
        Ville ville = (nom != null) ? this.villeService.findVilleByNom(nom) : null;
        return this.hebergementService.findAllByFilter(ville, dateArrivee, dateDepart, prix);
    }

    //Destination
    //http://localhost:8080/hebergements/findByVille?villeId=1
    @GetMapping("/findByVille")
    public List<Hebergement> findByVille(@RequestParam Integer villeId) {
        Ville ville = villeService.findById(villeId);
        return hebergementService.findByVille(ville);
    }

    //Date
    //http://localhost:8080/hebergements/findByDate?dateArrivee=2024-03-01&dateDepart=2024-03-10
    @GetMapping("/findByDate")
    public ResponseEntity<List<Hebergement>> findByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateArrivee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDepart) {
        List<Hebergement> result = hebergementService.findByDateArriveeAndDateDepart(dateArrivee, dateDepart);
        return new ResponseEntity<>(result, HttpStatus.OK);
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
//    @GetMapping("/searchByPlacesDisponibles")
//    public ResponseEntity<List<Hebergement>> searchByPlacesDisponibles(@RequestParam Integer placesDisponibles) {
//        List<Hebergement> result = hebergementService.searchByPlacesDisponibles(placesDisponibles);
//        return new ResponseEntity<>(result, HttpStatus.OK);
//    }

}
