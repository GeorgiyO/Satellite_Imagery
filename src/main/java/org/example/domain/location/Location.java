package org.example.domain.location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {
    private int id;
    private int locationId;
    private String name;
    private String description;

    public <T extends Location> void appendTo(T target) {
        target.setLocationId(locationId);
        target.setName(name);
        target.setDescription(description);
    }
}
