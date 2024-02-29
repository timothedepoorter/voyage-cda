package fr.timothe.voyage.vol;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.timothe.voyage.ville.Ville;
import fr.timothe.voyage.ville.VilleService;
import fr.timothe.voyage.vol.dto.ReservationVolDto;
import fr.timothe.voyage.vol.dto.VolDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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

    /**
     * GET
     * @return une liste de vols.
     */
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


    @PostMapping
    public Vol save(@RequestBody Vol vol) {
        return volService.save(vol);
    }


    @PutMapping("/{id}")
    public Vol update(@RequestBody Vol vol) {
        return volService.update(vol);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        volService.deleteById(id);
    }

    /**
     * Recherche les vols en fonction de plusieurs critères de filtre,
     * ou les paramètres sont facultatif.
     * @param nom Nom de la ville.
     * @param dateAller la date aller du vol.
     * @param dateRetour la date retour du vol.
     * @param prix le prix maximum souhaité.
     * @return Une liste de vol répondant aux critères du filtre.
     */
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

    /**
     * Nombre de place restante dans un vol.
     * @param volId l'id du vol.
     * @return Le nombre de places restante.
     */
    @GetMapping("/getPlaceRestante/{volId}")
    public ResponseEntity<?> getPlaceRestante(@PathVariable Integer volId) {
        return this.volService.getPlaceRestante(volId);
    }


    /**
     * Effectue une reservation pour un vol.
     * @param reservationDto Un DTO contenant les informations nécessaires pour la réservation.
     *                       Contient l'id et le nombre de personnes pour la réservation.
     * @return Une ResponseEntity contenant un message de succès si la réservation est effectuée avec succès.
     * En cas d'échec, renvoie 400 pour Bad Request, 404 pour Not Found + un message.
     */
    @PostMapping("/reservation")
    public ResponseEntity<?> effectuerReservation(@RequestBody ReservationVolDto reservationDto) {
        try {
            volService.effectuerReservation(reservationDto.getVolId(), reservationDto.getNombrePersonnes());
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
            errorResponse.put("message", "Aucun vol trouvé avec l'ID spécifié.");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }
    }
}
