package com.gride29.airbnb.clone.backend.security.services;

import com.gride29.airbnb.clone.backend.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gride29.airbnb.clone.backend.models.Listing;

import java.util.List;
import java.util.Optional;

@Service
public class ListingService {

    @Autowired
    ListingRepository listingRepository;

    public List<Listing> findAll() {
        return listingRepository.findAll();
    }

    public List<Listing> findByTitleContaining(String title) {
        return listingRepository.findByTitleContaining(title);
    }

    public Optional<Listing> findById(String id) {
        return listingRepository.findById(id);
    }

    public Listing save(Listing listing) {
        return listingRepository.save(listing);
    }

    public Listing update(String id, Listing listing) {
        Optional<Listing> optionalListing = listingRepository.findById(id);
        if (optionalListing.isPresent()) {
            listing.setId(id);
            return listingRepository.save(listing);
        } else {
            return null;
        }
    }

    public void deleteById(String id) {
        listingRepository.deleteById(id);
    }

    public void deleteAll() {
        listingRepository.deleteAll();
    }
}
