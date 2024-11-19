package com.example.mbtiles_backend.mapbox;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tiles")
public class TileController {

    @GetMapping("/{zoom}/{x}/{y}")
    public ResponseEntity<byte[]> getTile(@PathVariable int zoom, @PathVariable int x, @PathVariable int y) {
        try {
            String mbtilesFile = "C:/Users/NhatNM2/Downloads/tiles/mbtiles-backend/src/main/java/com/example/mbtiles_backend/mapbox/104281_matched_polygon_new.mbtiles"; // Đường dẫn tới file .mbtiles
//            JSONObject tileData = MBTilesReader.getTileData(mbtilesFile, zoom, x, y);
            TileRes tileData = MBTilesReader.getTiles(mbtilesFile, zoom, x, y);
            if(tileData != null) {
                return ResponseEntity.ok().body(tileData.data);
            } else {
                return null;
            }
//            if (tileData != null) {
//                return tileData;
//            } else {
//                return new JSONObject().put("error", "Tile not found");
//            }
        } catch (Exception e) {
            throw new RuntimeException(e);
//            e.printStackTrace();
//            return new JSONObject().put("error", "Failed to fetch tile data");
        }
    }
}
