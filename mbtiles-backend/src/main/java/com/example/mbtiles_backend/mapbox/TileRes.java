package com.example.mbtiles_backend.mapbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TileRes {
    int zoom;
    int column;
    int row;
    byte[] data;
}
