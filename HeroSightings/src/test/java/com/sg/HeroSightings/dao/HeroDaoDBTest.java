/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.HeroSightings.dao;

import com.sg.HeroSightings.entity.Hero;
import com.sg.HeroSightings.entity.Organization;
import com.sg.HeroSightings.entity.Sighting;
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
public class HeroDaoDBTest {
    
    @Autowired
    HeroDao heroDao;
    
    public HeroDaoDBTest() {
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
        List<Hero> heroes = heroDao.getAllHeroes();
        
        for(Hero h : heroes) {
            heroDao.deleteHero(h.getId());
        }
    }
    
//    @After
//    public void tearDown() {
//    }

    /**
     * Test of addHero method, of class HeroDaoDB.
     */
    @Test
    public void testAddHero() {
        Hero h = new Hero();
        h.setName("Hulk");
        h.setDescription("Grows 10x and turns green");
        h.setSuperPower("Super Strength");
        
        h = heroDao.addHero(h);
        
        Hero fromDao = heroDao.getHero(h.getId());
        
        assertEquals(fromDao, h);
        
    }

    /**
     * Test of getAllHeroes method, of class HeroDaoDB.
     */
    @Test
    public void testGetAllHeroes() {
        Hero h = new Hero();
        h.setName("Hulk");
        h.setDescription("Grows 10x and turns green");
        h.setSuperPower("Super Strength");
        
        h = heroDao.addHero(h);
        
        Hero h2 = new Hero();
        h2.setName("Batman");
        h2.setDescription("Wears an all black armored bat costume");
        h2.setSuperPower("Being Rich");
        
        h2 = heroDao.addHero(h2);
        
        List<Hero> heroes = heroDao.getAllHeroes();
        
        assertEquals(2, heroes.size());
        assertTrue(heroes.contains(h2));
        assertTrue(heroes.contains(h));
        
    }

    /**
     * Test of updateHero method, of class HeroDaoDB.
     */
    @Test
    public void testUpdateHero() {
        Hero h = new Hero();
        h.setName("Green Lantern");
        h.setDescription("Grows 10x and turns green");
        h.setSuperPower("Super Strength");
        
        h = heroDao.addHero(h);
        
        h.setName("Hulk");
        
        heroDao.updateHero(h);
        
        assertTrue(h.getName().equalsIgnoreCase("Hulk"));
    }

    /**
     * Test of deleteHero method, of class HeroDaoDB.
     */
    @Test
    public void testDeleteHero() {
        Hero h = new Hero();
        h.setName("Hulk");
        h.setDescription("Grows 10x and turns green");
        h.setSuperPower("Super Strength");
        
        h = heroDao.addHero(h);
        
        Organization o = new Organization();
        o.setName("Avengers");
        o.setDescription("Group of heroes");
        o.setAddress("101 Main Street");
        o.setContact("1-800-555-0202");
        o.setHeroes(heroDao.getAllHeroes());
        
        Sighting s = new Sighting();
        s.setDate(LocalDate.now());
        s.setHeroes(heroDao.getAllHeroes());
        
        heroDao.deleteHero(h.getId());
        
        assertFalse(heroDao.getAllHeroes().contains(h));
        assertNull(heroDao.getHero(h.getId()));
        
    }
    
}
