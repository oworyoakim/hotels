package com.hotels.properties.services;

import java.util.Optional;

import com.hotels.properties.models.Amenity;

public interface AmenityService {
    Optional<Amenity> find(Long id);
    Optional<Amenity> create(Amenity amenity);
}
