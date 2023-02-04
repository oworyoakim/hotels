package com.hotels.properties.services;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.hotels.db.DatabaseConnection;
import com.hotels.properties.enumerations.AmenityType;
import com.hotels.properties.enumerations.PropertyType;
import com.hotels.properties.models.Amenity;
import com.hotels.properties.models.Property;
import lombok.AllArgsConstructor;
import org.jdbi.v3.core.mapper.RowMapper;

@AllArgsConstructor
@ApplicationScoped
public class AmenityServiceImpl implements AmenityService {
    private final DatabaseConnection connection;

    private static final String SELECT_AMENITY = """
        SELECT
            id,
            name,
            amenityType,
            avatar,
            createdAt,
            updatedAt
        FROM
            amenities am
        """;
    @Override
    public Optional<Amenity> create(Amenity amenity) {
        return connection.getConnection().withHandle(handle -> {
            Long amenityId = handle.createUpdate("""
                    INSERT INTO amenities (
                        name,
                        amenityType,
                        avatar,
                        createdAt,
                        updatedAt
                    ) VALUES (
                        :name,
                        :amenityType,
                        :avatar,
                        :createdAt,
                        :updatedAt
                    )
                """).bind("name", amenity.getName())
                .bind("amenityType", amenity.getAmenityType())
                .bind("avatar", amenity.getAvatar())
                .bind("createdAt", amenity.getCreatedAt())
                .bind("updatedAt", amenity.getUpdatedAt())
                .executeAndReturnGeneratedKeys("id")
                .mapTo(Long.class)
                .one();

            return find(amenityId);
        });
    }

    @Override
    public Optional<Amenity> find(Long id) {
        return connection.getConnection().withHandle(handle -> handle.createQuery(SELECT_AMENITY + " WHERE am.id = :id")
            .bind("id", id)
            .map(amenityRowMapper())
            .findFirst());
    }

    private static RowMapper<Amenity> amenityRowMapper() {
        return (rs, ctx) -> Amenity.builder()
            .id(rs.getLong("id"))
            .amenityType(AmenityType.valueOf(rs.getString("amenityType")))
            .name(rs.getString("name"))
            .avatar(rs.getString("avatar"))
            .createdAt(rs.getTimestamp("createdAt").toLocalDateTime())
            .updatedAt(rs.getTimestamp("updatedAt").toLocalDateTime())
            .build();
    }
}
