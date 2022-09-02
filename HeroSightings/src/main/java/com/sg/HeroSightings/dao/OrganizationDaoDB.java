/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.HeroSightings.dao;

import com.sg.HeroSightings.dao.HeroDaoDB.HeroMapper;
import com.sg.HeroSightings.entity.Hero;
import com.sg.HeroSightings.entity.Organization;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Organization addOrganization(Organization org) {
        jdbc.update("INSERT INTO `Organization`(name, description, address, contact) VALUES(?, ?, ?, ?)",
                org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getContact());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setId(newId);
        insertHeroOrganization(org);

        return org;
    }

    @Override
    public Organization getOrganization(int id) {
        try {
            Organization org = jdbc.queryForObject("SELECT * FROM `Organization` WHERE id = ?",
                    new OrganizationMapper(), id);
            org.setHeroes(getAllHeroesForOrg(org));

            return org;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganization() {
        List<Organization> orgs = jdbc.query("SELECT * FROM `Organization`", new OrganizationMapper());

        for (Organization o : orgs) {
            o.setHeroes(getAllHeroesForOrg(o));
        }
        return orgs;

    }

    @Override
    @Transactional
    public void updateOrganization(Organization org) {
        jdbc.update("UPDATE `Organization` SET name = ?, description = ?, "
                + "address = ?, contact = ? WHERE id = ?",
                org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getContact(),
                org.getId());

        jdbc.update("DELETE FROM Hero_Organization WHERE organizationid = ?", org.getId());
        insertHeroOrganization(org);
    }

    @Override
    public void deleteOrganization(int id) {
        jdbc.update("DELETE FROM hero_organization WHERE organizationid = ?", id);
        jdbc.update("DELETE FROM `Organization` WHERE id = ?", id);
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int arg1) throws SQLException {
            Organization org = new Organization();
            org.setId(rs.getInt("id"));
            org.setName(rs.getString("name"));
            org.setDescription(rs.getString("description"));
            org.setAddress(rs.getString("address"));
            org.setContact(rs.getString("contact"));

            return org;
        }

    }

    private List<Hero> getAllHeroesForOrg(Organization org) {
        return jdbc.query("SELECT h.* FROM Hero h JOIN hero_organization ho "
                + "ON h.id = ho.heroid WHERE ho.organizationid = ?", new HeroMapper(), org.getId());
    }

    private void insertHeroOrganization(Organization org) {
        for (Hero h : org.getHeroes()) {
            jdbc.update("INSERT INTO Hero_Organization(heroid, organizationid) VALUES(?, ?)",
                    h.getId(),
                    org.getId());
        }
    }

}
