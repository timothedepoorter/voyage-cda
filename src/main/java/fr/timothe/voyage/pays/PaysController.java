package fr.timothe.voyage.pays;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.timothe.voyage.pays.dto.PaysCompletDto;
import fr.timothe.voyage.ville.Ville;
import fr.timothe.voyage.ville.dto.VilleSansPaysDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/pays")
public class PaysController {
    private final PaysService paysService;
    private final ObjectMapper objectMapper;

    public PaysController(PaysService paysService, ObjectMapper objectMapper) {
        this.paysService = paysService;
        this.objectMapper = objectMapper;
    }

   //create
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Pays save(@RequestBody Pays pays){return  paysService.save(pays);}

    //get
    @GetMapping
    public List<Pays> findAll(){
        return paysService.findAll();
    }

    @GetMapping("/{id}")
    public Pays findById(@PathVariable Integer id){
        return paysService.findById(id);
    }

    //Update
    @PutMapping("/{id}")
    public Pays update(@RequestBody Pays pays){
        return  paysService.update(pays);
    }

    //Delete
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        paysService.deleteById(id);
    }

    @PostMapping("/{id}/villes")
    public PaysCompletDto addVilleToPays(@PathVariable Integer id, @RequestBody Ville ville) {
        Pays pays = paysService.addVilleToPays(ville, id);
        PaysCompletDto paysCompletDto = new PaysCompletDto();
        paysCompletDto.setId(pays.getId());
        paysCompletDto.setNom(pays.getNom());
        paysCompletDto.setVilles(
                pays.getVilles().stream().map(
                        unmappedVille -> objectMapper.convertValue(
                                unmappedVille,
                                VilleSansPaysDto.class
                        )
                ).toList()
        );
        return paysCompletDto;
    }
}
