/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.HeroSightings.dao;

import com.sg.HeroSightings.entity.Hero;
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
public class HeroDaoDB implements HeroDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        jdbc.update("INSERT INTO Hero(name, description, superpower) VALUES(?, ?, ?)",
                hero.getName(),
                hero.getDescription(),
                hero.getSuperPower());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setId(newId);

        return hero;
    }

    @Override
    public Hero getHero(int id) {
        try {
            return jdbc.queryForObject("SELECT * FROM Hero WHERE id = ?",
                    new HeroMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Hero> getAllHeroes() {
        return jdbc.query("SELECT * FROM Hero", new HeroMapper());
    }

    @Override
    @Transactional
    public void updateHero(Hero hero) {
        jdbc.update("UPDATE Hero SET name = ?, description = ?, superpower = ? WHERE id = ?", 
                hero.getName(),
                hero.getDescription(),
                hero.getSuperPower(),
                hero.getId());
    }

    @Override
    public void deleteHero(int id) {
        jdbc.update("DELETE FROM Hero_Organization WHERE heroid = ?", id);
        jdbc.update("DELETE FROM Hero_Sighting WHERE heroid = ?", id);
        jdbc.update("DELETE FROM Hero WHERE id = ?", id);
    }

    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int arg1) throws SQLException {
            Hero h = new Hero();
            h.setId(rs.getInt("id"));
            h.setName(rs.getString("name"));
            h.setDescription(rs.getString("description"));
            h.setSuperPower(rs.getString("superpower"));

            return h;
        }

    }

}
