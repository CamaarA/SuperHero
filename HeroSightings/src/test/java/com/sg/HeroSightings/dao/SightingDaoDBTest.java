/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.HeroSightings.dao;

import com.sg.HeroSightings.entity.Hero;
import com.sg.HeroSightings.entity.Location;
import com.sg.HeroSightings.entity.Sighting;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SightingDaoDBTest {
    
    @Autowired
    SightingDao sightDao;
    
    @Autowired
    HeroDao heroDao;
    
    @Autowired
    LocationDao locDao;
    
    public SightingDaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Hero> heroes = heroDao.getAllHeroes();
        
        for(Hero h : heroes) {
            heroDao.deleteHero(h.getId());
        }
        
        List<Location> locs = locDao.getAllLocations();
        
        for(Location l : locs) {
            locDao.deleteLocation(l.getId());
        }
        
        List<Sighting> sightings = sightDao.getAllSightings();
        
        for(Sighting s : sightings) {
            sightDao.deleteSighting(s.getId());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addSighting method, of class SightingDaoDB.
     */
    @Test
    public void testAddSighting() {
        Hero h = new Hero();
        h.setName("Hulk");
        h.setDescription("Grows 10x and turns green");
        h.setSuperPower("Super Strength");
        
        h = heroDao.addHero(h);
        
        Location l = new Location();
        l.setName("New York City");
        l.setDescription("Inner City");
        l.setAddress("101 Main Street");
        l.setLatitude(new BigDecimal("40.712800"));
        l.setLongitude(new BigDecimal("74.006000"));
        
        l = locDao.addLocation(l);
        
        Sighting s = new Sighting();
        s.setDate(LocalDate.now());
        s.setLocation(l);
        s.setHeroes(heroDao.getAllHeroes());
        
        s = sightDao.addSighting(s);
        
        Sighting fromDao = sightDao.getSighting(s.getId());
        
        assertEquals(fromDao, s);
        
    }

    /**
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightings() {
        Hero h = new Hero();
        h.setName("Hulk");
        h.setDescription("Grows 10x and turns green");
        h.setSuperPower("Super Strength");
        
        h = heroDao.addHero(h);
        
        Location l = new Location();
        l.setName("New York City");
        l.setDescription("Inner City");
        l.setAddress("101 Main Street");
        l.setLatitude(new BigDecimal("40.712800"));
        l.setLongitude(new BigDecimal("74.0060"));
        l = locDao.addLocation(l);
        
        Sighting s = new Sighting();
        s.setDate(LocalDate.now());
        s.setLocation(l);
        s.setHeroes(heroDao.getAllHeroes());
        
        s = sightDao.addSighting(s);
        
        Sighting s2 = new Sighting();
        s2.setDate(LocalDate.of(2020, Month.MARCH, 1));
        s2.setLocation(l);
        
        s2 = sightDao.addSighting(s2);
        
        List<Sighting> sights = sightDao.getAllSightings();
        
        assertEquals(2, sights.size());
    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {
        Hero h = new Hero();
        h.setName("Hulk");
        h.setDescription("Grows 10x and turns green");
        h.setSuperPower("Super Strength");
        
        h = heroDao.addHero(h);
        
        Location l = new Location();
        l.setName("New York City");
        l.setDescription("Inner City");
        l.setAddress("101 Main Street");
        l.setLatitude(new BigDecimal("40.712800"));
        l.setLongitude(new BigDecimal("74.006000"));
        
        l = locDao.addLocation(l);
        
        Sighting s = new Sighting();
        s.setDate(LocalDate.now());
        s.setLocation(l);
        s.setHeroes(heroDao.getAllHeroes());
        
        s = sightDao.addSighting(s);
        
        s.setDate(LocalDate.of(2020, Month.MARCH, 1));
        
        sightDao.updateSighting(s);
        
        assertEquals(s.getDate(), LocalDate.of(2020, Month.MARCH, 1));
        assertEquals(sightDao.getSighting(s.getId()), s);
    }

    /**
     * Test of deleteSighting method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSighting() {
        Location l = new Location();
        l.setName("New York City");
        l.setDescription("Inner City");
        l.setAddress("101 Main Street");
        l.setLatitude(new BigDecimal("40.712800"));
        l.setLongitude(new BigDecimal("74.006000"));
        
        l = locDao.addLocation(l);
        
        Hero h = new Hero();
        h.setName("Hulk");
        h.setDescription("Grows 10x and turns green");
        h.setSuperPower("Super Strength");
        
        h = heroDao.addHero(h);
        
        Sighting s = new Sighting();
        s.setDate(LocalDate.now());
        s.setLocation(l);
        s.setHeroes(heroDao.getAllHeroes());
        
        s = sightDao.addSighting(s);
        
        assertEquals(s, sightDao.getSighting(s.getId()));
        
        sightDao.deleteSighting(s.getId());
        
        assertNull(sightDao.getSighting(s.getId()));
    }
    
}
