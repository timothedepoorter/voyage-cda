package fr.timothe.voyage.tag;

import fr.timothe.voyage.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Tag findById( Integer id) {
        return  tagRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Tag inexistant")
        );
    }


    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }


    public Tag update(Tag tag) {
        return tagRepository.save(tag);
    }


    public void deleteById(Integer id) {
        Tag tag = this.findById(id);
        tagRepository.delete(tag);
    }


}
