/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.HeroSightings.dao;

import com.sg.HeroSightings.entity.Location;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Repository
public class LocationDaoDB implements LocationDao{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Location addLocation(Location location) {
        jdbc.update("INSERT INTO Location(name, description, address, latitude, longitude) "
                + "VALUES(?, ?, ?, ?, ?)", 
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude());
        
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        
        return location;
    }

    @Override
    public Location getLocation(int id) {
        try{
            return jdbc.queryForObject("SELECT * FROM Location WHERE id =?", 
                    new LocationMapper(), id);
        }catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        return jdbc.query("SELECT * FROM Location", new LocationMapper());
    }

    @Override
    @Transactional
    public void updateLocation(Location location) {
        jdbc.update("UPDATE Location SET name = ?, description = ?, address = ?, "
                + "latitude = ?, longitude = ? WHERE id = ?", 
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getId());
    }

    @Override
    public void deleteLocation(int id) {
        jdbc.update("DELETE hs.* FROM Hero_Sighting hs JOIN sighting s "
                + "ON s.id = hs.sightingid WHERE s.locationid = ?", id);
        jdbc.update("DELETE FROM Sighting WHERE locationid = ?", id);
        jdbc.update("DELETE FROM Location WHERE id = ?", id);
    }
    
    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int arg1) throws SQLException {
            Location loc = new Location();
            loc.setId(rs.getInt("id"));
            loc.setName(rs.getString("name"));
            loc.setDescription(rs.getString("description"));
            loc.setAddress(rs.getString("address"));
            loc.setLatitude(rs.getBigDecimal("latitude"));
            loc.setLongitude(rs.getBigDecimal("longitude"));
            
            return loc;
        }
        
    }
    
}
