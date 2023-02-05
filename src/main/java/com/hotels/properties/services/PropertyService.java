package com.hotels.properties.services;

import java.util.List;
import java.util.Optional;

import com.hotels.properties.models.Property;

public interface PropertyService {
    List<Property> findAll();
    Optional<Property> create(Property property);
    Optional<Property> find(Long id);
    List<Property> findByClientId(Long clientId);
}
