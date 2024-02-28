package fr.timothe.voyage.hebergement;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hebergements")
public class HebergementController {

    private final HebergementService hebergementService;


    public HebergementController(HebergementService hebergementService){
        this.hebergementService = hebergementService;

    }

    //Create
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Hebergement save(@RequestBody Hebergement hebergement) {
        return hebergementService.save(hebergement);
    }

    //Read
    @GetMapping
    public List<Hebergement> findAll(@RequestParam(required = false) Integer id){
        if (id != null){
            hebergementService.findAllByVilleId(id);
        }
        return hebergementService.findAll();
    }

    @GetMapping("/{id}")
    public Hebergement findById(@PathVariable Integer id){
        return hebergementService.findById(id);
    }


    //Update
    @PutMapping("/{id}")
    public Hebergement update(@RequestBody Hebergement hebergement){
        return hebergementService.update(hebergement);
    }


    //Delete
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id){
        hebergementService.deleteById(id);
    }
}
