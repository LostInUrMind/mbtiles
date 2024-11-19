package com.example.mbtiles_backend.mapbox;

import org.imintel.mbtiles4j.MBTilesReadException;
import org.imintel.mbtiles4j.MBTilesReader;
import org.imintel.mbtiles4j.Tile;
import org.json.JSONObject;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public class TilesReader {
    static Logger logger = Logger.getLogger(TilesReader.class.getName());

    public static JSONObject getTileData(String mbtilesFile, int zoom, int x, int y) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Kết nối tới SQLite file .mbtiles
            connection = DriverManager.getConnection("jdbc:sqlite:" + mbtilesFile);
            logger.info("Start get data");

            // Truy vấn tile từ bảng "tiles"
            String sql = "SELECT tile_data FROM tiles WHERE zoom_level = ? AND tile_column = ? AND tile_row = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, zoom);
            stmt.setInt(2, x);
            stmt.setInt(3, y);

            rs = stmt.executeQuery();

            if(rs.next()) {
                byte[] tileData = rs.getBytes("tile_data");

                // Tạo JSON trả về
                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("tile_data", tileData);
                return jsonResponse;
            } else {
                logger.info("No data");
                return null; // Không tìm thấy tile
            }
        } catch(SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(rs != null) rs.close();
            if(stmt != null) stmt.close();
            if(connection != null) connection.close();
        }
    }

    public static int flipY(int z, int y) {
        return (int) (Math.pow(2, z) - 1 - y);
    }

    public static Tile getTiles(String mbtilesFile, int zoom, int col, int row) throws RuntimeException {
        File file = Paths.get(mbtilesFile).toFile();
        row = flipY(zoom, row);
        try {
            MBTilesReader reader = new MBTilesReader(file);
            Tile tile = reader.getTile(zoom, col, row);
            logger.info("Fetched tile: z:" + tile.getZoom() + " x:" + tile.getColumn() + " y:" + tile.getRow());
            return tile;
        } catch(MBTilesReadException e) {
            throw new RuntimeException(e);
        }

    }

}
