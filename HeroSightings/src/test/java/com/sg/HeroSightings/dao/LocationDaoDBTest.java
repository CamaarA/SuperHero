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
import java.util.List;
import org.junit.Before;
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
public class LocationDaoDBTest {
    
    @Autowired
    LocationDao dao;
    
    @Autowired
    HeroDao heroDao;
    
    public LocationDaoDBTest() {
    }
    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
    
    @Before
    public void setUp() {
        List<Location> locs = dao.getAllLocations();
        
        for(Location l : locs){
            dao.deleteLocation(l.getId());
        }
    }
    
//    @After
//    public void tearDown() {
//    }

    /**
     * Test of addLocation method, of class LocationDaoDB.
     */
    @Test
    public void testAddLocation() {
        Location l = new Location();
        l.setName("New York City");
        l.setDescription("Inner City");
        l.setAddress("101 Main Street");
        l.setLatitude(new BigDecimal("40.712800"));
        l.setLongitude(new BigDecimal("74.006000"));
        
        l = dao.addLocation(l);
        
        Location fromDao = dao.getLocation(l.getId());
        
        assertEquals(fromDao, l);
    }

    /**
     * Test of getLocation method, of class LocationDaoDB.
     */
    
    @Test
    public void testGetAllLocations() {
        Location l = new Location();
        l.setName("New York City");
        l.setDescription("Inner City");
        l.setAddress("101 Main Street");
        l.setLatitude(new BigDecimal("40.7128"));
        l.setLongitude(new BigDecimal("74.0060"));
        
        l = dao.addLocation(l);
        
        Location l2 = new Location();
        l2.setName("New York City");
        l2.setDescription("Inner City");
        l2.setAddress("101 Main Street");
        l2.setLatitude(new BigDecimal("40.7128"));
        l2.setLongitude(new BigDecimal("74.0060"));
        
        l2 = dao.addLocation(l2);
        
        List<Location> locs = dao.getAllLocations();
        
        assertEquals(2, locs.size());
    }

    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */
    @Test
    public void testUpdateLocation() {
        Location l = new Location();
        l.setName("New York");
        l.setDescription("Inner City");
        l.setAddress("101 Main Street");
        l.setLatitude(new BigDecimal("40.712800"));
        l.setLongitude(new BigDecimal("74.006000"));
        
        l = dao.addLocation(l);
        
        l.setName("New York City");
        
        dao.updateLocation(l);
        
        assertTrue(l.getName().equalsIgnoreCase("New York City"));
        assertEquals(dao.getLocation(l.getId()), l);
    }

    /**
     * Test of deleteLocation method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocation() {
        Location l = new Location();
        l.setName("New York City");
        l.setDescription("Inner City");
        l.setAddress("101 Main Street");
        l.setLatitude(new BigDecimal("40.7128"));
        l.setLongitude(new BigDecimal("74.0060"));
        
        l = dao.addLocation(l);
        
        Hero h = new Hero();
        h.setName("Hulk");
        h.setDescription("Grows 10x and turns green");
        h.setSuperPower("Super Strength");
        
        h = heroDao.addHero(h);
        
        Sighting s = new Sighting();
        s.setDate(LocalDate.now());
        s.setLocation(l);
        s.setHeroes(heroDao.getAllHeroes());
        
        dao.deleteLocation(l.getId());
        
        assertFalse(dao.getAllLocations().contains(l));
        assertNull(dao.getLocation(l.getId()));
    }
    
}
