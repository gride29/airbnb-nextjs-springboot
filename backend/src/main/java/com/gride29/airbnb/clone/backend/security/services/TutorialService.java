package com.gride29.airbnb.clone.backend.security.services;

import java.util.List;
import java.util.Optional;

import com.gride29.airbnb.clone.backend.models.Tutorial;
import com.gride29.airbnb.clone.backend.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TutorialService {

    @Autowired
    TutorialRepository tutorialRepository;

    public List<Tutorial> findAll() {
        return tutorialRepository.findAll();
    }

    public List<Tutorial> findByTitleContaining(String title) {
        return tutorialRepository.findByTitleContaining(title);
    }

    public Optional<Tutorial> findById(String id) {
        return tutorialRepository.findById(id);
    }

    public Tutorial save(Tutorial tutorial) {
        return tutorialRepository.save(tutorial);
    }

    public Tutorial update(String id, Tutorial tutorial) {
        Optional<Tutorial> optionalTutorial = tutorialRepository.findById(id);
        if (optionalTutorial.isPresent()) {
            tutorial.setId(id);
            return tutorialRepository.save(tutorial);
        } else {
            return null;
        }
    }

    public void deleteById(String id) {
        tutorialRepository.deleteById(id);
    }

    public void deleteAll() {
        tutorialRepository.deleteAll();
    }

    public Optional<Tutorial> findByPublished(boolean isPublished) {
        return tutorialRepository.findByPublished(isPublished);
    }
}