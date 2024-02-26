package fr.timothe.voyage.vol;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vols")
public class VolController {
    private final VolService volService;

    public VolController(VolService volService) {
        this.volService = volService;
    }

    //GET
    @GetMapping
    public List<Vol> findAll() {
        return volService.findAll();
    }

    @GetMapping("/{id}")
    public Vol findById(@PathVariable Integer id) {
        return volService.findById(id);
    }

    //POST
    @PostMapping
    public Vol save(@RequestBody Vol vol) {
        return volService.save(vol);
    }

    //PUT
    @PutMapping("/{id")
    public Vol update(@RequestBody Vol vol) {
        return volService.update(vol);
    }

    //DELETE
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        volService.deleteById(id);
    }
}
