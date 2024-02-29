package fr.timothe.voyage.ville;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.timothe.voyage.hebergement.Hebergement;

import fr.timothe.voyage.hebergement.dto.HebergementSansVilleDto;
import fr.timothe.voyage.pays.dto.PaysCompletDto;
import fr.timothe.voyage.ville.dto.VilleCompletDto;
import fr.timothe.voyage.ville.dto.VilleSansPaysDto;

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
    public VilleSansPaysDto findById(@PathVariable Integer id){

        return objectMapper.convertValue( villeService.findById(id), VilleSansPaysDto.class);
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
}
