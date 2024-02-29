package fr.timothe.voyage.hebergement;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.timothe.voyage.hebergement.dto.HebergementDto;
import fr.timothe.voyage.hebergement.dto.PlaceRestanteHebergementDto;
import fr.timothe.voyage.hebergement.dto.ReservationHebergementDto;
import fr.timothe.voyage.ville.Ville;
import fr.timothe.voyage.ville.VilleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    private ObjectMapper objectMapper;
    public HebergementController(HebergementService hebergementService, VilleService villeService,ObjectMapper objectMapper) {
        this.hebergementService = hebergementService;
        this.villeService = villeService;
        this.objectMapper = objectMapper;
    }

    /**
     * CREATE
     * @param hebergement
     * @return un nouvel hebergement
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Hebergement save(@RequestBody Hebergement hebergement) {
        return hebergementService.save(hebergement);
    }

    /**
     * GET
     * @return une liste d'hebergements.
     */
    @GetMapping
    public List<HebergementDto> findAll() {
        return hebergementService.FindAll().stream().map(
                hebergement -> objectMapper.convertValue(hebergement, HebergementDto.class)
        ).toList();
    }

    /**
     * GET
     * @param id identifiant unique d'un hebergement
     * @return l'hebergement qui possède l'id spécifié.
     */
    @GetMapping("/{id}")
    public HebergementDto findById(@PathVariable Integer id) {

        return objectMapper.convertValue( hebergementService.findById(id), HebergementDto.class);
    }


    @PutMapping("/{id}")
    public Hebergement update(@RequestBody Hebergement hebergement) {
        return hebergementService.update(hebergement);
    }

    /**
     * DELETE un hebergement
     * @param id identifiant unique de l'hebergement pour supprimé l'hebergement associé.
     */
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        hebergementService.deleteById(id);
    }

    /**
     * Recherche des hébergements en fonction de plusieurs critères de filtre,
     * ou les paramètres sont facultatif.
     * @param nom Nom de la ville.
     * @param dateArrivee La date d'arrivée.
     * @param dateDepart La date de départ.
     * @param prix Le prix maximum souhaité.
     * @param tags La liste des tags à inclure dans la recherche.
     * @return Une liste d'Hebergement répondant aux critères du filtre.
     */
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



    //http://localhost:8080/hebergements/findByVille?villeId=1
    /**
     * Recherche des hébergements dans une ville spécifiée.
     *
     * @param villeId L'identifiant de la ville pour laquelle on veut un hebergement.
     * @return Une liste d' Hebergement correspondant à la ville.
     */
    @GetMapping("/findByVille")
    public List<Hebergement> findByVille(@RequestParam Integer villeId) {
        Ville ville = villeService.findById(villeId);
        return hebergementService.findByVille(ville);
    }


    //http://localhost:8080/hebergements/findByDate?dateArrivee=2024-03-01&dateDepart=2024-03-10
    /**
     * Recherche des hébergements disponibles pour une période spécifiée.
     *
     * @param dateArrivee La date d'arrivée souhaitée au format ISO (yyyy-MM-dd).
     * @param dateDepart La date de départ.
     * @return Une ResponseEntity contenant une liste d'Hebergement pour la période spécifiée.
     *         La réponse est OK (200) si des hébergements sont trouvés.
     */
    @GetMapping("/findByDate")
    public ResponseEntity<List<Hebergement>> findByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateArrivee,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDepart) {
        List<Hebergement> result = hebergementService.findByDateArriveeAndDateDepart(dateArrivee, dateDepart);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    /**
     * Effectue une réservation pour un hébergement.
     *
     * @param reservationDto Un DTO contenant les informations nécessaires pour la réservation.
     *                       Contient l'id et le nombre de personnes pour la réservation.
     * @return Une ResponseEntity contenant un message de succès si la réservation est effectuée avec succès.
     *         En cas d'échec, renvoie 400 pour Bad Request, 404 pour Not Found + un message.
     */
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

    /**
     * Nombre de places restantes pour un hébergement.
     *
     * @param hebergementId L'id de l'hébergement pour lequel on récupère les places restantes.
     * @return Une ResponseEntity contenant un DTO avec le nombre de place restante.
     *         En cas d'échec, renvoie 404 pour Not Found.
     */
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
