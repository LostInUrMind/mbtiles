package com.example.mbtiles_backend.mapbox;

import org.imintel.mbtiles4j.Tile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tiles")
public class TileController {

    @GetMapping("/{zoom}/{x}/{y}.pbf")
    public ResponseEntity<byte[]> getTile(@PathVariable int zoom, @PathVariable int x, @PathVariable int y) {
        try {
            String mbtilesFile = "C:/Users/NhatNM2/Downloads/output.mbtiles"; // Đường dẫn tới file .mbtiles
            Tile tileData = TilesReader.getTiles(mbtilesFile, zoom, x, y);
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_ENCODING, "gzip");
            headers.setContentType(MediaType.valueOf("application/pbf"));
            if(tileData.getData() != null) {
                return ResponseEntity.ok()
                        .headers(headers)
                        .body(tileData.getData().readAllBytes());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
