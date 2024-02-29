package fr.timothe.voyage.hebergement;
import fr.timothe.voyage.ville.Ville;
import fr.timothe.voyage.ville.VilleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/hebergements")

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
            @RequestParam(name = "prix",required = false) Double prix,
            @RequestParam(name = "tags", required = false) List<String> tags
    ) {
        Ville ville = (nom != null) ? this.villeService.findVilleByNom(nom) : null;
        return this.hebergementService.findAllByFilter(ville, dateArrivee, dateDepart, prix, tags);
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

    @PostMapping("/reservation")
    public ResponseEntity<?> effectuerReservation(@RequestBody ReservationHebergementDto reservationDto) {
        try {
            hebergementService.effectuerReservation(reservationDto.getHebergementId(), reservationDto.getNombrePersonnes());
            String successMessage = String.format("Réservation effectuée avec succès pour %d personne(s).", reservationDto.getNombrePersonnes());
            Map<String, String> successResponse = new HashMap<>();
            successResponse.put("message", successMessage);

            return new ResponseEntity<>(successResponse, HttpStatus.OK);

        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Nombre de places insuffisant pour la réservation.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Aucun hébergement trouvé avec l'ID spécifié.");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getPlaceRestante/{hebergementId}")
    public ResponseEntity<?> getPlaceRestante(@PathVariable Integer hebergementId) {
        try {
            Hebergement hebergement = hebergementService.findById(hebergementId);
            PlaceRestanteHebergementDto responseDto = new PlaceRestanteHebergementDto();
            responseDto.setHebergementId(hebergement.getId());
            responseDto.setPlacesRestantes(hebergement.getPlacesTotal());

            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Aucun hébergement trouvé avec l'ID spécifié.");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }

}
