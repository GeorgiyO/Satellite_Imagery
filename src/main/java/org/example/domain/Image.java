package org.example.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Image {
    private int locationId;
    private byte[] data;
}
