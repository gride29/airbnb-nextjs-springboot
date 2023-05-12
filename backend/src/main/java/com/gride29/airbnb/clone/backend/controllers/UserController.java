package com.gride29.airbnb.clone.backend.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gride29.airbnb.clone.backend.security.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/favorites/{userId}")
    public ResponseEntity<Map<String, Object>> getFavoriteListings(@PathVariable String userId) {
        String[] favorites = userService.getFavoriteListings(userId);
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("favoriteListings", favorites);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/favorites/{userId}")
    public ResponseEntity<Map<String, Object>> addFavoriteListing(
            @PathVariable String userId,
            @RequestBody String favoriteListing
    ) throws JsonProcessingException {
        String addedListing = userService.addFavoriteListing(userId, favoriteListing);
        if (addedListing == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Listing already added.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("userId", userId);
            response.put("addedListing", addedListing);
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/favorites/{userId}")
    public ResponseEntity<Map<String, Object>> removeFavoriteListing(
            @PathVariable String userId,
            @RequestBody String favoriteListing
    ) throws JsonProcessingException {
        String removedListing = userService.removeFavoriteListing(userId, favoriteListing);
        if (removedListing == null) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Listing not found or already deleted.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("userId", userId);
            response.put("removedListing", removedListing);
            return ResponseEntity.ok(response);
        }
    }
}
