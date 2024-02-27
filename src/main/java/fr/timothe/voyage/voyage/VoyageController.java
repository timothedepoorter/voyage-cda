package fr.timothe.voyage.voyage;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/voyages")
public class VoyageController {
    private final VoyageService voyageService;

    public VoyageController(VoyageService voyageService) {
        this.voyageService = voyageService;
    }

    @GetMapping()
    public List<Voyage> findAll() {
        return this.voyageService.findAll();
    }

    @GetMapping(path = "/{id}")
    public Voyage findById(@PathVariable Integer id) {
        return this.voyageService.findById(id);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Integer id) {
        this.voyageService.delete(id);
    }

    @PostMapping()
    public Voyage save(Voyage voyage) {
        return this.voyageService.save(voyage);
    }

    @PutMapping(path ="/{id}")
    public Voyage update(Voyage voyage) {
        return this.voyageService.save(voyage);
    }
}
