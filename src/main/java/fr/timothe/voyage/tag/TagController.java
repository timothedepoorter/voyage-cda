package fr.timothe.voyage.tag;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }


    @GetMapping
    public List<Tag> findAll() {
        return tagService.findAll();
    }

    @GetMapping("/{id}")
    public Tag findById(@PathVariable Integer id) {
        return tagService.findById(id);
    }


    @PostMapping
    public Tag save(@RequestBody Tag tag) {
        return tagService.save(tag);
    }


    @PutMapping("/{id}")
    public Tag update(@RequestBody Tag tag) {
        return tagService.update(tag);
    }


    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Integer id) {
        tagService.deleteById(id);
    }
}
