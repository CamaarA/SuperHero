/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.HeroSightings.dao;

import com.sg.HeroSightings.dao.HeroDaoDB.HeroMapper;
import com.sg.HeroSightings.dao.LocationDaoDB.LocationMapper;
import com.sg.HeroSightings.entity.Hero;
import com.sg.HeroSightings.entity.Location;
import com.sg.HeroSightings.entity.Sighting;
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
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        jdbc.update("INSERT INTO Sighting(`date`, locationid) VALUE(?, ?)",
                sighting.getDate(),
                sighting.getLocation().getId());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        insertHeroSighting(sighting);

        return sighting;
    }

    @Override
    public Sighting getSighting(int id) {
        try {
            Sighting s = jdbc.queryForObject("SELECT * FROM Sighting WHERE id =?",
                    new SightingMapper(), id);
            s.setLocation(getLocationForSighting(s));
            s.setHeroes(getAllHeroesForSighting(s));

            return s;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        List<Sighting> sightings = jdbc.query("SELECT * FROM Sighting", new SightingMapper());

        for (Sighting s : sightings) {
            s.setLocation(getLocationForSighting(s));
            s.setHeroes(getAllHeroesForSighting(s));
        }

        return sightings;
    }

    @Override
    @Transactional
    public void updateSighting(Sighting sighting) {
        jdbc.update("UPDATE Sighting SET `date` = ?, locationid = ? WHERE id = ?", 
                sighting.getDate(), 
                sighting.getLocation().getId(),
                sighting.getId());
        
        jdbc.update("DELETE FROM Hero_Sighting WHERE sightingid = ?", sighting.getId());
        insertHeroSighting(sighting);
    }

    @Override
    public void deleteSighting(int id) {
        jdbc.update("DELETE FROM Hero_Sighting WHERE sightingid = ?", id);
        jdbc.update("DELETE FROM Sighting WHERE id = ?", id);
    }
    
    @Override
    public List<Sighting> latestSightings() {
        List<Sighting> sightings = jdbc.query("SELECT * FROM Sighting ORDER BY `date` desc LIMIT 10", 
                new SightingMapper());
        
        for(Sighting s : sightings) {
            s.setLocation(getLocationForSighting(s));
            s.setHeroes(getAllHeroesForSighting(s));
        }
        
        return sightings;
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int arg1) throws SQLException {
            Sighting s = new Sighting();
            s.setId(rs.getInt("id"));
            s.setDate(rs.getDate("date").toLocalDate());

            return s;
        }

    }

    private List<Hero> getAllHeroesForSighting(Sighting s) {
        return jdbc.query("SELECT h.* FROM Hero h JOIN hero_sighting hs "
                + "ON h.id = hs.heroid WHERE hs.sightingid = ?", new HeroMapper(), s.getId());
    }

    private Location getLocationForSighting(Sighting s) {
        return jdbc.queryForObject("SELECT l.* FROM Location l JOIN Sighting s "
                + "ON s.locationid = l.id WHERE s.id = ?", new LocationMapper(), s.getId());

    }
    
    private void insertHeroSighting(Sighting s) {
        for(Hero h : s.getHeroes()) {
            jdbc.update("INSERT INTO Hero_Sighting(heroid, sightingid) VALUES(?, ?)", 
                    h.getId(), 
                    s.getId());
        }
    }

}
