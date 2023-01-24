package com.hotels.properties.services;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import com.hotels.db.DatabaseConnection;
import com.hotels.properties.enumerations.PropertyType;
import com.hotels.properties.models.Property;
import lombok.AllArgsConstructor;
import org.jdbi.v3.core.mapper.RowMapper;

@AllArgsConstructor
@ApplicationScoped
public class PropertyServiceImpl implements PropertyService {
    private final DatabaseConnection connection;

    private static final String SELECT_PROPERTY = """
        SELECT
            id,
            clientId,
            userId,
            propertyType,
            name,
            description,
            listingCurrency,
            nightlyRate,
            bathrooms,
            checkin,
            checkout,
            published,
            country,
            city,
            address,
            zip,
            createdAt,
            updatedAt
        FROM
            properties
        """;

    @Override
    public List<Property> findAll() {
        return connection.getConnection().withHandle(handle ->
            handle.createQuery(SELECT_PROPERTY + " WHERE published")
                .map(propertyRowMapper())
                .list());
    }

    @Override
    public Optional<Property> create(Property property) {
        return connection.getConnection().withHandle(handle -> {
            Long propertyId = handle.createUpdate("""
                    INSERT INTO properties (
                        clientId,
                        userId,
                        propertyType,
                        name,
                        description,
                        nightlyRate,
                        bathrooms,
                        listingCurrency,
                        checkin,
                        checkout,
                        published,
                        country,
                        city,
                        zip,
                        address,
                        createdAt,
                        updatedAt
                    ) VALUES (
                        :clientId,
                        :userId,
                        :propertyType,
                        :name,
                        :description,
                        :listingCurrency,
                        :nightlyRate,
                        :bathrooms,
                        :checkin,
                        :checkout,
                        :published,
                        :country,
                        :city,
                        :zip,
                        :address,
                        :createdAt,
                        :updatedAt
                    )
                """).bind("clientId", property.getClientId())
                .bind("userId", property.getUserId())
                .bind("propertyType", property.getPropertyType())
                .bind("name", property.getName())
                .bind("description", property.getDescription())
                .bind("listingCurrency", property.getListingCurrency())
                .bind("nightlyRate", property.getNightlyRate())
                .bind("bathrooms", property.getBathrooms())
                .bind("checkin", property.getCheckin())
                .bind("checkout", property.getCheckout())
                .bind("published", property.getPublished())
                .bind("country", property.getCountry())
                .bind("city", property.getCity())
                .bind("address", property.getAddress())
                .bind("zip", property.getZip())
                .bind("createdAt", property.getCreatedAt())
                .bind("updatedAt", property.getUpdatedAt())
                .executeAndReturnGeneratedKeys("id")
                .map((rs, ctx) -> rs.getLong("id"))
                .first();

            return find(propertyId);
        });
    }

    @Override
    public Optional<Property> find(Long id) {
        return connection.getConnection().withHandle(handle -> handle.createQuery(SELECT_PROPERTY + " WHERE id = :id")
            .bind("id", id)
            .map(propertyRowMapper())
            .findFirst());
    }

    @Override
    public List<Property> findByClientId(Long clientId) {
        return connection.getConnection().withHandle(handle ->
            handle.createQuery(SELECT_PROPERTY + " WHERE p.clientId = :clientId")
                .bind("clientId", clientId)
                .map(propertyRowMapper())
                .list());
    }

    private static RowMapper<Property> propertyRowMapper() {
        return (rs, ctx) -> Property.builder()
            .id(rs.getLong("id"))
            .clientId(rs.getLong("clientId"))
            .userId(rs.getLong("userId"))
            .propertyType(PropertyType.valueOf(rs.getString("propertyType")))
            .name(rs.getString("name"))
            .description(rs.getString("description"))
            .listingCurrency(rs.getString("listingCurrency"))
            .nightlyRate(rs.getDouble("nightlyRate"))
            .bathrooms(rs.getInt("bathrooms"))
            .published(rs.getBoolean("published"))
            .checkin(rs.getTime("checkin").toLocalTime())
            .checkout(rs.getTime("checkout").toLocalTime())
            .country(rs.getString("country"))
            .city(rs.getString("city"))
            .address(rs.getString("address"))
            .zip(rs.getString("zip"))
            .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
            .updatedAt(rs.getTimestamp("updatedAt").toLocalDateTime())
            .build();
    }
}
