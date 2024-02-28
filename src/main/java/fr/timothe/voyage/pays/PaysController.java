package fr.timothe.voyage.pays;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.timothe.voyage.pays.dto.PaysDto;
import fr.timothe.voyage.ville.Ville;
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
    public List<PaysDto> findAll(){
        return paysService.findAll().stream().map(
                pays -> objectMapper.convertValue(pays, PaysDto.class)
        ).toList();
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
    public Ville addVilleToPays(@PathVariable Integer id, @RequestBody Ville ville) {
        this.paysService.addVilleToPays(ville, id);
        return ville;
    }
}
