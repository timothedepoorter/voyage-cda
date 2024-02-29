package fr.timothe.voyage.pays;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.timothe.voyage.pays.dto.PaysCompletDto;
import fr.timothe.voyage.pays.dto.PaysDto;
import fr.timothe.voyage.ville.Ville;
import fr.timothe.voyage.ville.VilleService;
import fr.timothe.voyage.ville.dto.VilleSansPaysDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path="/pays")
public class PaysController {
    private final PaysService paysService;
    private final VilleService villeService;
    private final ObjectMapper objectMapper;

    public PaysController(PaysService paysService, VilleService villeService, ObjectMapper objectMapper) {
        this.paysService = paysService;
        this.villeService = villeService;
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
    public PaysCompletDto findById(@PathVariable Integer id){

        Pays pays = paysService.findById(id);
        PaysCompletDto paysCompletDto = new PaysCompletDto();
        paysCompletDto.setId(pays.getId());
        paysCompletDto.setNom(pays.getNom());
        paysCompletDto.setVilles(pays.getVilles().stream().map(
                        ville -> objectMapper.convertValue(ville, VilleSansPaysDto.class)
                ).toList()

        );

        return paysCompletDto;

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
//    public ResponseEntity<?> addVilleToPays(
//            @PathVariable Integer id) {
//        try {
//            Pays pays = paysService.findById(id);
//
//            PaysSansVilleDto reponseDto = new PaysSansVilleDto();
//            reponseDto.setId(pays.getId());
//            reponseDto.setNom(pays.getNom());
//            return new ResponseEntity<>(reponseDto, HttpStatus.CREATED);
//        } catch (NoSuchElementException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    public PaysCompletDto addVilleToPays(@PathVariable Integer id, @RequestBody Ville ville) {
        Pays pays = paysService.addVilleToPays(id, ville);
        PaysCompletDto paysCompletDto = new PaysCompletDto();
        paysCompletDto.setId(pays.getId());
        paysCompletDto.setNom(pays.getNom());
        paysCompletDto.setVilles(
                pays.getVilles().stream().map(
                        unmappedVille -> objectMapper.convertValue(unmappedVille, VilleSansPaysDto.class)
                ).toList()

        );

        return paysCompletDto;
   }


}
