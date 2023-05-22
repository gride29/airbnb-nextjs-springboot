package com.gride29.airbnb.clone.backend.controllers;

import com.gride29.airbnb.clone.backend.models.Listing;
import com.gride29.airbnb.clone.backend.security.services.ListingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ListingController {

    @Autowired
    ListingService listingService;

    @GetMapping("/listings")
    @ResponseStatus(HttpStatus.OK)
    public List<Listing> getAllListings(@RequestParam(required = false) String title) {
        if (title == null)
            return listingService.findAll();
        else
            return listingService.findByTitleContaining(title);
    }

    @GetMapping("/listings/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Listing>> searchListings(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String roomCount,
            @RequestParam(required = false) String guestCount,
            @RequestParam(required = false) String bathroomCount,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String category
    ) throws ParseException {
        List<Listing> listings = listingService.searchListings(
                userId, roomCount, guestCount, bathroomCount, location, startDate, endDate, category
        );
        return ResponseEntity.ok(listings);
    }

    @GetMapping("/listings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Listing> getListingById(@PathVariable("id") String id) {
        return listingService.findById(id);
    }

    @GetMapping("/listings/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Listing> getListingsByUserId(@PathVariable("id") String id) {
        return listingService.findByUserId(id);
    }

    @PostMapping("/listings")
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<Listing> createListing(@RequestBody Listing listing) {
        return Optional.ofNullable(listingService
                .save(new Listing(listing.getTitle(),
                        listing.getDescription(),
                        listing.getCategory(),
                        listing.getImageSrc(),
                        listing.getLocation(),
                        listing.getUserId(),
                        LocalDateTime.now(),
                        listing.getGuestCount(),
                        listing.getRoomCount(),
                        listing.getBathroomCount(),
                        listing.getPrice())));
    }

    @PutMapping("/listings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Listing> updateListing(@PathVariable("id") String id, @RequestBody Listing listing) {
        return Optional.ofNullable(listingService.update(id, listing));
    }

    @DeleteMapping("/listings/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteListing(@PathVariable("id") String id) {
        listingService.deleteById(id);
    }

    @DeleteMapping("/listings")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllListings() {
        listingService.deleteAll();
    }
}