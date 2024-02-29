package fr.timothe.voyage.tag;

import fr.timothe.voyage.exceptions.BadRequestException;
import fr.timothe.voyage.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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


    public Tag save(Tag tag) throws BadRequestException {
        verifyValuesTag(tag);
        return tagRepository.save(tag);
    }

    private static void verifyValuesTag(Tag tag) {
        List<String> erreurs = new ArrayList<>();

        if (tag.getNom() == null) {
            erreurs.add("L'intitulé du tag est obligatoire");
        }

        if (!erreurs.isEmpty()) {
            throw new BadRequestException(erreurs);
        }

    }


    public Tag update(Tag tag) {
        return tagRepository.save(tag);
    }


    public void deleteById(Integer id) {
        Tag tag = this.findById(id);
        tagRepository.delete(tag);
    }


}
