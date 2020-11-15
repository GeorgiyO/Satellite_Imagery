package org.example.database.image.country;

import org.example.domain.image.Country;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryMapper implements RowMapper<Country> {
    @Override
    public Country mapRow(ResultSet rs, int i) throws SQLException {
        Country country = new Country();

        country.setId(rs.getInt("id"));
        country.setName(rs.getString("name"));
        country.setDescription(rs.getString("description"));

        Blob blob = rs.getBlob("image");
        int length = (int) blob.length();
        byte[] image = blob.getBytes(1, length);
        country.setImage(image);

        return country;
    }
}
