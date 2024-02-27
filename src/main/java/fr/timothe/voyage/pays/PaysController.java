package fr.timothe.voyage.pays;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/pays")
public class PaysController {
    private final PaysService paysService;

    public PaysController(PaysService paysService) {
        this.paysService = paysService;
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
}
