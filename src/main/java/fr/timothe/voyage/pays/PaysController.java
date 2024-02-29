package fr.timothe.voyage.pays;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.timothe.voyage.pays.dto.PaysCompletDto;
import fr.timothe.voyage.pays.dto.PaysDto;
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

    /**
     * CREATE Pays
     * @param pays
     * @return un nouveau pays
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Pays save(@RequestBody Pays pays){return  paysService.save(pays);}

    /**
     * GET
     * @return une liste de pays
     */
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


    @PutMapping("/{id}")
    public Pays update(@RequestBody Pays pays){
        return  paysService.update(pays);
    }

    /**
     * DELETE un pays
     * @param id -utilise l'id pour supprimer le pays associé.
     */
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        paysService.deleteById(id);
    }
}
