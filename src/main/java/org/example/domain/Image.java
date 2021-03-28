package org.example.domain;

import lombok.Getter;
import lombok.Setter;
import org.example.domain.location.LocationType;

@Getter
@Setter
public class Image {
    private int locationId;
    private byte[] data;
}
