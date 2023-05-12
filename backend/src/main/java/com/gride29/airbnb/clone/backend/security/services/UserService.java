package com.gride29.airbnb.clone.backend.security.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gride29.airbnb.clone.backend.models.User;
import com.gride29.airbnb.clone.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public String[] getFavoriteListings(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            return user.getFavoriteListings();
        } else {
            return new String[0];
        }
    }

    public String addFavoriteListing(String userId, String favoriteListing) throws JsonProcessingException {
        User user = userRepository.findById(userId).orElse(null);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(favoriteListing);
        String favoriteListingValue = jsonNode.get("favoriteListing").asText();
        boolean alreadyAdded = false;

        if (user != null) {
            String[] favorites = user.getFavoriteListings();
            if (Arrays.asList(favorites).contains(favoriteListingValue)) {
                alreadyAdded = true;
            } else {
                String[] updatedFavorites = Arrays.copyOf(favorites, favorites.length + 1);
                updatedFavorites[favorites.length] = favoriteListingValue;
                user.setFavoriteListings(updatedFavorites);
                userRepository.save(user);
            }
        }
        if (alreadyAdded) {
            return null;
        } else {
            return favoriteListingValue;
        }
    }

    public String removeFavoriteListing(String userId, String favoriteListing) throws JsonProcessingException {
        User user = userRepository.findById(userId).orElse(null);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(favoriteListing);
        String favoriteListingValue = jsonNode.get("favoriteListing").asText();
        boolean isRemoved = false;

        if (user != null) {
            String[] favorites = user.getFavoriteListings();
            List<String> updatedFavorites = new ArrayList<>(Arrays.asList(favorites));
            isRemoved = updatedFavorites.remove(favoriteListingValue);
            user.setFavoriteListings(updatedFavorites.toArray(new String[0]));
            userRepository.save(user);
        }

        if (isRemoved) {
            return favoriteListingValue;
        } else {
            return null;
        }
    }
}
