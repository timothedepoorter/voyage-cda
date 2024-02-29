package fr.timothe.voyage.ville;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.timothe.voyage.hebergement.Hebergement;

import fr.timothe.voyage.hebergement.dto.HebergementSansVilleDto;
import fr.timothe.voyage.pays.Pays;
import fr.timothe.voyage.ville.dto.VilleCompletDto;

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

    /**
     * CREATE une ville
     * @param ville
     * @return une nouvelle ville
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Ville save(@RequestBody Ville ville) {
        return villeService.save(ville);
    }

    /**
     * GET
     * @return une liste de ville
     */
    @GetMapping
    public List<VilleDto> findAll(){
        return villeService.findAll().stream().map(
                ville -> objectMapper.convertValue(ville, VilleDto.class)
        ).toList();
    }

    @GetMapping("/{id}")
    public VilleCompletDto findById(@PathVariable Integer id){

        return objectMapper.convertValue( villeService.findById(id), VilleCompletDto.class);
    }

    @PutMapping("/{id}")
    public Ville update(@RequestBody Ville ville){
        return villeService.update(ville);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        villeService.deleteById(id);
    }

    /**
     * Ajoute un hébergement à une ville.
     *
     * @param id L'id de la ville à laquelle ajouter l'hébergement.
     * @param hebergement à ajouter à la ville.
     * @return VilleCompletDto contenant les informations complètes de la ville après l'ajout de l'hébergement.
     */
    @PostMapping("/{id}/hebergements")
    public VilleCompletDto addHebergementToVille(@PathVariable Integer id, @RequestBody Hebergement hebergement) {
        Ville ville = villeService.addHebergementToVille(id, hebergement);
        VilleCompletDto villeCompletDto = new VilleCompletDto();
        villeCompletDto.setId(ville.getId());
        villeCompletDto.setNom(ville.getNom());
        villeCompletDto.setHebergements(
                ville.getHebergements().stream().map(
                        unmappedHebergement -> objectMapper.convertValue(
                                unmappedHebergement,
                                HebergementSansVilleDto.class
                        )
                ).toList()
        );
        return villeCompletDto;
    }

    /**
     * Ajoute un pays à une ville.
     *
     * @param id L'id de la ville à laquelle ajouter le pays.
     * @param pays à ajouter à la ville.
     * @return La ville mise à jour après l'ajout du pays.
     */
    @PostMapping("/{id}/pays")
    public Ville addPaysToVille(@PathVariable Integer id, @RequestBody Pays pays) {
        return villeService.addPaysToVille(id, pays);
    }
}
